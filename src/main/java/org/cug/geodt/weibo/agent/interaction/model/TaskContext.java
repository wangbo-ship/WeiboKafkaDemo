package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 会话内任务上下文，支持多轮补参与状态追踪。
 */
@Data
public class TaskContext {
    private String conversationId;
    private String taskId;
    private TaskType taskType = TaskType.UNKNOWN;
    private InteractionStatus status = InteractionStatus.COLLECTING_PARAMS;
    /** 已抽取的业务参数槽位 */
    private Map<String, Object> slots = new LinkedHashMap<>();
    /** 仍缺失的参数名 */
    private List<String> missingInputs = new ArrayList<>();
    /** 用户是否已确认执行 */
    private boolean confirmed;
    /** 本轮识别到的用户意图 */
    private String lastUserIntent;
    /** 最近一轮用户原始输入 */
    private String lastUserMessage;
    /** 生成的中间产物，如 SOAP 预览、WFS 地址 */
    private Map<String, Object> artifacts = new LinkedHashMap<>();
    /** 执行计划快照 */
    private ExecutionPlan plan;
    /** 溯源步骤日志 */
    private List<StepLogEntry> stepLogs = new ArrayList<>();
    private long createdAt;
    private long updatedAt;
    private int turnCount;
}
