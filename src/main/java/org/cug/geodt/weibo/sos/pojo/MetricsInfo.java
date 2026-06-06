package org.cug.geodt.weibo.sos.pojo;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/5/27 11:04
 */
public class MetricsInfo {
    private String metricName;

    private String metricType;

    private String location;

    public MetricsInfo(String metricName, String metricType, String location) {
        this.metricName = metricName;
        this.metricType = metricType;
        this.location = location;
    }

    public MetricsInfo() {
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "MetricsInfo{" +
                "metricName='" + metricName + '\'' +
                ", metricType='" + metricType + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
