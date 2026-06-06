package org.cug.geodt.weibo.sos.pojo;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/19 17:23
 */

public class SensorInfoFeature {
    private String sensorId;
    private String sensorName;
    private String sensorType;
    private String sensorAttribute;
    private String sensorInfoSrid;
    private Float sensorLongitude;
    private Float sensorLatitude;
    private Integer sensorCreateTime;
    private String createUserId;
    private String metrics;
    private Float velocity;
    private String featureId;
    private String featureName;
    private String featureSrid;
    private String featureArea;

    public SensorInfoFeature(String sensorId, String sensorName, String sensorType, String sensorAttribute, String sensorInfoSrid, Float sensorLongitude, Float sensorLatitude, Integer sensorCreateTime, String createUserId, String metrics, Float velocity, String featureId, String featureName, String featureSrid, String featureArea) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.sensorAttribute = sensorAttribute;
        this.sensorInfoSrid = sensorInfoSrid;
        this.sensorLongitude = sensorLongitude;
        this.sensorLatitude = sensorLatitude;
        this.sensorCreateTime = sensorCreateTime;
        this.createUserId = createUserId;
        this.metrics = metrics;
        this.velocity = velocity;
        this.featureId = featureId;
        this.featureName = featureName;
        this.featureSrid = featureSrid;
        this.featureArea = featureArea;
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

    public String getSensorInfoSrid() {
        return sensorInfoSrid;
    }

    public void setSensorInfoSrid(String sensorInfoSrid) {
        this.sensorInfoSrid = sensorInfoSrid;
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

    public Integer getSensorCreateTime() {
        return sensorCreateTime;
    }

    public void setSensorCreateTime(Integer sensorCreateTime) {
        this.sensorCreateTime = sensorCreateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public Float getVelocity() {
        return velocity;
    }

    public void setVelocity(Float velocity) {
        this.velocity = velocity;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureSrid() {
        return featureSrid;
    }

    public void setFeatureSrid(String featureSrid) {
        this.featureSrid = featureSrid;
    }

    public String getFeatureArea() {
        return featureArea;
    }

    public void setFeatureArea(String featureArea) {
        this.featureArea = featureArea;
    }

    public SensorInfoFeature() {
    }
}
