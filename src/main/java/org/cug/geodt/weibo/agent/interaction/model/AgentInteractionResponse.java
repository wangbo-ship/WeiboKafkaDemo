package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Agent 交互响应，面向用户的可理解摘要（非原始思维链）。
 */
@Data
public class AgentInteractionResponse {
    private String conversationId;
    private String taskId;
    private InteractionStatus status;
    private TaskType taskType;
    private UserIntent recognizedIntent;
    /** 面向用户的理解摘要 */
    private String understandingSummary;
    /** 已抽取参数 */
    private Map<String, Object> extractedParams = new LinkedHashMap<>();
    /** 缺失参数 */
    private List<String> missingInputs = new ArrayList<>();
    /** 执行计划 */
    private ExecutionPlan plan;
    /** 是否可执行 */
    private Boolean canExecute;
    /** 是否需要用户确认 */
    private Boolean needConfirmation;
    /** 最终回复文本 */
    private String replyText;
    /** 结果服务地址等 */
    private Map<String, Object> resultArtifacts = new LinkedHashMap<>();
    /** 溯源步骤 */
    private List<StepLogEntry> stepLogs = new ArrayList<>();
    /** 任务追踪视图（状态 + 参数快照 + 步骤日志 + 产物） */
    private TaskTrace taskTrace;
    private int turnCount;
}
