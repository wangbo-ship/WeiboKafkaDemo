package org.cug.geodt.weibo.sos.enums;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.enums
 * @Description
 * @date 2023/1/3 14:36
 */
public enum FilterEnum {
    EQUAL("等于","equal"),
    GTE("大于等于","gte"),
    GT("大于","gt"),
    LTE("大于等于","lte"),
    IN("在","in"),
    LT("小于","lt"),
    RANGE("在范围内","range"),
    REGEXLIKE("正则","regexLike"),
    WITHIN("在之内","within"),
    CONTAINS("包括","contains");
    private final String note;
    private final String value;

    FilterEnum(String note,String value) {
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
