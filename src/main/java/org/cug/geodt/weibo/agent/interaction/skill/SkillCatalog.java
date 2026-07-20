package org.cug.geodt.weibo.agent.interaction.skill;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.SkillDefinition;
import org.cug.geodt.weibo.agent.interaction.model.SkillSlotSpec;
import org.cug.geodt.weibo.agent.interaction.tool.AgentToolRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 内置 Skill 模板目录，供 LLM 选用或作为动态 Skill 的参考。
 */
@Component
public class SkillCatalog {

    private final Map<String, SkillDefinition> builtins = new LinkedHashMap<>();
    private final AgentToolRegistry toolRegistry;

    public SkillCatalog(AgentToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
        registerBuiltins();
    }

    public List<SkillDefinition> listBuiltins() {
        return new ArrayList<>(builtins.values());
    }

    public SkillDefinition getBuiltin(String skillId) {
        SkillDefinition copy = cloneSkill(builtins.get(skillId));
        if (copy != null) {
            refreshToolAvailability(copy);
        }
        return copy;
    }

    public SkillDefinition normalize(SkillDefinition raw) {
        if (raw == null) {
            return null;
        }
        SkillDefinition builtin = builtins.get(raw.getSkillId());
        if (builtin != null) {
            SkillDefinition merged = cloneSkill(builtin);
            merged.setDynamicallyGenerated(false);
            if (raw.getSkillName() != null) {
                merged.setSkillName(raw.getSkillName());
            }
            if (raw.getDescription() != null) {
                merged.setDescription(raw.getDescription());
            }
            if (!raw.getSteps().isEmpty()) {
                merged.setSteps(raw.getSteps());
            }
            if (!raw.getTools().isEmpty()) {
                merged.setTools(raw.getTools());
            }
            refreshToolAvailability(merged);
            return merged;
        }
        raw.setDynamicallyGenerated(true);
        refreshToolAvailability(raw);
        return raw;
    }

    public TaskType mapToTaskType(String skillId) {
        if (skillId == null) {
            return TaskType.UNKNOWN;
        }
        switch (skillId) {
            case "sos_to_wfs":
                return TaskType.SOS_TO_WFS;
            case "wfs_spatial_analysis":
                return TaskType.WFS_SPATIAL_ANALYSIS;
            case "weibo_event_analysis":
                return TaskType.WEIBO_EVENT_ANALYSIS;
            default:
                return TaskType.DYNAMIC;
        }
    }

    public String buildCatalogPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        int i = 0;
        for (SkillDefinition skill : builtins.values()) {
            if (i++ > 0) {
                sb.append(",\n");
            }
            sb.append("  ").append(toPromptJson(skill));
        }
        sb.append("\n]");
        return sb.toString();
    }

    public String buildToolCatalogPrompt() {
        return toolRegistry.buildToolCatalogPrompt();
    }

    private void registerBuiltins() {
        SkillDefinition sos = new SkillDefinition();
        sos.setSkillId("sos_to_wfs");
        sos.setSkillName("SOS 查询并发布 WFS");
        sos.setDescription("查询 SOS 观测数据，可选发布为 WFS 图层");
        sos.setRequiredSlots(slots(
                slot("city", "城市", "目标城市，如武汉市"),
                slot("beginDate", "开始日期", "YYYY-MM-DD"),
                slot("endDate", "结束日期", "YYYY-MM-DD"),
                slot("observedProperty", "观测属性", "如情感得分、温度等")
        ));
        sos.setOptionalSlots(slots(
                slot("shouldPublishWfs", "是否发布 WFS", "true/false"),
                slot("featureOfInterest", "兴趣区域 URI", "武汉市可用 http://www.org.cug.geodt/feature/city4201")
        ));
        sos.setTools(list("query_sos", "publish_wfs"));
        sos.setSteps(list(
                "根据参数生成 SOS GetObservation 请求",
                "调用 query_sos 查询观测结果",
                "若需要则调用 publish_wfs 发布 WFS"
        ));
        builtins.put(sos.getSkillId(), sos);

        SkillDefinition spatial = new SkillDefinition();
        spatial.setSkillId("wfs_spatial_analysis");
        spatial.setSkillName("WFS 图层空间分析");
        spatial.setDescription("对 WFS 图层执行空间分析并输出结果图层");
        spatial.setRequiredSlots(slots(
                slot("wfsLayer", "WFS 图层", "GeoServer WFS 图层名或 URL"),
                slot("analysisType", "分析类型", "如缓冲区、叠加、热点等")
        ));
        spatial.setOptionalSlots(slots(
                slot("outputLayerName", "输出图层名", "分析结果图层名称")
        ));
        spatial.setTools(list("get_wfs_features", "run_spatial_analysis"));
        spatial.setSteps(list("读取 WFS 要素", "执行空间分析", "发布分析结果"));
        builtins.put(spatial.getSkillId(), spatial);

        SkillDefinition weibo = new SkillDefinition();
        weibo.setSkillId("weibo_event_analysis");
        weibo.setSkillName("微博事件分析");
        weibo.setDescription("抓取微博事件相关数据并进行时空分析");
        weibo.setRequiredSlots(slots(
                slot("city", "城市", "分析区域"),
                slot("eventKeyword", "事件关键词", "如暴雨、地震"),
                slot("beginDate", "开始日期", "YYYY-MM-DD"),
                slot("endDate", "结束日期", "YYYY-MM-DD")
        ));
        weibo.setOptionalSlots(slots(
                slot("analysisDepth", "分析深度", "summary / detailed")
        ));
        weibo.setTools(list("crawl_weibo", "query_geomesa"));
        weibo.setSteps(list("抓取微博内容", "查询 GeoMesa 时空记录", "汇总事件分析结果"));
        builtins.put(weibo.getSkillId(), weibo);

        builtins.values().forEach(this::refreshToolAvailability);
    }

    private void refreshToolAvailability(SkillDefinition skill) {
        if (skill == null || skill.getTools() == null || skill.getTools().isEmpty()) {
            if (skill != null) {
                skill.setToolsAvailable(false);
            }
            return;
        }
        skill.setToolsAvailable(skill.getTools().stream().allMatch(toolRegistry::isRegistered));
    }

    private SkillDefinition cloneSkill(SkillDefinition source) {
        if (source == null) {
            return null;
        }
        SkillDefinition copy = new SkillDefinition();
        copy.setSkillId(source.getSkillId());
        copy.setSkillName(source.getSkillName());
        copy.setDescription(source.getDescription());
        copy.setDynamicallyGenerated(source.isDynamicallyGenerated());
        copy.setRequiredSlots(new ArrayList<>(source.getRequiredSlots()));
        copy.setOptionalSlots(new ArrayList<>(source.getOptionalSlots()));
        copy.setSteps(new ArrayList<>(source.getSteps()));
        copy.setTools(new ArrayList<>(source.getTools()));
        copy.setToolsAvailable(source.isToolsAvailable());
        return copy;
    }

    private String toPromptJson(SkillDefinition skill) {
        return "{"
                + "\"skillId\":\"" + skill.getSkillId() + "\","
                + "\"skillName\":\"" + skill.getSkillName() + "\","
                + "\"description\":\"" + skill.getDescription() + "\","
                + "\"requiredSlots\":" + slotNames(skill.getRequiredSlots()) + ","
                + "\"optionalSlots\":" + slotNames(skill.getOptionalSlots()) + ","
                + "\"tools\":" + jsonArray(skill.getTools()) + ","
                + "\"toolsAvailable\":" + skill.isToolsAvailable()
                + "}";
    }

    private String slotNames(List<SkillSlotSpec> slots) {
        List<String> names = new ArrayList<>();
        for (SkillSlotSpec slot : slots) {
            names.add(slot.getName());
        }
        return jsonArray(names);
    }

    private String jsonArray(List<String> items) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("\"").append(items.get(i)).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }

    private static List<SkillSlotSpec> slots(SkillSlotSpec... specs) {
        List<SkillSlotSpec> list = new ArrayList<>();
        Collections.addAll(list, specs);
        return list;
    }

    private static SkillSlotSpec slot(String name, String label, String description) {
        return new SkillSlotSpec(name, label, description);
    }

    private static List<String> list(String... items) {
        List<String> result = new ArrayList<>();
        Collections.addAll(result, items);
        return result;
    }
}
