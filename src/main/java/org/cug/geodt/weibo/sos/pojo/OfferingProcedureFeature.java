package org.cug.geodt.weibo.sos.pojo;

import org.cug.geodt.weibo.sos.utils.DateUtils;

import java.math.BigInteger;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/27 14:58
 */
public class OfferingProcedureFeature {
    private BigInteger datasetId;
    private String datasetType;
    private String observationType;
    private BigInteger offeringId;
    private String offeringIdentifier;
    private String offeringName;
    private String offeringDescription;
    private String offeringIsReference;
    private Long offeringSamplingTimeStart;
    private Long offeringSamplingTimeEnd;
    private Long offeringResultTimeStart;
    private Long offeringResultTimeEnd;
    private String offeringGeom;
    private BigInteger procedureId;
    private String procedureIdentifier;
    private String procedureName;
    private String procedureDescription;
    private String procedureDescriptionFormat;
    private BigInteger featureId;
    private String featureIdentifier;
    private String featureName;
    private String featureDescription;
    private String featureArea;
    private int featureSRID;

    public String getOfferingSamplingTimeStartStr() {
        return DateUtils.convertToISO8601(offeringSamplingTimeStart*1000L);
    }

    public String getOfferingSamplingTimeEndStr() {
        return DateUtils.convertToISO8601(offeringSamplingTimeEnd*1000L);
    }

    public String getOfferingResultTimeStartStr() {
        return DateUtils.convertToISO8601(offeringResultTimeStart*1000L);
    }

    public OfferingProcedureFeature(BigInteger datasetId, String datasetType, String observationType, BigInteger offeringId, String offeringIdentifier, String offeringName, String offeringDescription, String offeringIsReference, Long offeringSamplingTimeStart, Long offeringSamplingTimeEnd, Long offeringResultTimeStart, Long offeringResultTimeEnd, String offeringGeom, BigInteger procedureId, String procedureIdentifier, String procedureName, String procedureDescription, String procedureDescriptionFormat, BigInteger featureId, String featureIdentifier, String featureName, String featureDescription, String featureArea, int featureSRID) {
        this.datasetId = datasetId;
        this.datasetType = datasetType;
        this.observationType = observationType;
        this.offeringId = offeringId;
        this.offeringIdentifier = offeringIdentifier;
        this.offeringName = offeringName;
        this.offeringDescription = offeringDescription;
        this.offeringIsReference = offeringIsReference;
        this.offeringSamplingTimeStart = offeringSamplingTimeStart;
        this.offeringSamplingTimeEnd = offeringSamplingTimeEnd;
        this.offeringResultTimeStart = offeringResultTimeStart;
        this.offeringResultTimeEnd = offeringResultTimeEnd;
        this.offeringGeom = offeringGeom;
        this.procedureId = procedureId;
        this.procedureIdentifier = procedureIdentifier;
        this.procedureName = procedureName;
        this.procedureDescription = procedureDescription;
        this.procedureDescriptionFormat = procedureDescriptionFormat;
        this.featureId = featureId;
        this.featureIdentifier = featureIdentifier;
        this.featureName = featureName;
        this.featureDescription = featureDescription;
        this.featureArea = featureArea;
        this.featureSRID = featureSRID;
    }

    public OfferingProcedureFeature() {
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

    public String getOfferingGeom() {
        return offeringGeom;
    }

    public void setOfferingGeom(String offeringGeom) {
        this.offeringGeom = offeringGeom;
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

    public String getOfferingResultTimeEndStr() {
        return DateUtils.convertToISO8601(offeringResultTimeEnd*1000L);
    }
}
