package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Skill 定义：可由内置模板选用，也可由 LLM 在对话中动态生成。
 */
@Data
public class SkillDefinition {
    private String skillId;
    private String skillName;
    private String description;
    /** 是否 LLM 动态生成（非内置三件套） */
    private boolean dynamicallyGenerated;
    private List<SkillSlotSpec> requiredSlots = new ArrayList<>();
    private List<SkillSlotSpec> optionalSlots = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private List<String> tools = new ArrayList<>();
    /** 底层 Tool 是否已在本系统注册可执行 */
    private boolean toolsAvailable;
}
