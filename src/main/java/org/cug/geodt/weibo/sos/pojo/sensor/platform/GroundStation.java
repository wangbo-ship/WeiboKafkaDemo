package org.cug.geodt.weibo.sos.pojo.sensor.platform;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo.sensor.platform
 * @Description
 * @date 2023/7/10 15:10
 */
@TableName("ground_station")
public class GroundStation {
    private String stationFullName;
    private String stationAbbreviation;
    @TableId
    private String platformId;
    private String stationType;
    private String intendApplication;
    private Float stationLocationLon;
    private Float stationLocationLat;
    private Float stationLocationAlt;
    private String stationPlatformInfo;
    private Boolean datasourceState;
    private String datasourceName;
    private String datasourceId;
    private String datasourceType;

    public GroundStation() {
    }

    public GroundStation(String stationFullName, String stationAbbreviation, String platformId, String stationType, String intendApplication, Float stationLocationLon, Float stationLocationLat, Float stationLocationAlt, String stationPlatformInfo, Boolean datasourceState, String datasourceName, String datasourceId, String datasourceType) {
        this.stationFullName = stationFullName;
        this.stationAbbreviation = stationAbbreviation;
        this.platformId = platformId;
        this.stationType = stationType;
        this.intendApplication = intendApplication;
        this.stationLocationLon = stationLocationLon;
        this.stationLocationLat = stationLocationLat;
        this.stationLocationAlt = stationLocationAlt;
        this.stationPlatformInfo = stationPlatformInfo;
        this.datasourceState = datasourceState;
        this.datasourceName = datasourceName;
        this.datasourceId = datasourceId;
        this.datasourceType = datasourceType;
    }

    public String getStationFullName() {
        return stationFullName;
    }

    public void setStationFullName(String stationFullName) {
        this.stationFullName = stationFullName;
    }

    public String getStationAbbreviation() {
        return stationAbbreviation;
    }

    public void setStationAbbreviation(String stationAbbreviation) {
        this.stationAbbreviation = stationAbbreviation;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getIntendApplication() {
        return intendApplication;
    }

    public void setIntendApplication(String intendApplication) {
        this.intendApplication = intendApplication;
    }

    public Float getStationLocationLon() {
        return stationLocationLon;
    }

    public void setStationLocationLon(Float stationLocationLon) {
        this.stationLocationLon = stationLocationLon;
    }

    public Float getStationLocationLat() {
        return stationLocationLat;
    }

    public void setStationLocationLat(Float stationLocationLat) {
        this.stationLocationLat = stationLocationLat;
    }

    public Float getStationLocationAlt() {
        return stationLocationAlt;
    }

    public void setStationLocationAlt(Float stationLocationAlt) {
        this.stationLocationAlt = stationLocationAlt;
    }

    public String getStationPlatformInfo() {
        return stationPlatformInfo;
    }

    public void setStationPlatformInfo(String stationPlatformInfo) {
        this.stationPlatformInfo = stationPlatformInfo;
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

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }
}
