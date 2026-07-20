package org.cug.geodt.weibo.agent.interaction.tool;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.service.SosSoapBuilder;
import org.cug.geodt.weibo.agent.model.ToolResponseEnvelope;
import org.cug.geodt.weibo.agent.service.SosAgentToolService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Agent Tool 注册表。动态 Skill 按 LLM 规划的 tool 名称依次调用已注册 Tool。
 */
@Component
@Slf4j
public class AgentToolRegistry {

    private final Map<String, Function<TaskContext, ToolResponseEnvelope>> tools = new LinkedHashMap<>();
    private final Map<String, String> descriptions = new LinkedHashMap<>();

    public AgentToolRegistry(SosAgentToolService sosAgentToolService, SosSoapBuilder sosSoapBuilder) {
        register("query_sos", "查询 SOS 观测数据", ctx -> {
            String xml = stringVal(ctx.getArtifacts().get("generatedSoapPreview"));
            if (!StringUtils.hasText(xml)) {
                xml = sosSoapBuilder.buildFromSlots(ctx.getSlots());
                ctx.getArtifacts().put("generatedSoapPreview", xml);
            }
            return sosAgentToolService.querySos(xml);
        });
        register("publish_wfs", "将 SOS 结果发布为 WFS", ctx -> {
            String sosResponse = stringVal(ctx.getArtifacts().get("sosResponseXml"));
            return sosAgentToolService.publishWfs(sosResponse);
        });
        register("get_wfs_features", "读取 WFS 图层要素（占位）", ctx ->
                ToolResponseEnvelope.fail("get_wfs_features", "Tool 尚未实现", "NOT_IMPLEMENTED", null));
        register("run_spatial_analysis", "执行空间分析（占位）", ctx ->
                ToolResponseEnvelope.fail("run_spatial_analysis", "Tool 尚未实现", "NOT_IMPLEMENTED", null));
        register("crawl_weibo", "抓取微博数据（占位）", ctx ->
                ToolResponseEnvelope.fail("crawl_weibo", "Tool 尚未实现", "NOT_IMPLEMENTED", null));
        register("query_geomesa", "查询 GeoMesa（占位）", ctx ->
                ToolResponseEnvelope.fail("query_geomesa", "Tool 尚未实现", "NOT_IMPLEMENTED", null));
    }

    public void register(String toolName, String description, Function<TaskContext, ToolResponseEnvelope> handler) {
        tools.put(toolName, handler);
        descriptions.put(toolName, description);
    }

    public boolean isRegistered(String toolName) {
        return tools.containsKey(toolName);
    }

    public Set<String> listToolNames() {
        return tools.keySet();
    }

    public ToolResponseEnvelope invoke(String toolName, TaskContext context) {
        Function<TaskContext, ToolResponseEnvelope> handler = tools.get(toolName);
        if (handler == null) {
            return ToolResponseEnvelope.fail(toolName, "Tool 未注册: " + toolName, "TOOL_NOT_FOUND", null);
        }
        log.info("Invoking tool={} conversationId={}", toolName, context.getConversationId());
        return handler.apply(context);
    }

    public String buildToolCatalogPrompt() {
        StringBuilder sb = new StringBuilder("[\n");
        int i = 0;
        for (Map.Entry<String, String> entry : descriptions.entrySet()) {
            if (i++ > 0) {
                sb.append(",\n");
            }
            sb.append("  {\"name\":\"").append(entry.getKey())
                    .append("\",\"description\":\"").append(entry.getValue()).append("\"}");
        }
        sb.append("\n]");
        return sb.toString();
    }

    private static String stringVal(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}
