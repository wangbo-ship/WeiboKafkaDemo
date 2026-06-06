package org.cug.geodt.weibo.sos.pojo.sensor.sos;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Author WJW
 * Date 2023/7/13 9:55
 */
@Component
public class Offerings implements Serializable {

    private String offeringId;
    private String identifier;
    private String name;
    private String description;
    private String isReference;
    private Long samplingTimeStart;
    private Long samplingTimeEnd;
    private Long resultTimeStart;
    private Long resultTimeEnd;

    public Offerings() {
    }

    public Offerings(String offeringId, String identifier, String name, String description, String isReference, Long samplingTimeStart, Long samplingTimeEnd, Long resultTimeStart, Long resultTimeEnd) {
        this.offeringId = offeringId;
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.isReference = isReference;
        this.samplingTimeStart = samplingTimeStart;
        this.samplingTimeEnd = samplingTimeEnd;
        this.resultTimeStart = resultTimeStart;
        this.resultTimeEnd = resultTimeEnd;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsReference() {
        return isReference;
    }

    public void setIsReference(String isReference) {
        this.isReference = isReference;
    }

    public Long getSamplingTimeStart() {
        return samplingTimeStart;
    }

    public void setSamplingTimeStart(Long samplingTimeStart) {
        this.samplingTimeStart = samplingTimeStart;
    }

    public Long getSamplingTimeEnd() {
        return samplingTimeEnd;
    }

    public void setSamplingTimeEnd(Long samplingTimeEnd) {
        this.samplingTimeEnd = samplingTimeEnd;
    }

    public Long getResultTimeStart() {
        return resultTimeStart;
    }

    public void setResultTimeStart(Long resultTimeStart) {
        this.resultTimeStart = resultTimeStart;
    }

    public Long getResultTimeEnd() {
        return resultTimeEnd;
    }

    public void setResultTimeEnd(Long resultTimeEnd) {
        this.resultTimeEnd = resultTimeEnd;
    }




}
