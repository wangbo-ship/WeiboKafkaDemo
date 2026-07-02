package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;
import org.cug.geodt.weibo.agent.interaction.enums.InteractionStatus;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 完整溯源链，记录从用户输入到最终结果的全过程证据。
 */
@Data
public class ProvenanceChain {
    private String conversationId;
    private String taskId;
    private TaskType taskType;
    private InteractionStatus status;
    private List<StepLogEntry> steps = new ArrayList<>();
    private Map<String, Object> inputSnapshot = new LinkedHashMap<>();
    private Map<String, Object> artifacts = new LinkedHashMap<>();
    private long createdAt;
    private long updatedAt;
}
