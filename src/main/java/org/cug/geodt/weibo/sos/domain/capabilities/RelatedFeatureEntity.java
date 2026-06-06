package org.cug.geodt.weibo.sos.domain.capabilities;


/**
 * Author WJW
 * Date 2023/6/7 20:48
 */
public class RelatedFeatureEntity {
    private String role;
    private String target;
    private String Href;

    public RelatedFeatureEntity(String role, String target, String href) {
        this.role = role;
        this.target = target;
        Href = href;
    }

    public RelatedFeatureEntity() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getHref() {
        return Href;
    }

    public void setHref(String href) {
        Href = href;
    }
}
