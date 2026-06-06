package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行计划：将调用哪些 Skill 和 Tool。
 */
@Data
public class ExecutionPlan {
    private List<String> summaryPoints = new ArrayList<>();
    private List<String> skills = new ArrayList<>();
    private List<String> tools = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private boolean executable;
}
