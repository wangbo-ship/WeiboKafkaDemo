package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;

/**
 * Agent 交互请求。
 */
@Data
public class AgentInteractionRequest {
    /** 会话 ID，首轮可不传，系统自动生成 */
    private String conversationId;
    /** 用户本轮自然语言输入 */
    private String userMessage;
    /** 显式确认执行，优先级高于意图识别 */
    private Boolean confirmed;
    /** 显式取消当前任务 */
    private Boolean cancel;
}
