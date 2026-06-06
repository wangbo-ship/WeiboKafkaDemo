package org.cug.geodt.weibo.sos.pojo.sensor.info;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.cug.geodt.weibo.sos.pojo.sensor.OfferingProcedureFeatureSensorInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/7/7 9:55
 */


@Repository
@Component
@Primary
@TableName("sensor_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorInfo {
    @TableId
    private String sensorId;
    private String sensorLongName;
    private String sensorType;
    private String srid;
    private Float sensorLongitude;
    private Float sensorLatitude;
    private Long sensorInstallTime;
    private String createUserId;
    private String metrics;
    private Float sensorLength;
    private Float sensorWidth;
    private Float sensorHeight;
    private Float sensorWeight;
    private String organisationName;
    private String individualName;
    private String telephone;
    private String country;
    private String city;
    private String administrativeArea;
    private String address;
    private String postalCode;
    private String email;
    private Integer datasourceId;
    private String datasourceType;
    private Float obsRadius;
    private String dataFormat;
    private String arcrole;
    private String obsTheme;
    private Long sampleDuration;
    private Long sampleInterval;
    private Long sensorDeployTime;
    private String datasourceState;
    private String sensorShortName;
    private String intendApplication;
    private String equipmentManufacturer;
    private String description;
    private Float sensorAltitude;
    private String fkPlatform;
    private Integer number;
    private String deliveryPoint;



    public SensorInfo(OfferingProcedureFeatureSensorInfo offeringProcedureFeatureSensorInfo) {
        this.sensorId = offeringProcedureFeatureSensorInfo.getSensorId();
        this.sensorLongName = offeringProcedureFeatureSensorInfo.getSensorLongName();
        this.sensorType = offeringProcedureFeatureSensorInfo.getSensorType();
        this.srid = offeringProcedureFeatureSensorInfo.getSensorSRID();
        this.sensorLongitude = offeringProcedureFeatureSensorInfo.getSensorLongitude();
        this.sensorLatitude = offeringProcedureFeatureSensorInfo.getSensorLatitude();
        this.sensorInstallTime = offeringProcedureFeatureSensorInfo.getSensorInstallTime();
        this.createUserId = offeringProcedureFeatureSensorInfo.getCreateUserId();
        this.metrics = offeringProcedureFeatureSensorInfo.getMetrics();
        this.sensorLength = offeringProcedureFeatureSensorInfo.getSensorLength();
        this.sensorWidth = offeringProcedureFeatureSensorInfo.getSensorWidth();
        this.sensorHeight = offeringProcedureFeatureSensorInfo.getSensorHeight();
        this.sensorWeight = offeringProcedureFeatureSensorInfo.getSensorWeight();
        this.organisationName = offeringProcedureFeatureSensorInfo.getOrganisationName();
        this.individualName = offeringProcedureFeatureSensorInfo.getFeatureName();
        this.telephone = offeringProcedureFeatureSensorInfo.getTelephone();
        this.country = offeringProcedureFeatureSensorInfo.getCountry();
        this.city = offeringProcedureFeatureSensorInfo.getCity();
        this.administrativeArea = offeringProcedureFeatureSensorInfo.getAdministrativeArea();
        this.address = offeringProcedureFeatureSensorInfo.getAddress();
        this.postalCode = offeringProcedureFeatureSensorInfo.getPostalCode();
        this.email = offeringProcedureFeatureSensorInfo.getEmail();
        this.datasourceId = offeringProcedureFeatureSensorInfo.getDatasourceId();
        this.datasourceType = offeringProcedureFeatureSensorInfo.getDatasourceType();
        this.obsRadius = offeringProcedureFeatureSensorInfo.getObsRadius();
        this.dataFormat = offeringProcedureFeatureSensorInfo.getDataFormat();
        this.arcrole = offeringProcedureFeatureSensorInfo.getArcrole();
        this.obsTheme = offeringProcedureFeatureSensorInfo.getObsTheme();
        this.sampleDuration = offeringProcedureFeatureSensorInfo.getSampleDuration();
        this.sampleInterval = offeringProcedureFeatureSensorInfo.getSampleInterval();
        this.sensorDeployTime = offeringProcedureFeatureSensorInfo.getSensorDeployTime();
        this.datasourceState = offeringProcedureFeatureSensorInfo.getDatasourceState();
        this.sensorShortName = offeringProcedureFeatureSensorInfo.getSensorShortName();
        this.intendApplication = offeringProcedureFeatureSensorInfo.getIntendApplication();
        this.equipmentManufacturer = offeringProcedureFeatureSensorInfo.getEquipmentManufacturer();
        this.description = offeringProcedureFeatureSensorInfo.getDescription();
        this.sensorAltitude = offeringProcedureFeatureSensorInfo.getSensorHeight();
        this.fkPlatform = offeringProcedureFeatureSensorInfo.getPlatformId();
    }

    public SensorInfo(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, String deliveryPoint) {
        this.sensorId = sensorId;
        this.sensorLongName = sensorLongName;
        this.sensorType = sensorType;
        this.srid = srid;
        this.sensorLongitude = sensorLongitude;
        this.sensorLatitude = sensorLatitude;
        this.sensorInstallTime = sensorInstallTime;
        this.createUserId = createUserId;
        this.metrics = metrics;
        this.sensorLength = sensorLength;
        this.sensorWidth = sensorWidth;
        this.sensorHeight = sensorHeight;
        this.sensorWeight = sensorWeight;
        this.organisationName = organisationName;
        this.individualName = individualName;
        this.telephone = telephone;
        this.country = country;
        this.city = city;
        this.administrativeArea = administrativeArea;
        this.address = address;
        this.postalCode = postalCode;
        this.email = email;
        this.datasourceId = datasourceId;
        this.datasourceType = datasourceType;
        this.obsRadius = obsRadius;
        this.dataFormat = dataFormat;
        this.arcrole = arcrole;
        this.obsTheme = obsTheme;
        this.sampleDuration = sampleDuration;
        this.sampleInterval = sampleInterval;
        this.sensorDeployTime = sensorDeployTime;
        this.datasourceState = datasourceState;
        this.sensorShortName = sensorShortName;
        this.intendApplication = intendApplication;
        this.equipmentManufacturer = equipmentManufacturer;
        this.description = description;
        this.sensorAltitude = sensorAltitude;
        this.fkPlatform = fkPlatform;
        this.deliveryPoint = deliveryPoint;
    }

    public SensorInfo(SensorInfo sensorInfo){
        this.sensorId = sensorInfo.getSensorId();
        this.sensorLongName = sensorInfo.getSensorLongName();
        this.sensorType = sensorInfo.getSensorType();
        this.srid = sensorInfo.getSrid();
        this.sensorLongitude = sensorInfo.getSensorLongitude();
        this.sensorLatitude = sensorInfo.getSensorLatitude();
        this.sensorInstallTime = sensorInfo.getSensorInstallTime();
        this.createUserId = sensorInfo.getCreateUserId();
        this.metrics = sensorInfo.getMetrics();
        this.sensorLength = sensorInfo.getSensorLength();
        this.sensorWidth = sensorInfo.getSensorWidth();
        this.sensorHeight = sensorInfo.getSensorHeight();
        this.sensorWeight = sensorInfo.getSensorWeight();
        this.organisationName = sensorInfo.getOrganisationName();
        this.individualName = sensorInfo.getIndividualName();
        this.telephone =sensorInfo.getTelephone();
        this.country= sensorInfo.getCountry();
        this.city =sensorInfo.getCity() ;
        this.administrativeArea = sensorInfo.getAdministrativeArea();
        this.address = sensorInfo.getAddress();
        this.postalCode = sensorInfo.getPostalCode();
        this.email = sensorInfo.getEmail();
        this.datasourceId = sensorInfo.getDatasourceId();
        this.datasourceType = sensorInfo.getDatasourceType();
        this.obsRadius = sensorInfo.getObsRadius();
        this.dataFormat = sensorInfo.getDataFormat();
        this.arcrole = sensorInfo.getArcrole();
        this.obsTheme = sensorInfo.getObsTheme();
        this.sampleDuration = sensorInfo.getSampleDuration();
        this.sampleInterval = sensorInfo.getSampleInterval();
        this.sensorDeployTime = sensorInfo.getSensorDeployTime();
        this.datasourceState = sensorInfo.getDatasourceState();
        this.sensorShortName = sensorInfo.getSensorShortName();
        this.intendApplication = sensorInfo.getIntendApplication();
        this.equipmentManufacturer = sensorInfo.getEquipmentManufacturer();
        this.description = sensorInfo.getDescription();
        this.sensorAltitude = sensorInfo.getSensorAltitude();
        this.fkPlatform = sensorInfo.getFkPlatform();
        this.deliveryPoint = sensorInfo.getDeliveryPoint();
    }

    public SensorInfo(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform, Integer number, String deliveryPoint) {
        this.sensorId = sensorId;
        this.sensorLongName = sensorLongName;
        this.sensorType = sensorType;
        this.srid = srid;
        this.sensorLongitude = sensorLongitude;
        this.sensorLatitude = sensorLatitude;
        this.sensorInstallTime = sensorInstallTime;
        this.createUserId = createUserId;
        this.metrics = metrics;
        this.sensorLength = sensorLength;
        this.sensorWidth = sensorWidth;
        this.sensorHeight = sensorHeight;
        this.sensorWeight = sensorWeight;
        this.organisationName = organisationName;
        this.individualName = individualName;
        this.telephone = telephone;
        this.country = country;
        this.city = city;
        this.administrativeArea = administrativeArea;
        this.address = address;
        this.postalCode = postalCode;
        this.email = email;
        this.datasourceId = datasourceId;
        this.datasourceType = datasourceType;
        this.obsRadius = obsRadius;
        this.dataFormat = dataFormat;
        this.arcrole = arcrole;
        this.obsTheme = obsTheme;
        this.sampleDuration = sampleDuration;
        this.sampleInterval = sampleInterval;
        this.sensorDeployTime = sensorDeployTime;
        this.datasourceState = datasourceState;
        this.sensorShortName = sensorShortName;
        this.intendApplication = intendApplication;
        this.equipmentManufacturer = equipmentManufacturer;
        this.description = description;
        this.sensorAltitude = sensorAltitude;
        this.fkPlatform = fkPlatform;
        this.number = number;
        this.deliveryPoint = deliveryPoint;
    }

    public SensorInfo() {
    }

    public SensorInfo(String sensorId, String sensorLongName, String sensorType, String srid, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String individualName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, Float sensorAltitude, String fkPlatform) {
        this.sensorId = sensorId;
        this.sensorLongName = sensorLongName;
        this.sensorType = sensorType;
        this.srid = srid;
        this.sensorLongitude = sensorLongitude;
        this.sensorLatitude = sensorLatitude;
        this.sensorInstallTime = sensorInstallTime;
        this.createUserId = createUserId;
        this.metrics = metrics;
        this.sensorLength = sensorLength;
        this.sensorWidth = sensorWidth;
        this.sensorHeight = sensorHeight;
        this.sensorWeight = sensorWeight;
        this.organisationName = organisationName;
        this.individualName = individualName;
        this.telephone = telephone;
        this.country = country;
        this.city = city;
        this.administrativeArea = administrativeArea;
        this.address = address;
        this.postalCode = postalCode;
        this.email = email;
        this.datasourceId = datasourceId;
        this.datasourceType = datasourceType;
        this.obsRadius = obsRadius;
        this.dataFormat = dataFormat;
        this.arcrole = arcrole;
        this.obsTheme = obsTheme;
        this.sampleDuration = sampleDuration;
        this.sampleInterval = sampleInterval;
        this.sensorDeployTime = sensorDeployTime;
        this.datasourceState = datasourceState;
        this.sensorShortName = sensorShortName;
        this.intendApplication = intendApplication;
        this.equipmentManufacturer = equipmentManufacturer;
        this.description = description;
        this.sensorAltitude = sensorAltitude;
        this.fkPlatform = fkPlatform;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorLongName() {
        return sensorLongName;
    }

    public void setSensorLongName(String sensorLongName) {
        this.sensorLongName = sensorLongName;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSrid() {
        return srid;
    }

    public void setSrid(String srid) {
        this.srid = srid;
    }

    public Float getSensorLongitude() {
        return sensorLongitude;
    }

    public void setSensorLongitude(Float sensorLongitude) {
        this.sensorLongitude = sensorLongitude;
    }

    public Float getSensorLatitude() {
        return sensorLatitude;
    }

    public void setSensorLatitude(Float sensorLatitude) {
        this.sensorLatitude = sensorLatitude;
    }

    public Long getSensorInstallTime() {
        return sensorInstallTime;
    }

    public void setSensorInstallTime(Long sensorInstallTime) {
        this.sensorInstallTime = sensorInstallTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public Float getSensorLength() {
        return sensorLength;
    }

    public void setSensorLength(Float sensorLength) {
        this.sensorLength = sensorLength;
    }

    public Float getSensorWidth() {
        return sensorWidth;
    }

    public void setSensorWidth(Float sensorWidth) {
        this.sensorWidth = sensorWidth;
    }

    public Float getSensorHeight() {
        return sensorHeight;
    }

    public void setSensorHeight(Float sensorHeight) {
        this.sensorHeight = sensorHeight;
    }

    public Float getSensorWeight() {
        return sensorWeight;
    }

    public void setSensorWeight(Float sensorWeight) {
        this.sensorWeight = sensorWeight;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public Float getObsRadius() {
        return obsRadius;
    }

    public void setObsRadius(Float obsRadius) {
        this.obsRadius = obsRadius;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getArcrole() {
        return arcrole;
    }

    public void setArcrole(String arcrole) {
        this.arcrole = arcrole;
    }

    public String getObsTheme() {
        return obsTheme;
    }

    public void setObsTheme(String obsTheme) {
        this.obsTheme = obsTheme;
    }

    public Long getSampleDuration() {
        return sampleDuration;
    }

    public void setSampleDuration(Long sampleDuration) {
        this.sampleDuration = sampleDuration;
    }

    public Long getSampleInterval() {
        return sampleInterval;
    }

    public void setSampleInterval(Long sampleInterval) {
        this.sampleInterval = sampleInterval;
    }

    public Long getSensorDeployTime() {
        return sensorDeployTime;
    }

    public void setSensorDeployTime(Long sensorDeployTime) {
        this.sensorDeployTime = sensorDeployTime;
    }

    public String getDatasourceState() {
        return datasourceState;
    }

    public void setDatasourceState(String datasourceState) {
        this.datasourceState = datasourceState;
    }

    public String getSensorShortName() {
        return sensorShortName;
    }

    public void setSensorShortName(String sensorShortName) {
        this.sensorShortName = sensorShortName;
    }

    public String getIntendApplication() {
        return intendApplication;
    }

    public void setIntendApplication(String intendApplication) {
        this.intendApplication = intendApplication;
    }

    public String getEquipmentManufacturer() {
        return equipmentManufacturer;
    }

    public void setEquipmentManufacturer(String equipmentManufacturer) {
        this.equipmentManufacturer = equipmentManufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getSensorAltitude() {
        return sensorAltitude;
    }

    public void setSensorAltitude(Float sensorAltitude) {
        this.sensorAltitude = sensorAltitude;
    }

    public String getFkPlatform() {
        return fkPlatform;
    }

    public void setFkPlatform(String fkPlatform) {
        this.fkPlatform = fkPlatform;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(String deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }
}
