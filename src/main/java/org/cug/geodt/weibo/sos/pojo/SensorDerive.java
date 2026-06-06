package org.cug.geodt.weibo.sos.pojo;


public class SensorDerive {
    private String sensorId;
    private String metricName;
    private Double maxValue;
    private Double minValue;
    private Double avgValue;
    private Integer startTime;
    private Integer endTime;
    private Integer valueNumber;

    public SensorDerive() {
    }

    public SensorDerive(String sensorId, String metricName, Double maxValue, Double minValue, Double avgValue, Integer startTime, Integer endTime, Integer valueNumber) {
        this.sensorId = sensorId;
        this.metricName = metricName;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.avgValue = avgValue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.valueNumber = valueNumber;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(Double avgValue) {
        this.avgValue = avgValue;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(Integer valueNumber) {
        this.valueNumber = valueNumber;
    }
}
