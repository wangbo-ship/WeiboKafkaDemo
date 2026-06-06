package org.cug.geodt.weibo.sos.domain.capabilities;

import org.cug.geodt.weibo.sos.domain.describeSensor.TimeEntity;

import java.util.List;

/**
 * Author WJW
 * Date 2023/6/7 20:45
 */
public class OfferingEntity {
    private String Id;   //1.0
    private String identifier;
    private String procedure;



    private List<String> procedureList;    //1.0
    private List<String> procedureDescriptionFormatList;
    private List<String> observablePropertyList;
    private List<RelatedFeatureEntity>  relatedFeatureEntityList;
    private List<String> responseFormatList;
    private String observationType;
    private String featureOfInterestType;
    private TimeEntity timeEntity;            //1.0
    private BoundedByEntityV1 boundedByEntity;     //1.0

    public OfferingEntity() {
    }
    public OfferingEntity(String id, String identifier, String procedure, List<String> procedureList, List<String> procedureDescriptionFormatList, List<String> observablePropertyList, List<RelatedFeatureEntity> relatedFeatureEntityList, List<String> responseFormatList, String observationType, String featureOfInterestType, TimeEntity timeEntity, BoundedByEntityV1 boundedByEntity) {
        Id = id;
        this.identifier = identifier;
        this.procedure = procedure;
        this.procedureList = procedureList;
        this.procedureDescriptionFormatList = procedureDescriptionFormatList;
        this.observablePropertyList = observablePropertyList;
        this.relatedFeatureEntityList = relatedFeatureEntityList;
        this.responseFormatList = responseFormatList;
        this.observationType = observationType;
        this.featureOfInterestType = featureOfInterestType;
        this.timeEntity = timeEntity;
        this.boundedByEntity = boundedByEntity;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public List<String> getProcedureList() {
        return procedureList;
    }

    public void setProcedureList(List<String> procedureList) {
        this.procedureList = procedureList;
    }

    public List<String> getProcedureDescriptionFormatList() {
        return procedureDescriptionFormatList;
    }

    public void setProcedureDescriptionFormatList(List<String> procedureDescriptionFormatList) {
        this.procedureDescriptionFormatList = procedureDescriptionFormatList;
    }

    public List<String> getObservablePropertyList() {
        return observablePropertyList;
    }

    public void setObservablePropertyList(List<String> observablePropertyList) {
        this.observablePropertyList = observablePropertyList;
    }

    public List<RelatedFeatureEntity> getRelatedFeatureEntityList() {
        return relatedFeatureEntityList;
    }

    public void setRelatedFeatureEntityList(List<RelatedFeatureEntity> relatedFeatureEntityList) {
        this.relatedFeatureEntityList = relatedFeatureEntityList;
    }

    public List<String> getResponseFormatList() {
        return responseFormatList;
    }

    public void setResponseFormatList(List<String> responseFormatList) {
        this.responseFormatList = responseFormatList;
    }

    public String getObservationType() {
        return observationType;
    }

    public void setObservationType(String observationType) {
        this.observationType = observationType;
    }

    public String getFeatureOfInterestType() {
        return featureOfInterestType;
    }

    public void setFeatureOfInterestType(String featureOfInterestType) {
        this.featureOfInterestType = featureOfInterestType;
    }

    public TimeEntity getTimeEntity() {
        return timeEntity;
    }

    public void setTimeEntity(TimeEntity timeEntity) {
        this.timeEntity = timeEntity;
    }

    public BoundedByEntityV1 getBoundedByEntity() {
        return boundedByEntity;
    }

    public void setBoundedByEntity(BoundedByEntityV1 boundedByEntity) {
        this.boundedByEntity = boundedByEntity;
    }
}
