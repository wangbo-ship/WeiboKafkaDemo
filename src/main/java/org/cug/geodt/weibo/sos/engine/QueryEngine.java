package org.cug.geodt.weibo.sos.engine;

import org.cug.geodt.weibo.sos.engine.entity.Query;
import org.cug.geodt.weibo.sos.engine.entity.SensorQuery;
import org.cug.geodt.weibo.sos.engine.entity.SensorQueryLambda;
import org.cug.geodt.weibo.sos.expression.aggregator.node.AggregatorResult;
import org.cug.geodt.weibo.sos.pojo.SensorDerive;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine
 * @Description
 * @date 2023/5/10 10:52
 */
@Repository
public interface QueryEngine {
    //基础查询，支持对sensorInfo的条件查询
//    DataFrame selectBySensorIdsAndQueryDataFrame(List<String> sensorIds, Query query,Integer startTime,Integer endTime);

    List selectBySensorIdsAndQueryList(List<String> sensorIds, Query query,Integer startTime,Integer endTime);

    List selectByConditionsAndQueryList(List<String> sensorIds, Integer startTime,Integer endTime,String metricName,Query query);

    List selectBySensorIdsAndQueryList(SensorQuery sensorQuery);

    List selectBySensorIdsAndQueryList(SensorQueryLambda sensorQueryLambda);

//    DataFrame aggerateBySensorIdsAndTimeAndQueryDataFrame(List<String> sensorIds, Query query,Integer startTime,Integer endTime);

    Map<String, List<AggregatorResult>> aggregate(List<String> sensorIds , Query query, Integer startTime, Integer endTime);
    List<SensorDerive> aggerateBySensorIdsAndTimeAndQueryList(List<String> sensorIds , Query query, Integer startTime, Integer endTime);

    List<SensorInfo> getSensorInfoBySensorType(String sensorType);

    List<SensorDerive> aggerateByConditionsAndQueryList(List<String> sensorIds, Integer start, Integer end, String metricName, Query query);
}
