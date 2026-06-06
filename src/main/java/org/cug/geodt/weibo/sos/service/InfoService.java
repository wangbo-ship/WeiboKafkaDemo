package org.cug.geodt.weibo.sos.service;

import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Repository
public interface InfoService  {
    ReturnVO getAllSensorInfo();

    ReturnVO getSensorInfoById(List<String> sensorId);

    ReturnVO getAllMetricsById(List<String> sensorId);

    ReturnVO getMetricsByMetricName(String sensorId, String metricName);

    ReturnVO getLatlonById(List<String> sensorId);

    ReturnVO getAllSensorGroup();

    ReturnVO getSensorInfoBySensorType(String groupName);

    ReturnVO getOldestObsTimeById(List<String> sensorId);

    ReturnVO getLatestObsTimeById(List<String> sensorId);

    ReturnVO getLatestObsTimeStampByIdAndDate(List<String> sensorId, String date);

    ReturnVO getOldestObsTimeStampByIdAndDate(List<String> sensorId, String date);

    ReturnVO getSensorIdInSensorInfo();

    ReturnVO getSensorNumBySensorType();

    ReturnVO getMetricsBySensorType(String groupName);

    String insertSensor(String describeSensor,String sensorType) throws XmlException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    Object getBasicSensorInfoById(String sensorId) throws InvocationTargetException, IllegalAccessException;

    int deleteSensorBySensorId(String sensorId) throws InvocationTargetException, IllegalAccessException;

    int updateSensorById(String sensorType, String xml) throws XmlException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    SensorInfo getSensorBasicById(String sensorId);

    Result getSatelliteStatisticalDataByType();

    Result getUavStatisticalDataByType();

    Result getGroundStationStatisticalDataByType();

    Result getOceanStatisticalDataByType();

}
