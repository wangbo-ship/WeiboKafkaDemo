package org.cug.geodt.weibo.sos.pojo.sensor.info;

import org.springframework.stereotype.Component;

/**
 * @FileName Metric 单个测值项
 * @Author WJW
 * @Date 2023/7/28 17:22
 * @Description
 */

@Component
public class Metric {
    private String metricName;
    private String metricType;
    private String location;
    private String units;
    private String precision;

    public Metric() {
    }

    public Metric(String metricName, String metricType, String location, String units, String precision) {
        this.metricName = metricName;
        this.metricType = metricType;
        this.location = location;
        this.units = units;
        this.precision = precision;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }
}
