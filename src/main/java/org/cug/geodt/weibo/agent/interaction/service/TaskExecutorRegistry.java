package org.cug.geodt.weibo.agent.interaction.service;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 任务执行器注册表，按 TaskType 路由到对应 Skill 执行器。
 */
@Component
public class TaskExecutorRegistry {

    private final Map<TaskType, TaskExecutor> executors = new EnumMap<>(TaskType.class);

    public TaskExecutorRegistry(List<TaskExecutor> executorList) {
        for (TaskExecutor executor : executorList) {
            executors.put(executor.supportedTaskType(), executor);
        }
    }

    public TaskExecutor getExecutor(TaskType taskType) {
        return executors.get(taskType);
    }
}
