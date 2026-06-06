package org.cug.geodt.weibo.sos.engine.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.SensorDerive;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataFloat;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine
 * @Description
 * @date 2023/5/10 14:43
 */
@Mapper
@Repository
public interface QueryEngineMapper {
    List<SensorDataFloat> getSensorDataFloatByIds(List<String> sensorId);
    List<SensorDataString> getSensorDataStringByIds(List<String> sensorId);
    List<SensorInfo> getSensorInfoByIds(List<String> sensorId);
    //12.获取sensor_info表中所有sensor_id
    List<SensorInfo> getSensorIdInSensorInfo();
    //(辅助查询) 按id查询sensor_derive_a_quarter
    List<SensorDerive> getSensorDeriveBySensorIdAndQuarter(List<String> sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_hour
    List<SensorDerive> getSensorDeriveBySensorIdAndHour(List<String> sensorId,Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_day
    List<SensorDerive> getSensorDeriveBySensorIdAndDay(List<String> sensorId,Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_week
    List<SensorDerive> getSensorDeriveBySensorIdAndWeek(List<String> sensorId,Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_month
    List<SensorDerive> getSensorDeriveBySensorIdAndMonth(List<String> sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_year
    List<SensorDerive> getSensorDeriveBySensorIdAndYear(List<String> sensorId, Integer startTime, Integer endTime);

    List<SensorInfo> getSensorInfoBySensorType(String sensorType);

    List<SensorDataFloat> getSensorDataFloatByConditions(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);

    List<SensorDataString> getSensorDataStringByConditions(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);

    List<SensorDerive> getSensorDeriveByConditionsAndQuarter(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);

    List<SensorDerive> getSensorDeriveByConditionsAndHour(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);

    List<SensorDerive> getSensorDeriveByConditionsAndDay(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);

    List<SensorDerive> getSensorDeriveByConditionsAndWeek(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);

    List<SensorDerive> getSensorDeriveByConditionsAndMonth(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);

    List<SensorDerive> getSensorDeriveByConditionsAndYear(@Param("sensorId") List<String> sensorIds, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime, @Param("metricName") String metricName);
}
