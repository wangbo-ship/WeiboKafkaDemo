package org.cug.geodt.weibo.sos.pojo.sensor.data;

import org.locationtech.jts.geom.Geometry;

public interface SensorData<T> {

    String sensorId = "sensorId";
    Long obsTimestamp = 0L;
    String obsTime = "obsTime";
    String metricName = "metricName";
    String sensorType = "sensorType";
    double longitude = 0;
    double latitude = 0;
    Object metricValue = null;

    String getSensorId();

    void setSensorId(String sensorId);

    Long getObsTimestamp();

    void setObsTimestamp(Long obsTimestamp);

    String getObsTime();

    void setObsTime(String obsTime);

    String getMetricName() ;

    void setMetricName(String metricName);

    T getMetricValue();

    void setMetricValue(T metricValue);

    String getSensorType();

    void setSensorType(String sensorType);

    Float getLongitude();

    void setLongitude(Float longitude);

    Float getLatitude();

    void setLatitude(Float latitude);


    Geometry getCoordinate();


}
