package org.cug.geodt.weibo.agent.interaction.provenance;

import org.cug.geodt.weibo.agent.interaction.model.ProvenanceChain;
import org.cug.geodt.weibo.agent.interaction.model.StepLogEntry;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;
import org.cug.geodt.weibo.agent.interaction.model.TaskTrace;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * 数据溯源与任务追踪：记录交互/规划/执行各阶段证据，并组装完整追踪视图。
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

    public StepLogEntry recordExecutionSummary(TaskContext context, boolean success, String message) {
        return recordInteraction(context, "execution_summary",
                String.valueOf(context.getSlots()), message, success);
    }

    public ProvenanceChain buildChain(TaskContext context) {
        ProvenanceChain chain = new ProvenanceChain();
        chain.setConversationId(context.getConversationId());
        chain.setTaskId(context.getTaskId());
        chain.setTaskType(context.getTaskType());
        chain.setStatus(context.getStatus());
        chain.setSteps(new ArrayList<>(context.getStepLogs()));
        chain.setArtifacts(new LinkedHashMap<>(context.getArtifacts()));
        chain.setInputSnapshot(new LinkedHashMap<>(context.getSlots()));
        chain.setCreatedAt(context.getCreatedAt());
        chain.setUpdatedAt(context.getUpdatedAt());
        return chain;
    }

    public TaskTrace buildTaskTrace(TaskContext context, String understandingSummary) {
        TaskTrace trace = new TaskTrace();
        trace.setConversationId(context.getConversationId());
        trace.setTaskId(context.getTaskId());
        trace.setTaskType(context.getTaskType());
        trace.setStatus(context.getStatus());
        trace.setUnderstandingSummary(understandingSummary);
        trace.setInputSnapshot(new LinkedHashMap<>(context.getSlots()));
        trace.setMissingInputs(new ArrayList<>(context.getMissingInputs()));
        trace.setStepLogs(new ArrayList<>(context.getStepLogs()));
        trace.setResultArtifacts(new LinkedHashMap<>(context.getArtifacts()));
        trace.setConfirmed(context.isConfirmed());
        trace.setTurnCount(context.getTurnCount());
        trace.setCreatedAt(context.getCreatedAt());
        trace.setUpdatedAt(context.getUpdatedAt());
        return trace;
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
