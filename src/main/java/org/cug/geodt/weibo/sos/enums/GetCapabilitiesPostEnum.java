package org.cug.geodt.weibo.sos.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.enums
 * @Description
 * @date 2023/6/17 9:36
 */
public enum GetCapabilitiesPostEnum {

    ALL_SENSOR_INFO("/sos/getCapabilities","Content-Type", Collections.unmodifiableList(Arrays.asList("application/xml", "text/xml")),"查询全部的传感器信息");

    private final String href;
    private final String constraintName;
    private final List<String> allowedValue;

    private final String note;

    GetCapabilitiesPostEnum(String href,String constraintName,List<String> allowedValue,String note){
        this.href = href;
        this.constraintName = constraintName;
        this.allowedValue = allowedValue;
        this.note = note;
    }

    public String getHref() {
        return href;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public List<String> getAllowedValue() {
        return allowedValue;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "GetCapabilitiesPostEnum{" +
                "href='" + href + '\'' +
                ", constraintName='" + constraintName + '\'' +
                ", allowedValue='" + allowedValue + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

}
