package org.cug.geodt.weibo.sos.enums;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.enums
 * @Description
 * @date 2023/6/17 11:04
 */
public enum GetObservationGetEnum {

    GET_LATEST_METRIC_VALUE_BY_SENSOR_ID("/observation/{sensor_id}/latest/{interval_in_min}",null,null,"查询指定传感器最新几分钟内的数据"),
    GET_LATEST_METRIC_VALUE_BY_SENSOR_ID_AND_METRIC_NAME("/observation/{sensor_id}/latest/{interval_in_min}/{metric_name}",null,null,"查询指定传感器和指定测值项最新几分钟内的数据"),
    GET_METRIC_VALUE_BY_SENSOR_ID_AND_TIME_RANGE("/observation/{sensor_id}/time-range","Content-Type","application/x-kvp","查询指定传感器指定时间范围内的数据"),
    GET_METRIC_VALUE_BY_SENSOR_ID_AND_TIME_RANGE_AND_METRIC_NAME("/observation/{sensor_id}/time-range/{metric_name}","Content-Type","application/x-kvp","查询指定传感器指定测值项的时间范围内的数据"),
    GET_METRIC_VALUE_BY_TYPE_AND_METRIC_NAME("/observation/sensor-group/{group_name}/latest/{interval_in_min}/{metric_name}",null,null,"按照传感器组和测值项查询近几分钟的数据"),
    GET_METRIC_VALUE_BY_TYPE_AND_TIME_RANGE("/observation/sensor-group/{group_name}/time-range","Content-Type","application/x-kvp","查询一种传感器类型近几分钟的数据"),
    GET_METRIC_VALUE_BY_TYPE_AND_TIME_RANGE_AND_METRIC_NAME("/observation/sensor-group/{group_name}/time-range/{metric_name}","Content-Type","application/x-kvp","查询一种传感器类型下的测值项时间范围内的数据"),
    GET_METRIC_VALUE_BY_TYPE("/observation/sensor-group/{group_name}/latest/{interval_in_min}",null,null,"查询一种传感器类型近几分钟的数据");
    private final String href;
    private final String constraintName;
    private final String allowedValue;

    private final String note;

    GetObservationGetEnum(String href,String constraintName,String allowedValue,String note){
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

    public String getAllowedValue() {
        return allowedValue;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "GetObservationGetEnum{" +
                "href='" + href + '\'' +
                ", constraintName='" + constraintName + '\'' +
                ", allowedValue='" + allowedValue + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
