package org.cug.geodt.weibo.sos.pojo.sensor.platform;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.platform
 * @Description
 * @date 2023/7/10 15:13
 */
@TableName("satellite")
public class Satellite {
    private String satelliteFullName;
    private String satelliteAcronym;
    @TableId("platform_id")
    private String platformId;
    private String country;
    private String satelliteUser;
    private String intendApplication;
    private String satellitePlatformInfo;
    private Integer noradId;
    private Float revisitCycle;
    private Float maxSwayAngle;
    private Float maxPitchAngle;
    private Boolean datasourceState;
    private String datasourceName;
    private Date launchDate;
    private String datasourceId;
    private String satelliteType;
    private String datasourceType;
    private Boolean agileSatellite;
    private Float sideSwayAngle;
    private String tle;

    public Satellite() {
    }

    public Satellite(String satelliteFullName, String satelliteAcronym, String platformId, String country, String satelliteUser, String intendApplication, String satellitePlatformInfo, Integer noradId, Float revisitCycle, Float maxSwayAngle, Float maxPitchAngle, Boolean datasourceState, String datasourceName, Date launchDate, String datasourceId, String satelliteType, String datasourceType, Boolean agileSatellite, Float sideSwayAngle, String tle) {
        this.satelliteFullName = satelliteFullName;
        this.satelliteAcronym = satelliteAcronym;
        this.platformId = platformId;
        this.country = country;
        this.satelliteUser = satelliteUser;
        this.intendApplication = intendApplication;
        this.satellitePlatformInfo = satellitePlatformInfo;
        this.noradId = noradId;
        this.revisitCycle = revisitCycle;
        this.maxSwayAngle = maxSwayAngle;
        this.maxPitchAngle = maxPitchAngle;
        this.datasourceState = datasourceState;
        this.datasourceName = datasourceName;
        this.launchDate = launchDate;
        this.datasourceId = datasourceId;
        this.satelliteType = satelliteType;
        this.datasourceType = datasourceType;
        this.agileSatellite = agileSatellite;
        this.sideSwayAngle = sideSwayAngle;
        this.tle = tle;
    }

    public String getSatelliteFullName() {
        return satelliteFullName;
    }

    public void setSatelliteFullName(String satelliteFullName) {
        this.satelliteFullName = satelliteFullName;
    }

    public String getSatelliteAcronym() {
        return satelliteAcronym;
    }

    public void setSatelliteAcronym(String satelliteAcronym) {
        this.satelliteAcronym = satelliteAcronym;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSatelliteUser() {
        return satelliteUser;
    }

    public void setSatelliteUser(String satelliteUser) {
        this.satelliteUser = satelliteUser;
    }

    public String getIntendApplication() {
        return intendApplication;
    }

    public void setIntendApplication(String intendApplication) {
        this.intendApplication = intendApplication;
    }

    public String getSatellitePlatformInfo() {
        return satellitePlatformInfo;
    }

    public void setSatellitePlatformInfo(String satellitePlatformInfo) {
        this.satellitePlatformInfo = satellitePlatformInfo;
    }

    public Integer getNoradId() {
        return noradId;
    }

    public void setNoradId(Integer noradId) {
        this.noradId = noradId;
    }

    public Float getRevisitCycle() {
        return revisitCycle;
    }

    public void setRevisitCycle(Float revisitCycle) {
        this.revisitCycle = revisitCycle;
    }

    public Float getMaxSwayAngle() {
        return maxSwayAngle;
    }

    public void setMaxSwayAngle(Float maxSwayAngle) {
        this.maxSwayAngle = maxSwayAngle;
    }

    public Float getMaxPitchAngle() {
        return maxPitchAngle;
    }

    public void setMaxPitchAngle(Float maxPitchAngle) {
        this.maxPitchAngle = maxPitchAngle;
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

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getSatelliteType() {
        return satelliteType;
    }

    public void setSatelliteType(String satelliteType) {
        this.satelliteType = satelliteType;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public Boolean getAgileSatellite() {
        return agileSatellite;
    }

    public void setAgileSatellite(Boolean agileSatellite) {
        this.agileSatellite = agileSatellite;
    }

    public Float getSideSwayAngle() {
        return sideSwayAngle;
    }

    public void setSideSwayAngle(Float sideSwayAngle) {
        this.sideSwayAngle = sideSwayAngle;
    }

    public String getTle() {
        return tle;
    }

    public void setTle(String tle) {
        this.tle = tle;
    }
}
