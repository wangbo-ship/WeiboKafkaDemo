package org.cug.geodt.weibo.agent.interaction.enums;

/**
 * 会话任务当前所处阶段。
 */
public enum InteractionStatus {
    /** 正在收集参数 */
    COLLECTING_PARAMS,
    /** 参数齐全，已生成计划，等待确认 */
    PLANNED,
    /** 用户已确认，正在执行 */
    EXECUTING,
    /** 执行成功 */
    COMPLETED,
    /** 执行失败 */
    FAILED,
    /** 用户取消 */
    CANCELLED
}
