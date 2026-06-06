package org.cug.geodt.weibo.agent.interaction.provenance;

import org.cug.geodt.weibo.agent.interaction.model.ProvenanceChain;
import org.cug.geodt.weibo.agent.interaction.model.StepLogEntry;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 数据溯源服务：记录并查询完整证据链。
 */
@Service
public class ProvenanceService {

    public StepLogEntry recordInteraction(TaskContext context, String phase, String input, String output, boolean success) {
        StepLogEntry entry = baseEntry(context, phase, null, null);
        entry.setInputSnapshot(truncate(input));
        entry.setOutputSnapshot(truncate(output));
        entry.setSuccess(success);
        context.getStepLogs().add(entry);
        return entry;
    }

    public StepLogEntry recordToolCall(TaskContext context, String skillName, String toolName,
                                       String inputJson, String outputPreview, boolean success, String errorMessage) {
        StepLogEntry entry = baseEntry(context, "tool_execution", skillName, toolName);
        entry.setCallId(UUID.randomUUID().toString());
        entry.setInputSnapshot(truncate(inputJson));
        entry.setOutputSnapshot(truncate(outputPreview));
        entry.setSuccess(success);
        entry.setErrorMessage(errorMessage);
        context.getStepLogs().add(entry);
        return entry;
    }

    public ProvenanceChain buildChain(TaskContext context) {
        ProvenanceChain chain = new ProvenanceChain();
        chain.setConversationId(context.getConversationId());
        chain.setTaskId(context.getTaskId());
        chain.setSteps(context.getStepLogs());
        chain.setArtifacts(context.getArtifacts());
        chain.setCreatedAt(context.getCreatedAt());
        chain.setUpdatedAt(context.getUpdatedAt());
        return chain;
    }

    private StepLogEntry baseEntry(TaskContext context, String phase, String skillName, String toolName) {
        StepLogEntry entry = new StepLogEntry();
        entry.setStepIndex(context.getStepLogs().size() + 1);
        entry.setPhase(phase);
        entry.setSkillName(skillName);
        entry.setToolName(toolName);
        entry.setTimestamp(System.currentTimeMillis());
        return entry;
    }

    private String truncate(String value) {
        if (value == null) {
            return null;
        }
        return value.length() <= 2000 ? value : value.substring(0, 2000) + "...[truncated]";
    }
}
