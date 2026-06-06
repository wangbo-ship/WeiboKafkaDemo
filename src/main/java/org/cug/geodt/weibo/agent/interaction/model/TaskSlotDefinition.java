package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务类型对应的参数槽位定义。
 */
@Data
public class TaskSlotDefinition {
    private final String taskTypeCode;
    private final List<String> requiredSlots = new ArrayList<>();
    private final List<String> optionalSlots = new ArrayList<>();

    public TaskSlotDefinition(String taskTypeCode) {
        this.taskTypeCode = taskTypeCode;
    }

    public TaskSlotDefinition required(String... slots) {
        for (String slot : slots) {
            requiredSlots.add(slot);
        }
        return this;
    }

    public TaskSlotDefinition optional(String... slots) {
        for (String slot : slots) {
            optionalSlots.add(slot);
        }
        return this;
    }
}
