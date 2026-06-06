package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.info
 * @Description
 * @date 2023/7/7 11:04
 */
@Component
@TableName("satellite_image_type_scanning_sensor_medium_altitude_move")
public class SatelliteImageScanningSensor extends SensorInfo {
    private String scanningType;
    private Float resolution;
    private Float imageWidth;
    private Float fov;
    private Float sideAngle;
    private Float nadirResolution;

    public SatelliteImageScanningSensor() {
    }

    public SatelliteImageScanningSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, String scanningType, Float resolution, Float imageWidth, Float fov, Float sideAngle, Float nadirResolution) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.scanningType = scanningType;
        this.resolution = resolution;
        this.imageWidth = imageWidth;
        this.fov = fov;
        this.sideAngle = sideAngle;
        this.nadirResolution = nadirResolution;
    }

    public SatelliteImageScanningSensor(OfferingProcedureFeatureSensorInfo offering, String scanningType, Float resolution, Float imageWidth, Float fov, Float sideAngle, Float nadirResolution) {
        super(offering);
        this.scanningType = scanningType;
        this.resolution = resolution;
        this.imageWidth = imageWidth;
        this.fov = fov;
        this.sideAngle = sideAngle;
        this.nadirResolution = nadirResolution;
    }

    public String getScanningType() {
        return scanningType;
    }

    public void setScanningType(String scanningType) {
        this.scanningType = scanningType;
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

    public Float getFov() {
        return fov;
    }

    public void setFov(Float fov) {
        this.fov = fov;
    }

    public Float getSideAngle() {
        return sideAngle;
    }

    public void setSideAngle(Float sideAngle) {
        this.sideAngle = sideAngle;
    }

    public Float getNadirResolution() {
        return nadirResolution;
    }

    public void setNadirResolution(Float nadirResolution) {
        this.nadirResolution = nadirResolution;
    }
}
