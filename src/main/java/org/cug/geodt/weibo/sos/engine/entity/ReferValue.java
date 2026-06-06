package org.cug.geodt.weibo.sos.engine.entity;

public class ReferValue {
    private String type;
    private String value;

    public ReferValue(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public ReferValue() {
    }

    @Override
    public String toString() {
        return "referValue{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
