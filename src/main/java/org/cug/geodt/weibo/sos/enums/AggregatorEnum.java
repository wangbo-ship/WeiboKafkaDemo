package org.cug.geodt.weibo.sos.enums;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.enums
 * @Description
 * @date 2023/1/3 15:52
 */
public enum AggregatorEnum {
    MAX("最大值","max"),
    MIN("最小值","min"),
    AVERAGE("均值","avg"),
    SUM("求和","sum"),
    COUNT("计数","count");
    private final String note;
    private final String value;

    AggregatorEnum(String note,String value) {
        this.note = note;
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public String getValue() {
        return value;
    }
}
