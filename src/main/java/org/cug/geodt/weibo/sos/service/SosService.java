package org.cug.geodt.weibo.sos.service;

import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.pojo.sensor.SensorPublish;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.SensorPublishedResponse;

import java.util.List;

/**
 * @FileName SosService @Author WJW @Date 2023/8/12 10:49 @Description
 * 定义一个sosService，将所有的sos服务都放在一个service里面
 */
public interface SosService {


    List<SensorPublishedResponse> getPublishedInfo(int pageNum, int pageSize);

    List<String> getAllSensorId();

    List<SensorPublishedResponse>  getSensorIsPublished(String sensorId);

    int insertPublishedData(SensorPublish sensorPublish);

    int deletePublishedSensor(String sensorId);

    int getTotalRecordCount();

    List<String> getAllSensorType();

    List<String> getAllSensorIdBySensorType(String sensorType);

    List<String> getMetricsBySensorId(String sensorId);

    Long  getDataTimeBySensorId(String sensorId);

    List<String> getNotPublishedIds(List<String> sensorId);

    Result getCswMetaDataOnSos();

//    SensorPublishedResponse getSensorInfoBySensorType(String sensorType);
}
