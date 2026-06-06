package org.cug.geodt.weibo.sos.pojo.sensor;

import org.cug.geodt.weibo.sos.utils.DateUtils;

import java.math.BigInteger;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/27 14:58
 */

public class OfferingProcedureFeatureSensorInfo {
    private BigInteger datasetId;
    private String datasetType;
    private String observationType;
    private String platformId;

    private BigInteger offeringId;
    private String offeringIdentifier;
    private String offeringName;
    private String offeringDescription;
    private String offeringIsReference;
    private Long offeringSamplingTimeStart;
    private Long offeringSamplingTimeEnd;
    private Long offeringResultTimeStart;
    private Long offeringResultTimeEnd;

    private BigInteger procedureId;
    private String procedureIdentifier;
    private String procedureName;
    private String procedureDescription;
    private String procedureDescriptionFormat;
    private String procedureArea;

    private BigInteger featureId;
    private String featureIdentifier;
    private String featureName;
    private String featureDescription;
    private String featureArea;
    private int featureSRID;

    private String sensorId;
    private String sensorLongName;
    private String sensorType;
    private String sensorSRID;
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
    //    private String individualName;
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
    private String platformType;


    public String getOfferingSamplingTimeStartStr() {
        return DateUtils.convertToISO8601(offeringSamplingTimeStart * 1000L);
    }

    public String getSensorDeployTimeStr() {
        return DateUtils.convertToISO8601(sensorDeployTime * 1000L);
    }

    public String getSensorInstallTimeStr() {
        return DateUtils.convertToISO8601(sensorInstallTime * 1000L);
    }

    public String getOfferingSamplingTimeEndStr() {
        return DateUtils.convertToISO8601(offeringSamplingTimeEnd * 1000L);
    }

    public String getOfferingResultTimeStartStr() {
        return DateUtils.convertToISO8601(offeringResultTimeStart * 1000L);
    }

    public String getOfferingResultTimeEndStr() {
        return DateUtils.convertToISO8601(offeringResultTimeEnd * 1000L);
    }

    public OfferingProcedureFeatureSensorInfo() {
    }

    public OfferingProcedureFeatureSensorInfo(BigInteger datasetId, String datasetType, String observationType, String platformId, BigInteger offeringId, String offeringIdentifier, String offeringName, String offeringDescription, String offeringIsReference, Long offeringSamplingTimeStart, Long offeringSamplingTimeEnd, Long offeringResultTimeStart, Long offeringResultTimeEnd, BigInteger procedureId, String procedureIdentifier, String procedureName, String procedureDescription, String procedureDescriptionFormat, String procedureArea, BigInteger featureId, String featureIdentifier, String featureName, String featureDescription, String featureArea, int featureSRID, String sensorId, String sensorLongName, String sensorType, String sensorSRID, Float sensorLongitude, Float sensorLatitude, Long sensorInstallTime, String createUserId, String metrics, Float sensorLength, Float sensorWidth, Float sensorHeight, Float sensorWeight, String organisationName, String telephone, String country, String city, String administrativeArea, String address, String postalCode, String email, Integer datasourceId, String datasourceType, Float obsRadius, String dataFormat, String arcrole, String obsTheme, Long sampleDuration, Long sampleInterval, Long sensorDeployTime, String datasourceState, String sensorShortName, String intendApplication, String equipmentManufacturer, String description, String platformType) {
        this.datasetId = datasetId;
        this.datasetType = datasetType;
        this.observationType = observationType;
        this.platformId = platformId;
        this.offeringId = offeringId;
        this.offeringIdentifier = offeringIdentifier;
        this.offeringName = offeringName;
        this.offeringDescription = offeringDescription;
        this.offeringIsReference = offeringIsReference;
        this.offeringSamplingTimeStart = offeringSamplingTimeStart;
        this.offeringSamplingTimeEnd = offeringSamplingTimeEnd;
        this.offeringResultTimeStart = offeringResultTimeStart;
        this.offeringResultTimeEnd = offeringResultTimeEnd;
        this.procedureId = procedureId;
        this.procedureIdentifier = procedureIdentifier;
        this.procedureName = procedureName;
        this.procedureDescription = procedureDescription;
        this.procedureDescriptionFormat = procedureDescriptionFormat;
        this.procedureArea = procedureArea;
        this.featureId = featureId;
        this.featureIdentifier = featureIdentifier;
        this.featureName = featureName;
        this.featureDescription = featureDescription;
        this.featureArea = featureArea;
        this.featureSRID = featureSRID;
        this.sensorId = sensorId;
        this.sensorLongName = sensorLongName;
        this.sensorType = sensorType;
        this.sensorSRID = sensorSRID;
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
        this.platformType = platformType;
    }

    public BigInteger getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(BigInteger datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    public String getObservationType() {
        return observationType;
    }

    public void setObservationType(String observationType) {
        this.observationType = observationType;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public BigInteger getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(BigInteger offeringId) {
        this.offeringId = offeringId;
    }

    public String getOfferingIdentifier() {
        return offeringIdentifier;
    }

    public void setOfferingIdentifier(String offeringIdentifier) {
        this.offeringIdentifier = offeringIdentifier;
    }

    public String getOfferingName() {
        return offeringName;
    }

    public void setOfferingName(String offeringName) {
        this.offeringName = offeringName;
    }

    public String getOfferingDescription() {
        return offeringDescription;
    }

    public void setOfferingDescription(String offeringDescription) {
        this.offeringDescription = offeringDescription;
    }

    public String getOfferingIsReference() {
        return offeringIsReference;
    }

    public void setOfferingIsReference(String offeringIsReference) {
        this.offeringIsReference = offeringIsReference;
    }

    public Long getOfferingSamplingTimeStart() {
        return offeringSamplingTimeStart;
    }

    public void setOfferingSamplingTimeStart(Long offeringSamplingTimeStart) {
        this.offeringSamplingTimeStart = offeringSamplingTimeStart;
    }

    public Long getOfferingSamplingTimeEnd() {
        return offeringSamplingTimeEnd;
    }

    public void setOfferingSamplingTimeEnd(Long offeringSamplingTimeEnd) {
        this.offeringSamplingTimeEnd = offeringSamplingTimeEnd;
    }

    public Long getOfferingResultTimeStart() {
        return offeringResultTimeStart;
    }

    public void setOfferingResultTimeStart(Long offeringResultTimeStart) {
        this.offeringResultTimeStart = offeringResultTimeStart;
    }

    public Long getOfferingResultTimeEnd() {
        return offeringResultTimeEnd;
    }

    public void setOfferingResultTimeEnd(Long offeringResultTimeEnd) {
        this.offeringResultTimeEnd = offeringResultTimeEnd;
    }

    public BigInteger getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(BigInteger procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureIdentifier() {
        return procedureIdentifier;
    }

    public void setProcedureIdentifier(String procedureIdentifier) {
        this.procedureIdentifier = procedureIdentifier;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureDescription() {
        return procedureDescription;
    }

    public void setProcedureDescription(String procedureDescription) {
        this.procedureDescription = procedureDescription;
    }

    public String getProcedureDescriptionFormat() {
        return procedureDescriptionFormat;
    }

    public void setProcedureDescriptionFormat(String procedureDescriptionFormat) {
        this.procedureDescriptionFormat = procedureDescriptionFormat;
    }

    public String getProcedureArea() {
        return procedureArea;
    }

    public void setProcedureArea(String procedureArea) {
        this.procedureArea = procedureArea;
    }

    public BigInteger getFeatureId() {
        return featureId;
    }

    public void setFeatureId(BigInteger featureId) {
        this.featureId = featureId;
    }

    public String getFeatureIdentifier() {
        return featureIdentifier;
    }

    public void setFeatureIdentifier(String featureIdentifier) {
        this.featureIdentifier = featureIdentifier;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getFeatureArea() {
        return featureArea;
    }

    public void setFeatureArea(String featureArea) {
        this.featureArea = featureArea;
    }

    public int getFeatureSRID() {
        return featureSRID;
    }

    public void setFeatureSRID(int featureSRID) {
        this.featureSRID = featureSRID;
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

    public String getSensorSRID() {
        return sensorSRID;
    }

    public void setSensorSRID(String sensorSRID) {
        this.sensorSRID = sensorSRID;
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

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }
}
