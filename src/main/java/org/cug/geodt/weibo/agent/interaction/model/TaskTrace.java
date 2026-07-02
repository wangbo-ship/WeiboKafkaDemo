package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务追踪视图：整合会话状态、参数快照、步骤日志与结果产物，供溯源与汇报使用。
 */
@Data
public class TaskTrace {
    private String conversationId;
    private String taskId;
    private TaskType taskType;
    private InteractionStatus status;
    /** 面向用户的理解摘要（非模型原始思维链） */
    private String understandingSummary;
    /** 已抽取参数快照 */
    private Map<String, Object> inputSnapshot = new LinkedHashMap<>();
    /** 仍缺失的参数键 */
    private List<String> missingInputs = new ArrayList<>();
    /** 逐步执行日志 */
    private List<StepLogEntry> stepLogs = new ArrayList<>();
    /** 中间/最终结果产物 */
    private Map<String, Object> resultArtifacts = new LinkedHashMap<>();
    private boolean confirmed;
    private int turnCount;
    private long createdAt;
    private long updatedAt;
}
