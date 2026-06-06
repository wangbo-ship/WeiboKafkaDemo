package org.cug.geodt.weibo.sos.pojo;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/19 10:01
 */
public class OfferingProduceSensorInfo {
    private String offeringId;
    private String offeringName;
    private String offeringDescription;
    private String procedureId;
    private String procedureName;
    private String procedureDescription;
    private String procedureDescriptionFormat;
    private String sensorId;
    private String sensorName;
    private String sensorType;
    private String sensorAttribute;
    private String srid;
    private Float sensorLongitude;
    private Float sensorLatitude;
    private Integer sensorCreateTime;
    private String createUserId;
    private String metrics;
    private Float velocity;

    public OfferingProduceSensorInfo(String offeringId, String offeringName, String offeringDescription, String procedureId, String procedureName, String procedureDescription, String procedureDescriptionFormat, String sensorId, String sensorName, String sensorType, String sensorAttribute, String srid, Float sensorLongitude, Float sensorLatitude, Integer sensorCreateTime, String createUserId, String metrics, Float velocity) {
        this.offeringId = offeringId;
        this.offeringName = offeringName;
        this.offeringDescription = offeringDescription;
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.procedureDescription = procedureDescription;
        this.procedureDescriptionFormat = procedureDescriptionFormat;
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.sensorAttribute = sensorAttribute;
        this.srid = srid;
        this.sensorLongitude = sensorLongitude;
        this.sensorLatitude = sensorLatitude;
        this.sensorCreateTime = sensorCreateTime;
        this.createUserId = createUserId;
        this.metrics = metrics;
        this.velocity = velocity;
    }

    public OfferingProduceSensorInfo() {
    }

    public String getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
    }

    public String getOfferingName() {
        return offeringName;
    }

    public void setOfferingName(String offeringName) {
        this.offeringName = offeringName;
    }

    public String getOfferingDescription() {
        return offeringDescription;
    }

    public void setOfferingDescription(String offeringDescription) {
        this.offeringDescription = offeringDescription;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureDescription() {
        return procedureDescription;
    }

    public void setProcedureDescription(String procedureDescription) {
        this.procedureDescription = procedureDescription;
    }

    public String getProcedureDescriptionFormat() {
        return procedureDescriptionFormat;
    }

    public void setProcedureDescriptionFormat(String procedureDescriptionFormat) {
        this.procedureDescriptionFormat = procedureDescriptionFormat;
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
}
