package org.cug.geodt.weibo.agent.interaction.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 单步执行溯源记录。
 */
@Data
public class StepLogEntry {
    private int stepIndex;
    private String phase;
    private String skillName;
    private String toolName;
    private String callId;
    private String inputSnapshot;
    private String outputSnapshot;
    private boolean success;
    private String errorMessage;
    private long timestamp;
    private Map<String, Object> metadata = new LinkedHashMap<>();
}
