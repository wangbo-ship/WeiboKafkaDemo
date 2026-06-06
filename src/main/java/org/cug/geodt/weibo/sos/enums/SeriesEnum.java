package org.cug.geodt.weibo.sos.enums;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.enums
 * @Description
 * @date 2023/5/12 10:48
 */
public enum SeriesEnum {
    BOOLEAN("布尔","Boolean"),
    DOUBLE("大于等于","gte"),
    EMPTY("大于","gt"),
    FLOAT("大于等于","lte"),
    GENERIC("在","in"),
    INTEGER("小于","lt"),
    STRING("在范围内","range");
    private final String note;
    private final String value;

    SeriesEnum(String note,String value) {
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
