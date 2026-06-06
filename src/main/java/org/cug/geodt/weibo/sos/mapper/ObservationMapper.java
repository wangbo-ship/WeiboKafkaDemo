package org.cug.geodt.weibo.sos.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataFloat;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.vo.EntryByDay;
import org.cug.geodt.weibo.sos.vo.EntryByMonth;
import org.cug.geodt.weibo.sos.vo.EntryByYear;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ObservationMapper {
    /*
     * 二.传感器观测详情数据接口
     *   辅助查询
     * */

    //1.获取指定传感器在最近指定分钟内所有观测值信息
    List<SensorInfo> getLatestMetricValueById(String sensorId, Float intervalInMin);

    //获取指定传感器在最近指定分钟内所有观测值信息(sensor_data_float)
    List<SensorDataFloat> getLatestMetricValueByIdInFloat(String sensorId, Float intervalInMin);

    //获取指定传感器在最近指定分钟内所有观测值信息(sensor_data_string)
    List<SensorDataString> getLatestMetricValueByIdInString(String sensorId, Float intervalInMin);

    //2.获取指定传感器在最近指定分钟内指定观测值信息(sensor_data_float)
    List<SensorDataFloat> getLatestMetricValueBySensorIdAndMetricName(String sensorId, Float intervalInMin, String metricName);

    //3.获取指定传感器指定时间段内所有观测值信息
    List<SensorInfo> getMetricValueBySensorIdAndTimeRang(String sensorId, Integer startTime, Integer endTime);

    //获取指定传感器指定时间段内所有观测值信息(sensor_data_float)
    List<SensorDataFloat> getMetricValueByIdAndTimeRangInFloat(String sensorId, Integer startTime, Integer endTime);

    //获取指定传感器指定时间段内所有观测值信息(sensor_data_string)
    List<SensorDataString> getMetricValueByIdAndTimeRangInString(String sensorId, Integer startTime, Integer endTime);


    //4.获取指定传感器指定时间段内指定观测值信息(sensor_data_float)
    List<SensorInfo> getMetricValueByIdAndTimeRangAndMetricName(String sensorId, Integer startTime, Integer endTime , String metricName);

    //辅助查询(获取sensor_data_float中所有sensor_id)
    List<SensorDataFloat> getSensorIdInFloat();

    //辅助查询(获取sensor_data_float中所有metric_name)
    List<SensorDataFloat> getAllMetricNameInFloat();

    //辅助查询(获取sensor_data_string中所有sensor_id)
    List<SensorDataString> getSensorIdInString();

    /*
     * 三.传感器组观测详情数据接口
     *   辅助查询
     * */

    //1.获取一组传感器在最近指定分钟内指定观测值信息
    List<SensorInfo> getLatestMetricValueByTypeAndMetricName(String sensorType, Float intervalInMin, String metricName);

    //2.获取一组传感器指定时间段内所有观测值信息
    List<SensorInfo> getMetricValueByTypeAndTimeRang(String sensorType, Integer startTime, Integer endTime);

    //3.获取一组传感器指定时间段内指定观测值信息
    List<SensorInfo> getMetricValueByTypeAndTimeRangAndMetricName(String sensorType, Integer startTime, Integer endTime, String metricName);


    List<EntryByMonth> getDataEntryByMonth(int num);

    List<EntryByYear> getDataEntryByYear(int num);

    List<EntryByDay> getDataEntryByDay(int num);

    Long getTodayVolume(Long startTime, Long endTime);

    int getTotalDataEntries();

    long getTotalVolume();

    Long getAllDataEntry();

    Long getTodayEntry(long startTime, Long endTime);
}
