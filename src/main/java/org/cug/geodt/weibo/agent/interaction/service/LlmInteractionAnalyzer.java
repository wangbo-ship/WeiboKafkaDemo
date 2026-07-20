package org.cug.geodt.weibo.agent.interaction.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;
import org.cug.geodt.weibo.agent.interaction.llm.LlmChatClient;
import org.cug.geodt.weibo.agent.interaction.model.ExecutionPlan;
import org.cug.geodt.weibo.agent.interaction.model.LlmAnalysisResult;
import org.cug.geodt.weibo.agent.interaction.model.SkillDefinition;
import org.cug.geodt.weibo.agent.interaction.model.SkillSlotSpec;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.skill.SkillCatalog;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用 LLM 完成：意图识别、Skill 匹配/动态生成、参数抽取、执行计划构建。
 * 替代原 ParameterExtractionService / IntentRecognitionService / TaskPlanningService。
 */
@Service
@Slf4j
public class LlmInteractionAnalyzer {

    private static final String SYSTEM_PROMPT =
            "你是 GeoDT 业务 Agent 的交互理解模块。根据用户对话和会话上下文，完成：\n"
                    + "1) 识别用户意图 intent\n"
                    + "2) 选择内置 Skill 或动态生成新 Skill（skill 对象）\n"
                    + "3) 合并抽取/更新参数 slots（保留已有有效值，仅在新信息出现时覆盖）\n"
                    + "4) 计算 missingInputs（必填但未提供的 slot 名）\n"
                    + "5) 给出 replySuggestion（面向用户的简短中文回复）\n\n"
                    + "规则：\n"
                    + "- 优先选用内置 Skill；若无合适内置 Skill，可 dynamic 生成 skillId（snake_case）、skillName、description、requiredSlots、steps、tools\n"
                    + "- 动态 Skill 的 tools 只能从「已注册 Tool 列表」中选择；若需要新能力，在 steps 中说明并选最接近的已有 tools\n"
                    + "- 【重要】slots 必须基于 currentSlots 做合并：已有有效值必须原样保留，仅覆盖本轮明确提供的新值；禁止因补参而清空已有 city/beginDate/endDate 等\n"
                    + "- missingInputs 只能包含「合并后的 slots」里仍缺失的必填项，不能要求已在 currentSlots 中的字段\n"
                    + "- 用户只补充观测属性等单项时 intent=SUPPLY_PARAMS，不要判成 NEW_TASK\n"
                    + "- 用户明确确认执行时 intent=CONFIRM_EXECUTE；明确取消时 intent=CANCEL\n"
                    + "- 用户修改任务 intent=CHANGE_TASK；首轮描述任务 intent=NEW_TASK\n"
                    + "- 日期格式统一 YYYY-MM-DD；城市可带「市」；文中「情感得分」应写入 observedProperty\n"
                    + "- 仅输出一个 JSON 对象，不要 markdown，不要解释\n\n"
                    + "intent 枚举：NEW_TASK, SUPPLY_PARAMS, CONFIRM_EXECUTE, CANCEL, CHANGE_TASK, UNKNOWN\n\n"
                    + "输出 JSON 结构：\n"
                    + "{\n"
                    + "  \"intent\": \"NEW_TASK\",\n"
                    + "  \"skill\": {\n"
                    + "    \"skillId\": \"sos_to_wfs\",\n"
                    + "    \"skillName\": \"...\",\n"
                    + "    \"description\": \"...\",\n"
                    + "    \"dynamicallyGenerated\": false,\n"
                    + "    \"requiredSlots\": [{\"name\":\"city\",\"label\":\"城市\",\"description\":\"...\"}],\n"
                    + "    \"optionalSlots\": [],\n"
                    + "    \"steps\": [\"步骤1\", \"步骤2\"],\n"
                    + "    \"tools\": [\"query_sos\"]\n"
                    + "  },\n"
                    + "  \"slots\": {\"city\":\"武汉市\"},\n"
                    + "  \"missingInputs\": [\"beginDate\"],\n"
                    + "  \"replySuggestion\": \"...\"\n"
                    + "}";

    private final LlmChatClient chatClient;
    private final SkillCatalog skillCatalog;
    private final SlotDerivationHelper slotDerivationHelper;
    private final Gson gson = new Gson();

    public LlmInteractionAnalyzer(LlmChatClient chatClient,
                                    SkillCatalog skillCatalog,
                                    SlotDerivationHelper slotDerivationHelper) {
        this.chatClient = chatClient;
        this.skillCatalog = skillCatalog;
        this.slotDerivationHelper = slotDerivationHelper;
    }

    public LlmAnalysisResult analyze(TaskContext context,
                                       String userMessage,
                                       Boolean explicitConfirmed,
                                       Boolean explicitCancel) {
        if (Boolean.TRUE.equals(explicitCancel)) {
            return buildExplicitResult(context, UserIntent.CANCEL, "已取消当前任务。");
        }
        if (Boolean.TRUE.equals(explicitConfirmed) && !StringUtils.hasText(userMessage)) {
            LlmAnalysisResult result = buildExplicitResult(context, UserIntent.CONFIRM_EXECUTE, null);
            result.setPlan(buildPlan(context.getSkillDefinition(), context.getSlots(), context.getMissingInputs()));
            return result;
        }

        String llmRaw = chatClient.chat(SYSTEM_PROMPT, buildUserPrompt(context, userMessage));
        log.debug("LLM raw response: {}", llmRaw);
        LlmAnalysisResult result = parseResponse(llmRaw);

        if (Boolean.TRUE.equals(explicitConfirmed)) {
            result.setIntent(UserIntent.CONFIRM_EXECUTE);
        }
        if (Boolean.TRUE.equals(explicitCancel)) {
            result.setIntent(UserIntent.CANCEL);
        }

        applySkillMetadata(result);
        // plan / missing 以 applyToContext 合并后为准，此处不落最终态
        return result;
    }

    public void applyToContext(TaskContext context, LlmAnalysisResult result) {
        if (result.getSkill() != null) {
            context.setSkillDefinition(result.getSkill());
            context.setSkillId(result.getSkill().getSkillId());
            context.setTaskType(result.getMappedTaskType());
        } else if (context.getSkillDefinition() != null) {
            // 补参轮次模型偶发不回 skill，保留会话已有 Skill
            result.setSkill(context.getSkillDefinition());
            result.setMappedTaskType(context.getTaskType());
        }

        Map<String, Object> previousSlots = new LinkedHashMap<>(context.getSlots());
        Map<String, Object> merged = new LinkedHashMap<>(previousSlots);
        mergeSlots(merged, result.getSlots(), context.getLastUserMessage());
        context.setSlots(merged);
        slotDerivationHelper.enrich(context);

        SkillDefinition skill = context.getSkillDefinition();
        List<String> missing = calculateMissingInputs(skill, context.getSlots());
        context.setMissingInputs(missing);
        ExecutionPlan plan = buildPlan(skill, context.getSlots(), missing);
        context.setPlan(plan);

        UserIntent intent = refineIntent(result.getIntent(), context, previousSlots);
        result.setIntent(intent);
        context.setLastUserIntent(intent.name());

        result.setSkill(skill);
        result.setMappedTaskType(context.getTaskType());
        result.setSlots(new LinkedHashMap<>(context.getSlots()));
        result.setMissingInputs(new ArrayList<>(missing));
        result.setPlan(plan);
        if (intent != UserIntent.CANCEL && intent != UserIntent.CONFIRM_EXECUTE) {
            result.setReplySuggestion(rebuildReplySuggestion(result.getReplySuggestion(), skill, missing));
        }
    }

    /**
     * 合并槽位：保留已有有效值；布尔 false 默认不覆盖已有 true（除非用户明确说不发布）。
     */
    private void mergeSlots(Map<String, Object> target, Map<String, Object> incoming, String userMessage) {
        if (incoming == null) {
            return;
        }
        String msg = userMessage == null ? "" : userMessage;
        boolean userClearsPublish = msg.contains("不发布") || msg.contains("无需发布") || msg.contains("不要发布");
        incoming.forEach((key, value) -> {
            if (value == null) {
                return;
            }
            if ("shouldPublishWfs".equals(key)) {
                boolean newVal = Boolean.TRUE.equals(value) || "true".equalsIgnoreCase(String.valueOf(value));
                Object oldVal = target.get(key);
                if (newVal) {
                    target.put(key, true);
                } else if (userClearsPublish || oldVal == null) {
                    target.put(key, false);
                }
                // 否则保留旧的 true，避免补参时被模型误写成 false
                return;
            }
            if (StringUtils.hasText(String.valueOf(value))) {
                target.put(key, value);
            }
        });
    }

    private List<String> calculateMissingInputs(SkillDefinition skill, Map<String, Object> slots) {
        List<String> missing = new ArrayList<>();
        if (skill == null) {
            missing.add("taskType");
            return missing;
        }
        for (SkillSlotSpec spec : skill.getRequiredSlots()) {
            Object value = slots.get(spec.getName());
            if (value == null || !StringUtils.hasText(String.valueOf(value))) {
                missing.add(spec.getName());
            }
        }
        // SOS 场景：有城市但无法映射 FOI 时提示
        if ("sos_to_wfs".equals(skill.getSkillId())
                && slots.get("city") != null
                && StringUtils.hasText(String.valueOf(slots.get("city")))
                && !StringUtils.hasText(String.valueOf(slots.get("featureOfInterest")))) {
            missing.add("supportedCity");
        }
        return missing;
    }

    private UserIntent refineIntent(UserIntent intent, TaskContext context, Map<String, Object> previousSlots) {
        if (intent == UserIntent.CONFIRM_EXECUTE || intent == UserIntent.CANCEL || intent == UserIntent.CHANGE_TASK) {
            return intent;
        }
        if (context.getTurnCount() > 0 && !previousSlots.isEmpty()
                && (intent == UserIntent.NEW_TASK || intent == UserIntent.UNKNOWN)) {
            return UserIntent.SUPPLY_PARAMS;
        }
        return intent == null ? UserIntent.UNKNOWN : intent;
    }

    private String rebuildReplySuggestion(String llmReply, SkillDefinition skill, List<String> missing) {
        if (missing == null || missing.isEmpty()) {
            if (StringUtils.hasText(llmReply) && !llmReply.contains("缺少") && !llmReply.contains("请提供")) {
                return llmReply;
            }
            return "参数已齐全，已生成执行计划。是否确认执行？";
        }
        String labels = missing.stream()
                .map(key -> labelOf(skill, key))
                .collect(java.util.stream.Collectors.joining("、"));
        return "我已记录本轮参数，当前还缺少：" + labels + "。请补充后继续。";
    }

    private String labelOf(SkillDefinition skill, String key) {
        if (skill != null) {
            for (SkillSlotSpec spec : skill.getRequiredSlots()) {
                if (key.equals(spec.getName()) && StringUtils.hasText(spec.getLabel())) {
                    return spec.getLabel();
                }
            }
            for (SkillSlotSpec spec : skill.getOptionalSlots()) {
                if (key.equals(spec.getName()) && StringUtils.hasText(spec.getLabel())) {
                    return spec.getLabel();
                }
            }
        }
        return key;
    }

    private LlmAnalysisResult buildExplicitResult(TaskContext context, UserIntent intent, String reply) {
        LlmAnalysisResult result = new LlmAnalysisResult();
        result.setIntent(intent);
        result.setSkill(context.getSkillDefinition());
        result.setMappedTaskType(context.getTaskType());
        result.setSlots(new LinkedHashMap<>(context.getSlots()));
        result.setMissingInputs(new ArrayList<>(context.getMissingInputs()));
        result.setReplySuggestion(reply);
        return result;
    }

    private String buildUserPrompt(TaskContext context, String userMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("## 内置 Skill 模板\n").append(skillCatalog.buildCatalogPrompt()).append("\n\n");
        sb.append("## 已注册 Tool\n").append(skillCatalog.buildToolCatalogPrompt()).append("\n\n");
        sb.append("## 当前会话上下文\n");
        sb.append("- turnCount: ").append(context.getTurnCount()).append("\n");
        sb.append("- currentSkillId: ").append(context.getSkillId()).append("\n");
        sb.append("- currentTaskType: ").append(context.getTaskType()).append("\n");
        sb.append("- currentSlots: ").append(gson.toJson(context.getSlots())).append("\n");
        sb.append("- currentMissingInputs: ").append(gson.toJson(context.getMissingInputs())).append("\n");
        if (context.getSkillDefinition() != null) {
            sb.append("- currentSkill: ").append(gson.toJson(context.getSkillDefinition())).append("\n");
        }
        sb.append("\n## 用户本轮输入\n").append(userMessage == null ? "" : userMessage);
        sb.append("\n\n请在 slots 中保留 currentSlots 已有字段，并合并本轮新信息后输出完整 slots。");
        return sb.toString();
    }

    private LlmAnalysisResult parseResponse(String raw) {
        String json = extractJsonObject(raw);
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        LlmAnalysisResult result = new LlmAnalysisResult();
        result.setIntent(parseIntent(root));
        result.setSkill(parseSkill(root.getAsJsonObject("skill")));
        result.setSlots(parseSlots(root.getAsJsonObject("slots")));
        result.setMissingInputs(parseStringList(root.getAsJsonArray("missingInputs")));
        if (root.has("replySuggestion") && !root.get("replySuggestion").isJsonNull()) {
            result.setReplySuggestion(root.get("replySuggestion").getAsString());
        }
        applySkillMetadata(result);
        result.setPlan(buildPlan(result.getSkill(), result.getSlots(), result.getMissingInputs()));
        return result;
    }

    private void applySkillMetadata(LlmAnalysisResult result) {
        if (result.getSkill() != null) {
            SkillDefinition normalized = skillCatalog.normalize(result.getSkill());
            result.setSkill(normalized);
            result.setMappedTaskType(skillCatalog.mapToTaskType(normalized.getSkillId()));
        } else {
            result.setMappedTaskType(TaskType.UNKNOWN);
        }
    }

    private ExecutionPlan buildPlan(SkillDefinition skill,
                                    Map<String, Object> slots,
                                    List<String> missingInputs) {
        ExecutionPlan plan = new ExecutionPlan();
        if (skill == null) {
            plan.getSummaryPoints().add("尚未识别到明确的 Skill");
            plan.setExecutable(false);
            return plan;
        }
        plan.getSkills().add(skill.getSkillId());
        plan.getTools().addAll(skill.getTools());
        plan.getSteps().addAll(skill.getSteps());
        plan.getSummaryPoints().add("Skill：" + skill.getSkillName());
        if (StringUtils.hasText(skill.getDescription())) {
            plan.getSummaryPoints().add(skill.getDescription());
        }
        if (skill.isDynamicallyGenerated()) {
            plan.getSummaryPoints().add("（LLM 动态生成的 Skill）");
        }
        for (SkillSlotSpec spec : skill.getRequiredSlots()) {
            Object val = slots.get(spec.getName());
            if (val != null) {
                plan.getSummaryPoints().add(spec.getLabel() + "：" + val);
            }
        }
        boolean executable = missingInputs == null || missingInputs.isEmpty();
        if (!executable) {
            plan.getSummaryPoints().add("参数尚未齐全，需继续补充");
        } else if (!skill.isToolsAvailable() && skill.isDynamicallyGenerated()) {
            plan.getSummaryPoints().add("部分 Tool 尚未注册，确认后将尝试执行或返回占位提示");
        }
        plan.setExecutable(executable);
        return plan;
    }

    private UserIntent parseIntent(JsonObject root) {
        if (!root.has("intent")) {
            return UserIntent.UNKNOWN;
        }
        try {
            return UserIntent.valueOf(root.get("intent").getAsString().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return UserIntent.UNKNOWN;
        }
    }

    private SkillDefinition parseSkill(JsonObject skillJson) {
        if (skillJson == null) {
            return null;
        }
        SkillDefinition skill = new SkillDefinition();
        skill.setSkillId(getAsString(skillJson, "skillId"));
        skill.setSkillName(getAsString(skillJson, "skillName"));
        skill.setDescription(getAsString(skillJson, "description"));
        skill.setDynamicallyGenerated(skillJson.has("dynamicallyGenerated")
                && skillJson.get("dynamicallyGenerated").getAsBoolean());
        skill.setRequiredSlots(parseSlotSpecs(skillJson.getAsJsonArray("requiredSlots")));
        skill.setOptionalSlots(parseSlotSpecs(skillJson.getAsJsonArray("optionalSlots")));
        skill.setSteps(parseStringList(skillJson.getAsJsonArray("steps")));
        skill.setTools(parseStringList(skillJson.getAsJsonArray("tools")));
        return skill;
    }

    private List<SkillSlotSpec> parseSlotSpecs(JsonArray array) {
        List<SkillSlotSpec> specs = new ArrayList<>();
        if (array == null) {
            return specs;
        }
        for (JsonElement element : array) {
            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();
                specs.add(new SkillSlotSpec(
                        getAsString(obj, "name"),
                        getAsString(obj, "label"),
                        getAsString(obj, "description")
                ));
            } else if (element.isJsonPrimitive()) {
                String name = element.getAsString();
                specs.add(new SkillSlotSpec(name, name, name));
            }
        }
        return specs;
    }

    private Map<String, Object> parseSlots(JsonObject slotsJson) {
        Map<String, Object> slots = new LinkedHashMap<>();
        if (slotsJson == null) {
            return slots;
        }
        for (Map.Entry<String, JsonElement> entry : slotsJson.entrySet()) {
            JsonElement val = entry.getValue();
            if (val.isJsonNull()) {
                continue;
            }
            if (val.isJsonPrimitive()) {
                if (val.getAsJsonPrimitive().isBoolean()) {
                    slots.put(entry.getKey(), val.getAsBoolean());
                } else if (val.getAsJsonPrimitive().isNumber()) {
                    slots.put(entry.getKey(), val.getAsNumber());
                } else {
                    slots.put(entry.getKey(), val.getAsString());
                }
            } else {
                slots.put(entry.getKey(), val.toString());
            }
        }
        return slots;
    }

    private List<String> parseStringList(JsonArray array) {
        List<String> list = new ArrayList<>();
        if (array == null) {
            return list;
        }
        for (JsonElement element : array) {
            if (!element.isJsonNull()) {
                list.add(element.getAsString());
            }
        }
        return list;
    }

    private String getAsString(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull()) {
            return null;
        }
        return obj.get(key).getAsString();
    }

    private String extractJsonObject(String raw) {
        if (!StringUtils.hasText(raw)) {
            throw new IllegalStateException("LLM 返回为空");
        }
        String trimmed = raw.trim();
        if (trimmed.startsWith("```")) {
            int start = trimmed.indexOf('{');
            int end = trimmed.lastIndexOf('}');
            if (start >= 0 && end > start) {
                return trimmed.substring(start, end + 1);
            }
        }
        int start = trimmed.indexOf('{');
        int end = trimmed.lastIndexOf('}');
        if (start >= 0 && end > start) {
            return trimmed.substring(start, end + 1);
        }
        return trimmed;
    }
}
