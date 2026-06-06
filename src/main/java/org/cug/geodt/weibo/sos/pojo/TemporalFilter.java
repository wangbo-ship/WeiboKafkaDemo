package org.cug.geodt.weibo.sos.pojo;


import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.pojo
 * @Description
 * @date 2023/6/17 16:02
 */

public class TemporalFilter {
    private Integer minTime;
    private Integer maxTime;

    public TemporalFilter() {
    }

    public TemporalFilter(Integer minTime, Integer maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public String getMinTimeStr() {
        return convertToISO8601(minTime*1000L);
    }

    public String getMaxTimeStr() {
        return convertToISO8601(maxTime* 1000L);
    }

    public void setMaxTimeStr(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public Integer getMinTime() {
        return minTime;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    public static String convertToISO8601(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        return formatter.format(instant);
    }


}
