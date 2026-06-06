package org.cug.geodt.weibo.sos.utils.csw;

/**
 * @Classname BriefRecordInfo
 * @Description TODO
 * @Date 2023/8/25 15:36
 * @Created by mjh
 */
public class BriefRecordInfo {
    private String identifier;
    private String title;
    private String type;

    private Integer crs;

    private Double minX;
    private Double minY;
    private Double maxX;
    private Double maxY;

    public BriefRecordInfo() {
    }

    public BriefRecordInfo(String identifier, String title, String type) {
        this.identifier = identifier;
        this.title = title;
        this.type = type;
    }

    public BriefRecordInfo(String identifier, String title, String type, Integer crs, Double minX, Double minY, Double maxX, Double maxY) {
        this.identifier = identifier;
        this.title = title;
        this.type = type;
        this.crs = crs;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCrs() {
        return crs;
    }

    public void setCrs(Integer crs) {
        this.crs = crs;
    }

    public Double getMinX() {
        return minX;
    }

    public void setMinX(Double minX) {
        this.minX = minX;
    }

    public Double getMinY() {
        return minY;
    }

    public void setMinY(Double minY) {
        this.minY = minY;
    }

    public Double getMaxX() {
        return maxX;
    }

    public void setMaxX(Double maxX) {
        this.maxX = maxX;
    }

    public Double getMaxY() {
        return maxY;
    }

    public void setMaxY(Double maxY) {
        this.maxY = maxY;
    }

    @Override
    public String toString() {
        return "BriefRecordInfo{" +
                "identifier='" + identifier + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", crs=" + crs +
                ", minX=" + minX +
                ", minY=" + minY +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                '}';
    }
}
