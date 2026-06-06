package org.cug.geodt.weibo.sos.vo;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.vo
 * @Description
 * @date 2023/1/14 12:39
 */
public class SensorDataWithInfoVO {

    private String sensorId;
    private String sensorName;
    private String sensorType;
    private String sensorAttribute;
    private String srid;
    private Float sensorLongitude;
    private Float sensorLatitude;
    private List<SensorData> sensorData;

    public SensorDataWithInfoVO(String sensorId, String sensorName, String sensorType, String sensorAttribute, String srid, Float sensorLongitude, Float sensorLatitude, List<SensorData> sensorData) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.sensorAttribute = sensorAttribute;
        this.srid = srid;
        this.sensorLongitude = sensorLongitude;
        this.sensorLatitude = sensorLatitude;
        this.sensorData = sensorData;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorAttribute() {
        return sensorAttribute;
    }

    public void setSensorAttribute(String sensorAttribute) {
        this.sensorAttribute = sensorAttribute;
    }

    public String getSrid() {
        return srid;
    }

    public void setSrid(String srid) {
        this.srid = srid;
    }

    public Float getSensorLongitude() {
        return sensorLongitude;
    }

    public void setSensorLongitude(Float sensorLongitude) {
        this.sensorLongitude = sensorLongitude;
    }

    public Float getSensorLatitude() {
        return sensorLatitude;
    }

    public void setSensorLatitude(Float sensorLatitude) {
        this.sensorLatitude = sensorLatitude;
    }

    public List<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(List<SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    @Override
    public String toString() {
        return "SensorDataWithInfoVO{" +
                "sensorId='" + sensorId + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", sensorType='" + sensorType + '\'' +
                ", sensorAttribute='" + sensorAttribute + '\'' +
                ", srid='" + srid + '\'' +
                ", sensorLongitude=" + sensorLongitude +
                ", sensorLatitude=" + sensorLatitude +
                ", sensorData=" + sensorData +
                '}';
    }
}
