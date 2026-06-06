package org.cug.geodt.weibo.agent.interaction.service;

import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.model.TaskContext;

/**
 * 任务执行器，由 Skill 层实现并注册。
 */
public interface TaskExecutor {

    TaskType supportedTaskType();

    ExecutionResult execute(TaskContext context) throws Exception;

    class ExecutionResult {
        private final boolean success;
        private final String message;
        private final java.util.Map<String, Object> artifacts;

        public ExecutionResult(boolean success, String message, java.util.Map<String, Object> artifacts) {
            this.success = success;
            this.message = message;
            this.artifacts = artifacts;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public java.util.Map<String, Object> getArtifacts() {
            return artifacts;
        }
    }
}
