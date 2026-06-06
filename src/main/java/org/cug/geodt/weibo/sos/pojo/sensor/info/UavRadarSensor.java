package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.info
 * @Description
 * @date 2023/7/7 10:31
 */
@Component
@TableName("uav_radar_sensor_low_altitude_move")
public class UavRadarSensor extends SensorInfo {
    private Float minBandFrequencyRange;
    private Float maxBandFrequencyRange;
    private Float wireBundlesNumber;
    private Float verticalFov;
    private Float horizontalFov;

    public UavRadarSensor() {
    }

    public UavRadarSensor(OfferingProcedureFeatureSensorInfo offering, Float minBandFrequencyRange, Float maxBandFrequencyRange, Float wireBundlesNumber, Float verticalFov, Float horizontalFov) {
        super(offering);
        this.minBandFrequencyRange = minBandFrequencyRange;
        this.maxBandFrequencyRange = maxBandFrequencyRange;
        this.wireBundlesNumber = wireBundlesNumber;
        this.verticalFov = verticalFov;
        this.horizontalFov = horizontalFov;
    }

    public UavRadarSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, Float minBandFrequencyRange, Float maxBandFrequencyRange, Float wireBundlesNumber, Float verticalFov, Float horizontalFov) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.minBandFrequencyRange = minBandFrequencyRange;
        this.maxBandFrequencyRange = maxBandFrequencyRange;
        this.wireBundlesNumber = wireBundlesNumber;
        this.verticalFov = verticalFov;
        this.horizontalFov = horizontalFov;
    }



    public Float getMinBandFrequencyRange() {
        return minBandFrequencyRange;
    }

    public void setMinBandFrequencyRange(Float minBandFrequencyRange) {
        this.minBandFrequencyRange = minBandFrequencyRange;
    }

    public Float getMaxBandFrequencyRange() {
        return maxBandFrequencyRange;
    }

    public void setMaxBandFrequencyRange(Float maxBandFrequencyRange) {
        this.maxBandFrequencyRange = maxBandFrequencyRange;
    }

    public Float getWireBundlesNumber() {
        return wireBundlesNumber;
    }

    public void setWireBundlesNumber(Float wireBundlesNumber) {
        this.wireBundlesNumber = wireBundlesNumber;
    }

    public Float getVerticalFov() {
        return verticalFov;
    }

    public void setVerticalFov(Float verticalFov) {
        this.verticalFov = verticalFov;
    }

    public Float getHorizontalFov() {
        return horizontalFov;
    }

    public void setHorizontalFov(Float horizontalFov) {
        this.horizontalFov = horizontalFov;
    }
}
