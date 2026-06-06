package org.cug.geodt.weibo.sos.pojo.sensor.info;


import java.util.List;

/**
 * @FileName Metrics 测值项
 * @Author WJW
 * @Date 2023/7/28 17:20
 * @Description
 */

public class Metrics {
    private List<Metric> metricList;

    public Metrics() {
    }

    public Metrics(List<Metric> metricList) {
        this.metricList = metricList;
    }

    public List<Metric> getMetricList() {
        return metricList;
    }

    public void setMetricList(List<Metric> metricList) {
        this.metricList = metricList;
    }
}
