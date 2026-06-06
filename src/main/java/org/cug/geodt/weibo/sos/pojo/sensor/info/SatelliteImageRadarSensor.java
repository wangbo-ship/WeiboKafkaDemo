package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.info
 * @Description
 * @date 2023/7/7 11:01
 */
@Component
@TableName("satellite_image_type_radar_sensor_medium_altitude_move")
public class SatelliteImageRadarSensor extends SensorInfo {
    private Float resolution;
    private Float imageWidth;
    private Float minBandFrequencyRange;
    private Float maxBandFrequencyRange;
    private String polarizationType;

    public SatelliteImageRadarSensor() {
    }

    public SatelliteImageRadarSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, Float resolution, Float imageWidth, Float minBandFrequencyRange, Float maxBandFrequencyRange, String polarizationType) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.resolution = resolution;
        this.imageWidth = imageWidth;
        this.minBandFrequencyRange = minBandFrequencyRange;
        this.maxBandFrequencyRange = maxBandFrequencyRange;
        this.polarizationType = polarizationType;
    }

    public SatelliteImageRadarSensor(OfferingProcedureFeatureSensorInfo offering, Float resolution, Float imageWidth, Float minBandFrequencyRange, Float maxBandFrequencyRange, String polarizationType) {
        super(offering);
        this.resolution = resolution;
        this.imageWidth = imageWidth;
        this.minBandFrequencyRange = minBandFrequencyRange;
        this.maxBandFrequencyRange = maxBandFrequencyRange;
        this.polarizationType = polarizationType;
    }

    public Float getResolution() {
        return resolution;
    }

    public void setResolution(Float resolution) {
        this.resolution = resolution;
    }

    public Float getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Float imageWidth) {
        this.imageWidth = imageWidth;
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

    public String getPolarizationType() {
        return polarizationType;
    }

    public void setPolarizationType(String polarizationType) {
        this.polarizationType = polarizationType;
    }
}
