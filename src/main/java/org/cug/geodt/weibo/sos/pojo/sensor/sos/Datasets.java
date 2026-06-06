package org.cug.geodt.weibo.sos.pojo.sensor.sos;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Author WJW
 * Date 2023/7/13 9:54
 */
@Component
public class Datasets implements Serializable {
    private int datasetId;
    private String datasetType;
    private String observationType;
    private String fkProcedureId;
    private String fkOfferingId;
    private String fkFeatureId;
    private String fkPlatformId;
    private String fkSensorId;

    public Datasets() {
    }

    public Datasets(int datasetId, String datasetType, String observationType, String fkProcedureId, String fkOfferingId, String fkFeatureId, String fkPlatformId, String fkSensorId) {
        this.datasetId = datasetId;
        this.datasetType = datasetType;
        this.observationType = observationType;
        this.fkProcedureId = fkProcedureId;
        this.fkOfferingId = fkOfferingId;
        this.fkFeatureId = fkFeatureId;
        this.fkPlatformId = fkPlatformId;
        this.fkSensorId = fkSensorId;
    }

    public int getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(int datasetId) {
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

    public String getFkProcedureId() {
        return fkProcedureId;
    }

    public void setFkProcedureId(String fkProcedureId) {
        this.fkProcedureId = fkProcedureId;
    }

    public String getFkOfferingId() {
        return fkOfferingId;
    }

    public void setFkOfferingId(String fkOfferingId) {
        this.fkOfferingId = fkOfferingId;
    }

    public String getFkFeatureId() {
        return fkFeatureId;
    }

    public void setFkFeatureId(String fkFeatureId) {
        this.fkFeatureId = fkFeatureId;
    }

    public String getFkPlatformId() {
        return fkPlatformId;
    }

    public void setFkPlatformId(String fkPlatformId) {
        this.fkPlatformId = fkPlatformId;
    }

    public String getFkSensorId() {
        return fkSensorId;
    }

    public void setFkSensorId(String fkSensorId) {
        this.fkSensorId = fkSensorId;
    }

}
