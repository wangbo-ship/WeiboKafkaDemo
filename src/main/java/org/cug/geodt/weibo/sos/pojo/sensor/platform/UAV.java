package org.cug.geodt.weibo.sos.pojo.sensor.platform;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.platform
 * @Description
 * @date 2023/7/10 15:20
 */
@TableName("uav")
public class UAV {
    private String uavName;
    private String uavModel;
    @TableId
    private String platformId;
    private String uavType;
    private String equipmentManufacturer;
    private String uavStoragePosition;
    private Float uavStorageLocationLon;
    private Float uavStorageLocationLat;
    private Float uavStorageLocationAlt;
    private Float maxFlightAltitude;
    private Float maxAscentAndDescentSpeed;
    private Float maxFlightTime;
    private Float longestRange;
    private Float minCloudPlatformPitchAngle;
    private Float maxCloudPlatformPitchAngle;
    private Boolean datasourceState;
    private String datasourceName;
    private String datasourceId;
    private String intendApplication;
    private String datasourceType;

    public UAV() {
    }

    public UAV(String uavName, String uavModel, String platformId, String uavType, String equipmentManufacturer, String uavStoragePosition, Float uavStorageLocationLon, Float uavStorageLocationLat, Float uavStorageLocationAlt, Float maxFlightAltitude, Float maxAscentAndDescentSpeed, Float maxFlightTime, Float longestRange, Float minCloudPlatformPitchAngle, Float maxCloudPlatformPitchAngle, Boolean datasourceState, String datasourceName, String datasourceId, String intendApplication, String datasourceType) {
        this.uavName = uavName;
        this.uavModel = uavModel;
        this.platformId = platformId;
        this.uavType = uavType;
        this.equipmentManufacturer = equipmentManufacturer;
        this.uavStoragePosition = uavStoragePosition;
        this.uavStorageLocationLon = uavStorageLocationLon;
        this.uavStorageLocationLat = uavStorageLocationLat;
        this.uavStorageLocationAlt = uavStorageLocationAlt;
        this.maxFlightAltitude = maxFlightAltitude;
        this.maxAscentAndDescentSpeed = maxAscentAndDescentSpeed;
        this.maxFlightTime = maxFlightTime;
        this.longestRange = longestRange;
        this.minCloudPlatformPitchAngle = minCloudPlatformPitchAngle;
        this.maxCloudPlatformPitchAngle = maxCloudPlatformPitchAngle;
        this.datasourceState = datasourceState;
        this.datasourceName = datasourceName;
        this.datasourceId = datasourceId;
        this.intendApplication = intendApplication;
        this.datasourceType = datasourceType;
    }

    public String getUavName() {
        return uavName;
    }

    public void setUavName(String uavName) {
        this.uavName = uavName;
    }

    public String getUavModel() {
        return uavModel;
    }

    public void setUavModel(String uavModel) {
        this.uavModel = uavModel;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getUavType() {
        return uavType;
    }

    public void setUavType(String uavType) {
        this.uavType = uavType;
    }

    public String getEquipmentManufacturer() {
        return equipmentManufacturer;
    }

    public void setEquipmentManufacturer(String equipmentManufacturer) {
        this.equipmentManufacturer = equipmentManufacturer;
    }

    public String getUavStoragePosition() {
        return uavStoragePosition;
    }

    public void setUavStoragePosition(String uavStoragePosition) {
        this.uavStoragePosition = uavStoragePosition;
    }

    public Float getUavStorageLocationLon() {
        return uavStorageLocationLon;
    }

    public void setUavStorageLocationLon(Float uavStorageLocationLon) {
        this.uavStorageLocationLon = uavStorageLocationLon;
    }

    public Float getUavStorageLocationLat() {
        return uavStorageLocationLat;
    }

    public void setUavStorageLocationLat(Float uavStorageLocationLat) {
        this.uavStorageLocationLat = uavStorageLocationLat;
    }

    public Float getUavStorageLocationAlt() {
        return uavStorageLocationAlt;
    }

    public void setUavStorageLocationAlt(Float uavStorageLocationAlt) {
        this.uavStorageLocationAlt = uavStorageLocationAlt;
    }

    public Float getMaxFlightAltitude() {
        return maxFlightAltitude;
    }

    public void setMaxFlightAltitude(Float maxFlightAltitude) {
        this.maxFlightAltitude = maxFlightAltitude;
    }

    public Float getMaxAscentAndDescentSpeed() {
        return maxAscentAndDescentSpeed;
    }

    public void setMaxAscentAndDescentSpeed(Float maxAscentAndDescentSpeed) {
        this.maxAscentAndDescentSpeed = maxAscentAndDescentSpeed;
    }

    public Float getMaxFlightTime() {
        return maxFlightTime;
    }

    public void setMaxFlightTime(Float maxFlightTime) {
        this.maxFlightTime = maxFlightTime;
    }

    public Float getLongestRange() {
        return longestRange;
    }

    public void setLongestRange(Float longestRange) {
        this.longestRange = longestRange;
    }

    public Float getMinCloudPlatformPitchAngle() {
        return minCloudPlatformPitchAngle;
    }

    public void setMinCloudPlatformPitchAngle(Float minCloudPlatformPitchAngle) {
        this.minCloudPlatformPitchAngle = minCloudPlatformPitchAngle;
    }

    public Float getMaxCloudPlatformPitchAngle() {
        return maxCloudPlatformPitchAngle;
    }

    public void setMaxCloudPlatformPitchAngle(Float maxCloudPlatformPitchAngle) {
        this.maxCloudPlatformPitchAngle = maxCloudPlatformPitchAngle;
    }

    public Boolean getDatasourceState() {
        return datasourceState;
    }

    public void setDatasourceState(Boolean datasourceState) {
        this.datasourceState = datasourceState;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getIntendApplication() {
        return intendApplication;
    }

    public void setIntendApplication(String intendApplication) {
        this.intendApplication = intendApplication;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }
}