package org.cug.geodt.weibo.agent.interaction.service.executor;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.SkillDefinition;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.provenance.ProvenanceService;
import org.cug.geodt.weibo.agent.interaction.service.TaskExecutor;
import org.cug.geodt.weibo.agent.interaction.tool.AgentToolRegistry;
import org.cug.geodt.weibo.agent.model.ToolResponseEnvelope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行 LLM 动态生成的 Skill：按 plan 中的 tools 顺序调用 AgentToolRegistry。
 */
@Component
public class DynamicSkillExecutor implements TaskExecutor {

    private final AgentToolRegistry toolRegistry;
    private final ProvenanceService provenanceService;

    public DynamicSkillExecutor(AgentToolRegistry toolRegistry, ProvenanceService provenanceService) {
        this.toolRegistry = toolRegistry;
        this.provenanceService = provenanceService;
    }

    @Override
    public TaskType supportedTaskType() {
        return TaskType.DYNAMIC;
    }

    @Override
    public ExecutionResult execute(TaskContext context) {
        SkillDefinition skill = context.getSkillDefinition();
        if (skill == null) {
            return new ExecutionResult(false, "未找到 Skill 定义，无法执行。", context.getArtifacts());
        }

        List<String> tools = skill.getTools();
        if (tools == null || tools.isEmpty()) {
            return new ExecutionResult(false,
                    "Skill「" + skill.getSkillName() + "」未规划任何 Tool，请先完善 Skill 或注册 Tool。",
                    context.getArtifacts());
        }

        Map<String, Object> artifacts = context.getArtifacts();
        StringBuilder message = new StringBuilder();
        boolean allSuccess = true;

        for (String toolName : tools) {
            ToolResponseEnvelope result = toolRegistry.invoke(toolName, context);
            provenanceService.recordToolCall(context, skill.getSkillId(), toolName,
                    String.valueOf(context.getSlots()), preview(result), result.isSuccess(),
                    result.isSuccess() ? null : result.getMessage());

            if (!result.isSuccess()) {
                allSuccess = false;
                message.append("Tool ").append(toolName).append(" 失败：").append(result.getMessage()).append("\n");
                if ("NOT_IMPLEMENTED".equals(errorCode(result))
                        || "TOOL_NOT_FOUND".equals(errorCode(result))) {
                    message.append("（该 Tool 可后续自行实现或通过 AgentToolRegistry 注册开源 Tool）\n");
                }
                break;
            }
            mergeArtifacts(artifacts, result.getData());
            if (result.getData() != null && result.getData().get("sosResponseXml") != null) {
                artifacts.put("sosResponseXml", result.getData().get("sosResponseXml"));
            }
            if (result.getData() != null && result.getData().get("wfsUrl") != null) {
                artifacts.put("wfsUrl", result.getData().get("wfsUrl"));
            }
            message.append("Tool ").append(toolName).append(" 成功。");
        }

        if (allSuccess && artifacts.get("wfsUrl") != null) {
            message.append(" WFS URL: ").append(artifacts.get("wfsUrl"));
        }
        return new ExecutionResult(allSuccess, message.toString().trim(), artifacts);
    }

    private void mergeArtifacts(Map<String, Object> artifacts, Map<String, Object> data) {
        if (data == null) {
            return;
        }
        artifacts.putAll(data);
    }

    private String preview(ToolResponseEnvelope result) {
        if (result == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder("success=").append(result.isSuccess());
        builder.append(", message=").append(result.getMessage());
        if (result.getData() != null && result.getData().get("wfsUrl") != null) {
            builder.append(", wfsUrl=").append(result.getData().get("wfsUrl"));
        }
        return builder.toString();
    }

    private String errorCode(ToolResponseEnvelope result) {
        if (result.getError() == null || result.getError().get("code") == null) {
            return null;
        }
        return String.valueOf(result.getError().get("code"));
    }
}
