package org.cug.geodt.weibo.sos.pojo.sensor.sos;


import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Author WJW
 * Date 2023/7/13 10:04
 */
@Component
public class Procedures implements Serializable {
    private String procedureId;
    private String identifier;
    private String name;
    private String description;
    private String procedureDescriptionFormat;
    private String area;

    public Procedures() {
    }

    public Procedures(String procedureId, String identifier, String name, String description, String procedureDescriptionFormat, String area) {
        this.procedureId = procedureId;
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.procedureDescriptionFormat = procedureDescriptionFormat;
        this.area = area;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
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

    public String getProcedureDescriptionFormat() {
        return procedureDescriptionFormat;
    }

    public void setProcedureDescriptionFormat(String procedureDescriptionFormat) {
        this.procedureDescriptionFormat = procedureDescriptionFormat;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
