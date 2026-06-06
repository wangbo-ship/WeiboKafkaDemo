package org.cug.geodt.weibo.sos.enums;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.enums
 * @Description
 * @date 2023/6/14 15:52
 */
public enum DescribeSensorGetEnum {
    ALL_SENSOR_INFO("/info/sensors",null,null,"查询全部的传感器信息"),
    SENSOR_INFO_BY_SENSOR_ID("/info/{sensor_id}/bs-info", null,null,"根据传感器ID查询传感器信息"),
    SENSOR_METRIC_NAME_BY_SENSOR_ID("/info/{sensor_id}/metrics",null,null,"根据传感器ID获取测值项信息"),
    SENSOR_LAT_LON_BY_SENSOR_ID("/info/{sensor_id}/latlon",null,null,"获取传感器的经纬度"),
    ALL_SENSOR_GROUP("/info/sensor-group",null,null,"获取全部类型的传感器"),
    SENSOR_METRIC_NAME_BY_SENSOR_GROUP("/info/sensor-group/{group_name}",null,null,"获取传感器分组下的测值项"),
    SENSOR_OLDEST_OBS_TIME_BY_SENSOR_ID("/info/{sensor_id}/obs-time/oldest",null,null,"获取指定多个传感器最早观测时间"),
    SENSOR_LATEST_OBS_TIME_BY_SENSOR_ID("/info/{sensor_id}/obs-time/latest",null,null,"获取指定多个传感器最晚观测时间"),
    SENSOR_LATEST_OBS_TIME_BY_SENSOR_ID_AND_DATE("/info/{sensor_id}/obs-timestamp/{date}/latest",null,null,"获取指定多个传感器指定观测日期最近观测时间戳"),
    ALL_SENSOR_ID("/info/sensor-id",null,null,"取所有传感器的id"),
    SENSOR_NUM("/info/sensor-num",null,null,"传感器数量"),
    METRIC_BY_SENSOR_TYPE("/info/{group_name}/metric-name",null,null,"获取所有分组的传感器的数量");

    private final String href; //操作的链接地址
    private final String constraintName; //操作的约束条件名称
    private final String allowedValue; //操作的允许值
    private final String note; //操作的说明或注释

    DescribeSensorGetEnum(String href,String constraintName,String allowedValue,String note){
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
        return "DescribeSensorGetEnum{" +
                "href='" + href + '\'' +
                ", constraintName='" + constraintName + '\'' +
                ", allowedValue='" + allowedValue + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
