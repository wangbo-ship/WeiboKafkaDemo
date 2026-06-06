package org.cug.geodt.weibo.sos.pojo.sensor.data;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class SensorDataString implements SensorData {
    private String sensorId;
    private Long obsTimestamp;
    private String obsTime;
    private String metricName;
    private String metricValue;
    private String srid;
    private String sensorType;
    private Float longitude;
    private Float latitude;

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    @Override
    public void setMetricValue(Object metricValue) {

    }

    public SensorDataString() {
    }

    public SensorDataString(String sensorId, Long obsTimestamp, String obsTime, String metricName, String metricValue, String srid, String sensorType, Float longitude, Float latitude) {
        this.sensorId = sensorId;
        this.obsTimestamp = obsTimestamp;
        this.obsTime = obsTime;
        this.metricName = metricName;
        this.metricValue = metricValue;
        this.srid = srid;
        this.sensorType = sensorType;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String getSensorId() {
        return sensorId;
    }

    @Override
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public Long getObsTimestamp() {
        return obsTimestamp;
    }

    @Override
    public void setObsTimestamp(Long obsTimestamp) {
        this.obsTimestamp = obsTimestamp;
    }

    @Override
    public String getObsTime() {
        return obsTime;
    }

    @Override
    public void setObsTime(String obsTime) {
        this.obsTime = obsTime;
    }

    @Override
    public String getMetricName() {
        return metricName;
    }

    @Override
    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public void setSrid(String srid) {
        this.srid = srid;
    }

    @Override
    public String getSensorType() {
        return sensorType;
    }

    @Override
    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    @Override
    public Float getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @Override
    public Float getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getSrid() {
        return srid;
    }

    @Override
    public Geometry getCoordinate() {
        if (latitude==null || longitude ==null){
            return null;
        }
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate coord = new Coordinate(longitude, latitude);
        Point point = geometryFactory.createPoint(coord);
        if (srid==null|| srid.isEmpty()){
            this.srid = "4326";
        }
        point.setSRID(Integer.parseInt(srid));
        return point;
    }
}
