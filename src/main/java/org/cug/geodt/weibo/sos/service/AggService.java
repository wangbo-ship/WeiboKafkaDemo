package org.cug.geodt.weibo.sos.service;

import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggService {

    ReturnVO getAggValueByIdAndMetricName(String aggName, String aggSpan, List<String> sensorId, String metricName, Integer startTime, Integer endTime);
    ReturnVO getAggValueById(String aggName, String aggSpan, List<String> sensorId, Integer startTime, Integer endTime);
    ReturnVO getAggValueBySensorType(String aggName, String aggSpan, String sensorType, List<String> sensorId,Integer startTime, Integer endTime);

    ReturnVO getAggValueByGroupAndMetricName(String aggName, String aggSpan, String sensorType, List<String> sensorId,String metricName, Integer startTime, Integer endTime);

    ReturnVO getLatestAggValueByMetricNameAndMins(String aggName, Float spanMin, List<String> sensorId, String metricName, Float intervalInMins) throws NoSuchMethodException;
    ReturnVO getLatestAggValueByMins(String aggName, Float spanMin, List<String> sensorId, Float intervalInMins) throws NoSuchMethodException;
    ReturnVO getAggValueBySensorTypeAndMetricNameAndMins(String aggName, Float spanMin, String sensorType, List<String> sensorId,String metricName, Float intervalInMins) throws NoSuchMethodException;

    ReturnVO getLatestAggValueBySensorTypeAndMins(String aggName, Float spanMin, String sensorType, List<String> sensorId,Float intervalInMins) throws NoSuchMethodException;


}
