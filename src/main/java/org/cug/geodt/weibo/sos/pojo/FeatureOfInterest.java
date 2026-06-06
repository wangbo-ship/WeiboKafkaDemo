package org.cug.geodt.weibo.sos.pojo;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/13 14:52
 */

public class FeatureOfInterest {
    private String featureId;
    private String name;
    private String srid;
    private String area;

    public FeatureOfInterest(String featureId, String name, String srid, String area) {
        this.featureId = featureId;
        this.name = name;
        this.srid = srid;
        this.area = area;;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrid() {
        return srid;
    }

    public void setSrid(String srid) {
        this.srid = srid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "FeatureOfInterest{" +
                "featureId='" + featureId + '\'' +
                ", name='" + name + '\'' +
                ", srid='" + srid + '\'' +
                ", area=" + area +
                '}';
    }
}
