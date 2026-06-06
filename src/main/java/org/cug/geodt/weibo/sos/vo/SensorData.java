package org.cug.geodt.weibo.sos.vo;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.vo
 * @Description
 * @date 2023/1/14 12:54
 */
public class SensorData {
    private String obsTime;
    private Double metricValue;

    public SensorData(String obsTime, Double metricValue) {
        this.obsTime = obsTime;
        this.metricValue = metricValue;
    }

    public String getObsTime() {
        return obsTime;
    }

    public void setObsTime(String obsTime) {
        this.obsTime = obsTime;
    }

    public Double getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(Double metricValue) {
        this.metricValue = metricValue;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "obsTime='" + obsTime + '\'' +
                ", metricValue=" + metricValue +
                '}';
    }
}
