package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.info
 * @Description
 * @date 2023/7/7 10:24
 */

@Component
@TableName("uav_camera_sensor_low_altitude_move")
public class UavCameraSensor extends SensorInfo {
    private String cameraModel;
    private String imageSensorType;
    private Float horizontalFov;
    private Float verticalFov;
    private Float equivalentFocalDistance;
    private Float maxPixel;

    public UavCameraSensor() {
    }

    public UavCameraSensor(OfferingProcedureFeatureSensorInfo offering, String cameraModel, String imageSensorType, Float horizontalFov, Float verticalFov, Float equivalentFocalDistance, Float maxPixel) {
        super(offering);
        this.cameraModel = cameraModel;
        this.imageSensorType = imageSensorType;
        this.horizontalFov = horizontalFov;
        this.verticalFov = verticalFov;
        this.equivalentFocalDistance = equivalentFocalDistance;
        this.maxPixel = maxPixel;
    }

    public UavCameraSensor(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, String cameraModel, String imageSensorType, Float horizontalFov, Float verticalFov, Float equivalentFocalDistance, Float maxPixel) {
        super(sensorId, sensorLongName, sensorType, srid, sensorLongitude, sensorLatitude, sensorInstallTime, createUserId, metrics, sensorLength, sensorWidth, sensorHeight, sensorWeight, organisationName, individualName, telephone, country, city, administrativeArea, address, postalCode, email, datasourceId, datasourceType, obsRadius, dataFormat, arcrole, obsTheme, sampleDuration, sampleInterval, sensorDeployTime, datasourceState, sensorShortName, intendApplication, equipmentManufacturer, description, sensorAltitude, fkPlatform);
        this.cameraModel = cameraModel;
        this.imageSensorType = imageSensorType;
        this.horizontalFov = horizontalFov;
        this.verticalFov = verticalFov;
        this.equivalentFocalDistance = equivalentFocalDistance;
        this.maxPixel = maxPixel;
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    public String getImageSensorType() {
        return imageSensorType;
    }

    public void setImageSensorType(String imageSensorType) {
        this.imageSensorType = imageSensorType;
    }

    public Float getHorizontalFov() {
        return horizontalFov;
    }

    public void setHorizontalFov(Float horizontalFov) {
        this.horizontalFov = horizontalFov;
    }

    public Float getVerticalFov() {
        return verticalFov;
    }

    public void setVerticalFov(Float verticalFov) {
        this.verticalFov = verticalFov;
    }

    public Float getEquivalentFocalDistance() {
        return equivalentFocalDistance;
    }

    public void setEquivalentFocalDistance(Float equivalentFocalDistance) {
        this.equivalentFocalDistance = equivalentFocalDistance;
    }

    public Float getMaxPixel() {
        return maxPixel;
    }

    public void setMaxPixel(Float maxPixel) {
        this.maxPixel = maxPixel;
    }
}
