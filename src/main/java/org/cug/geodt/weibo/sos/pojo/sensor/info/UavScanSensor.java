package org.cug.geodt.weibo.sos.pojo.sensor.info;


import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.info
 * @Description
 * @date 2023/7/7 10:34
 */

@Component
@TableName("uav_scanning_sensor_low_altitude_move")
public class UavScanSensor extends SensorInfo {
    private Float scanResolution;
    private Float measureSection;
    private Float scanPointNumber;
    private Float verticalFov;
    private Float horizontalFov;

    public UavScanSensor() {
    }

    public UavScanSensor(OfferingProcedureFeatureSensorInfo offering, Float scanResolution, Float measureSection, Float scanPointNumber, Float verticalFov, Float horizontalFov) {
        super(offering);
        this.scanResolution = scanResolution;
        this.measureSection = measureSection;
        this.scanPointNumber = scanPointNumber;
        this.verticalFov = verticalFov;
        this.horizontalFov = horizontalFov;
    }

    public UavScanSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, Float scanResolution, Float measureSection, Float scanPointNumber, Float verticalFov, Float horizontalFov) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.scanResolution = scanResolution;
        this.measureSection = measureSection;
        this.scanPointNumber = scanPointNumber;
        this.verticalFov = verticalFov;
        this.horizontalFov = horizontalFov;
    }

    public Float getScanResolution() {
        return scanResolution;
    }

    public void setScanResolution(Float scanResolution) {
        this.scanResolution = scanResolution;
    }

    public Float getMeasureSection() {
        return measureSection;
    }

    public void setMeasureSection(Float measureSection) {
        this.measureSection = measureSection;
    }

    public Float getScanPointNumber() {
        return scanPointNumber;
    }

    public void setScanPointNumber(Float scanPointNumber) {
        this.scanPointNumber = scanPointNumber;
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
