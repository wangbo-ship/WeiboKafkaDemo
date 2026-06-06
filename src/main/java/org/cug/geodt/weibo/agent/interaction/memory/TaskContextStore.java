package org.cug.geodt.weibo.agent.interaction.memory;

import org.cug.geodt.weibo.agent.interaction.model.TaskContext;

import java.util.Optional;

/**
 * 会话任务上下文存储，后续可替换为 Redis / DB 实现。
 */
public interface TaskContextStore {

    TaskContext create(String conversationId);

    Optional<TaskContext> get(String conversationId);

    void save(TaskContext context);

    void delete(String conversationId);
}
