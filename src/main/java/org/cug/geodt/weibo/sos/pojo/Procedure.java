package org.cug.geodt.weibo.sos.pojo;


/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/14 16:39
 */
public class Procedure {
    private String procedureId;
    private String name;
    private String description;
    private String procedureDescriptionFormat;

    public Procedure() {
    }

    public Procedure(String procedureId, String name, String description, String procedureDescriptionFormat) {
        this.procedureId = procedureId;
        this.name = name;
        this.description = description;
        this.procedureDescriptionFormat = procedureDescriptionFormat;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
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
}
