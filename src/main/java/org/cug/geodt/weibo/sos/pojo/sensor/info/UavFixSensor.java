package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.info
 * @Description
 * @date 2023/7/7 10:28
 */
@Component
@TableName("uav_fix_sensor_low_altitude_move")
public class UavFixSensor extends SensorInfo {
    private Float sampleResponseTime;
    private Float detectionAccuracy;
    private Float sampleCycle;

    public UavFixSensor() {
    }

    public UavFixSensor(OfferingProcedureFeatureSensorInfo offering, Float sampleResponseTime, Float detectionAccuracy, Float sampleCycle) {
        super(offering);
        this.sampleResponseTime = sampleResponseTime;
        this.detectionAccuracy = detectionAccuracy;
        this.sampleCycle = sampleCycle;
    }

    public UavFixSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, Float sampleResponseTime, Float detectionAccuracy, Float sampleCycle) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.sampleResponseTime = sampleResponseTime;
        this.detectionAccuracy = detectionAccuracy;
        this.sampleCycle = sampleCycle;
    }

    public Float getSampleResponseTime() {
        return sampleResponseTime;
    }

    public void setSampleResponseTime(Float sampleResponseTime) {
        this.sampleResponseTime = sampleResponseTime;
    }

    public Float getDetectionAccuracy() {
        return detectionAccuracy;
    }

    public void setDetectionAccuracy(Float detectionAccuracy) {
        this.detectionAccuracy = detectionAccuracy;
    }

    public Float getSampleCycle() {
        return sampleCycle;
    }

    public void setSampleCycle(Float sampleCycle) {
        this.sampleCycle = sampleCycle;
    }
}
