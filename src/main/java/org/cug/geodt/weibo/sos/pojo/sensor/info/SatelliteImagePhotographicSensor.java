package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.pojo.sensor.info
 * @Description
 * @date 2023/7/7 10:55
 */


@Component
@TableName("satellite_image_type_photographic_sensor_medium_altitude_move")
public class SatelliteImagePhotographicSensor extends SensorInfo {
    private String photographyType;
    private Float resolution;
    private Float imageWidth;
    private Float fov;
    private Float focalLength;
    private Float forwardOverlap;

    public SatelliteImagePhotographicSensor() {
    }

    public SatelliteImagePhotographicSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, String photographyType, Float resolution, Float imageWidth, Float fov, Float focalLength, Float forwardOverlap) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.photographyType = photographyType;
        this.resolution = resolution;
        this.imageWidth = imageWidth;
        this.fov = fov;
        this.focalLength = focalLength;
        this.forwardOverlap = forwardOverlap;
    }

    public SatelliteImagePhotographicSensor(OfferingProcedureFeatureSensorInfo offering, String photographyType, Float resolution, Float imageWidth, Float fov, Float focalLength, Float forwardOverlap) {
        super(offering);
        this.photographyType = photographyType;
        this.resolution = resolution;
        this.imageWidth = imageWidth;
        this.fov = fov;
        this.focalLength = focalLength;
        this.forwardOverlap = forwardOverlap;
    }

    public SatelliteImagePhotographicSensor(SensorInfo sensorInfo,SatelliteImagePhotographicSensor satelliteImagePhotographicSensor) {
        super(sensorInfo);
        this.photographyType = satelliteImagePhotographicSensor.getPhotographyType();
        this.resolution = satelliteImagePhotographicSensor.getResolution();
        this.imageWidth = satelliteImagePhotographicSensor.getImageWidth();
        this.fov = satelliteImagePhotographicSensor.getFov();
        this.focalLength = satelliteImagePhotographicSensor.getFocalLength();
        this.forwardOverlap = satelliteImagePhotographicSensor.getForwardOverlap();
    }



    public String getPhotographyType() {
        return photographyType;
    }

    public void setPhotographyType(String photographyType) {
        this.photographyType = photographyType;
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

    public Float getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(Float focalLength) {
        this.focalLength = focalLength;
    }

    public Float getForwardOverlap() {
        return forwardOverlap;
    }

    public void setForwardOverlap(Float forwardOverlap) {
        this.forwardOverlap = forwardOverlap;
    }
}
