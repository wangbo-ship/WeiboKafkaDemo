package org.cug.geodt.weibo.agent.interaction.enums;

/**
 * 用户本轮输入的意图分类，由 LLM 或规则引擎识别。
 */
public enum UserIntent {
    /** 发起新任务或描述任务目标 */
    NEW_TASK,
    /** 补充缺失参数 */
    SUPPLY_PARAMS,
    /** 确认执行计划 */
    CONFIRM_EXECUTE,
    /** 修改已有参数或变更任务 */
    CHANGE_TASK,
    /** 取消当前任务 */
    CANCEL,
    /** 无法识别 */
    UNKNOWN
}
