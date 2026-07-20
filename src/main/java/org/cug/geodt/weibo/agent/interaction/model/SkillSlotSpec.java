package org.cug.geodt.weibo.agent.interaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillSlotSpec {
    private String name;
    private String label;
    private String description;
}
