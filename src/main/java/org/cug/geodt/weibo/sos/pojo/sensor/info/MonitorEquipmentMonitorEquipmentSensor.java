package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.info
 * @Description
 * @date 2023/7/7 10:57
 */
@Component
@TableName("monitor_equipment_monitor_equipment_sensor_ground_stay")
public class MonitorEquipmentMonitorEquipmentSensor extends SensorInfo {
    @Getter
    private String monitorEquipmentFullName;
    @Getter
    private String monitorCameraType;
    private String obsTheme;
    private String intendApplication;
    private String equipmentManufacturer;
    @Getter
    private String imageSensorType;
    @Getter
    private String resolution;
    @Getter
    private Integer effectivePixel;
    @Getter
    private Float clarity;
    @Getter
    private String signalToNoiseRatio;
    @Getter
    private Float horizontalFov;
    @Getter
    private Float verticalFov;
    @Getter
    private Float videoFrameRate;

    @Getter
    private String[] obsThemeList;

    public MonitorEquipmentMonitorEquipmentSensor(MonitorEquipmentMonitorEquipmentSensor m, SensorInfo newSensorInfo) {
        super(newSensorInfo);
        this.monitorEquipmentFullName = m.monitorEquipmentFullName;
        this.monitorCameraType = m.monitorCameraType;
        this.obsTheme = m.obsTheme;
        this.intendApplication = m.intendApplication;
        this.equipmentManufacturer = m.equipmentManufacturer;
        this.imageSensorType = m.imageSensorType;
        this.resolution = m.resolution;
        this.effectivePixel = m.effectivePixel;
        this.clarity = m.clarity;
        this.signalToNoiseRatio = m.signalToNoiseRatio;
        this.horizontalFov = m.horizontalFov;
        this.verticalFov = m.verticalFov;
        this.videoFrameRate = m.videoFrameRate;
        this.obsThemeList = m.obsThemeList;
    }

    public void setMonitorEquipmentFullName(String monitorEquipmentFullName) {
        this.monitorEquipmentFullName = monitorEquipmentFullName;
    }

    public void setMonitorCameraType(String monitorCameraType) {
        this.monitorCameraType = monitorCameraType;
    }

    @Override
    public String getObsTheme() {
        return obsTheme;
    }

    @Override
    public void setObsTheme(String obsTheme) {
        this.obsTheme = obsTheme;
    }

    @Override
    public String getIntendApplication() {
        return intendApplication;
    }

    @Override
    public void setIntendApplication(String intendApplication) {
        this.intendApplication = intendApplication;
    }

    @Override
    public String getEquipmentManufacturer() {
        return equipmentManufacturer;
    }

    @Override
    public void setEquipmentManufacturer(String equipmentManufacturer) {
        this.equipmentManufacturer = equipmentManufacturer;
    }

    public void setImageSensorType(String imageSensorType) {
        this.imageSensorType = imageSensorType;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setEffectivePixel(Integer effectivePixel) {
        this.effectivePixel = effectivePixel;
    }

    public void setClarity(Float clarity) {
        this.clarity = clarity;
    }

    public void setSignalToNoiseRatio(String signalToNoiseRatio) {
        this.signalToNoiseRatio = signalToNoiseRatio;
    }

    public void setHorizontalFov(Float horizontalFov) {
        this.horizontalFov = horizontalFov;
    }

    public void setVerticalFov(Float verticalFov) {
        this.verticalFov = verticalFov;
    }

    public void setVideoFrameRate(Float videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public void setObsThemeList(String[] obsThemeList) {
        this.obsThemeList = obsThemeList;
    }

    public MonitorEquipmentMonitorEquipmentSensor() {
    }
}

