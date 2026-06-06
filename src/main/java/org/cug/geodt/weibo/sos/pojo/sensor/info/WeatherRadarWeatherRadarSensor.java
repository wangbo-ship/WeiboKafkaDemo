package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.pojo.sensor.info
 * @Description
 * @date 2023/7/7 10:37
 */
@Component
@TableName("weather_radar_weather_radar_sensor_ground_stay")
public class WeatherRadarWeatherRadarSensor extends SensorInfo {
    private String polarizationDescription;
    private Float maxSensingDistance;
    private Float timeResolution;

    public WeatherRadarWeatherRadarSensor() {
    }




    public WeatherRadarWeatherRadarSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, String polarizationDescription, Float maxSensingDistance, Float timeResolution) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.polarizationDescription = polarizationDescription;
        this.maxSensingDistance = maxSensingDistance;
        this.timeResolution = timeResolution;
    }

    public WeatherRadarWeatherRadarSensor(String polarizationDescription, Float maxSensingDistance, Float timeResolution) {
        this.polarizationDescription = polarizationDescription;
        this.maxSensingDistance = maxSensingDistance;
        this.timeResolution = timeResolution;
    }

    public WeatherRadarWeatherRadarSensor(OfferingProcedureFeatureSensorInfo offering, String polarizationDescription, Float maxSensingDistance, Float timeResolution) {
        super(offering);
        this.polarizationDescription = polarizationDescription;
        this.maxSensingDistance = maxSensingDistance;
        this.timeResolution = timeResolution;
    }

    public String getPolarizationDescription() {
        return polarizationDescription;
    }

    public void setPolarizationDescription(String polarizationDescription) {
        this.polarizationDescription = polarizationDescription;
    }

    public Float getMaxSensingDistance() {
        return maxSensingDistance;
    }

    public void setMaxSensingDistance(Float maxSensingDistance) {
        this.maxSensingDistance = maxSensingDistance;
    }

    public Float getTimeResolution() {
        return timeResolution;
    }

    public void setTimeResolution(Float timeResolution) {
        this.timeResolution = timeResolution;
    }
}
