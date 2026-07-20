package org.cug.geodt.weibo.agent.interaction.enums;

/**
 * 业务任务类型，对应后续 Skill 层。
 */
public enum TaskType {
    SOS_TO_WFS("sos_to_wfs", "SOS 查询并发布 WFS"),
    WFS_SPATIAL_ANALYSIS("wfs_spatial_analysis", "WFS 图层空间分析"),
    WEIBO_EVENT_ANALYSIS("weibo_event_analysis", "微博事件分析"),
    DYNAMIC("dynamic", "LLM 动态生成的 Skill"),
    UNKNOWN("unknown", "未识别任务");

    private final String code;
    private final String label;

    TaskType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static TaskType fromCode(String code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (TaskType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
