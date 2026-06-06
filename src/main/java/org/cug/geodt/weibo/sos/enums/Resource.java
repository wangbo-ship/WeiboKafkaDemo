package org.cug.geodt.weibo.sos.enums;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.enums
 * @Description
 * @date 2023/5/8 20:26
 */
public enum Resource {
    SENSORDATA("传感器数据","sensorData"),
    SENSORINFO("传感器信息","sensorInfo");
    private final String note;
    private final String value;

    Resource(String note,String value) {
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
