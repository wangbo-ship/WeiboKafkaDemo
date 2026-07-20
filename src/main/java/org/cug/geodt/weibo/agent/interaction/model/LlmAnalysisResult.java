package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;
import org.cug.geodt.weibo.agent.interaction.enums.TaskType;
import org.cug.geodt.weibo.agent.interaction.enums.UserIntent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * LLM 单轮分析结果（结构化 JSON 解析后）。
 */
@Data
public class LlmAnalysisResult {
    private UserIntent intent = UserIntent.UNKNOWN;
    private SkillDefinition skill;
    private TaskType mappedTaskType = TaskType.UNKNOWN;
    private Map<String, Object> slots = new LinkedHashMap<>();
    private List<String> missingInputs = new ArrayList<>();
    private ExecutionPlan plan;
    /** LLM 建议回复用户的自然语言 */
    private String replySuggestion;
}
