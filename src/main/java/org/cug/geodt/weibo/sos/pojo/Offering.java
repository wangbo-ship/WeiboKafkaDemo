package org.cug.geodt.weibo.sos.pojo;


/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/13 14:49
 */

public class Offering {
    private String offeringId;
    private String name;
    private String description;

    public Offering() {
    }

    public Offering(String offeringId, String name, String description) {
        this.offeringId = offeringId;
        this.name = name;
        this.description = description;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
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
}
