package org.cug.geodt.weibo.sos.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.SensorDerive;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AggMapper {
    /*
     * 四.观测数据聚合查询接口
     *   辅助查询
     * */

    //(辅助查询) 查询sensor_derive1表中所有id
    List<SensorDerive> getSensorIdInDerive1();

    //(辅助查询) 查询sensor_derive1表中所有metricName
    List<SensorDerive> getMetricNameInDerive1();

    //(辅助查询) 按id获取sensor_derive中所有start_time
    List<SensorDerive> getStartTimeById(String sensorId);

    //(辅助查询) 按id获取sensor_derive中所有end_time
    List<SensorDerive> getEndTimeById(String sensor_id);

    //(辅助查询) 按id查询sensor_derive1中完全符合start_time和end_time的max,min,avg
    List<SensorDerive> getAllValueMeetTime(String sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_a_quarter中完全符合start_time和end_time的max,min,avg
    List<SensorDerive> getAllByValueMeeTimeQ(String sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_hour中完全符合start_time和end_time的max,min,avg
    List<SensorDerive> getAllByValueMeeTimeH(String sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_day中完全符合start_time和end_time的max,min,avg
    List<SensorDerive> getAllByValueMeeTimeD(String sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_week中完全符合start_time和end_time的max,min,avg
    List<SensorDerive> getAllByValueMeeTimeW(String sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_month中完全符合start_time和end_time的max,min,avg
    List<SensorDerive> getAllByValueMeeTimeM(String sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 按id查询sensor_derive_one_year中完全符合start_time和end_time的max,min,avg
    List<SensorDerive> getAllByValueMeeTimeY(String sensorId, Integer startTime, Integer endTime);

    //(辅助查询) 查询sensor_derive_a_quarter中start_time和end_time
    List<SensorDerive> getTimeQ(String sensorId);

    //(辅助查询) 查询sensor_derive_one_hour中start_time和end_time
    List<SensorDerive> getTimeH(String sensorId);

    //(辅助查询) 查询sensor_derive_one_day中start_time和end_time
    List<SensorDerive> getTimeD(String sensorId);

    //(辅助查询) 查询sensor_derive_one_week中start_time和end_time
    List<SensorDerive> getTimeW(String sensorId);

    //(辅助查询) 查询sensor_derive_one_month中start_time和end_time
    List<SensorDerive> getTimeM(String sensorId);

    //(辅助查询) 查询sensor_derive_one_year中start_time和end_time
    List<SensorDerive> getTimeY(String sensorId);

    //(辅助查询)按id查询sensor_data_float中所有观测时间戳
    List<SensorDataString> getObsTimeStamp(String sensorId);

    //获取指定传感器指定时间段内所有观测值信息(sensor_data_float)包含左右边界,并计算max,min,avg转为sensor_derive对象
    List<SensorDerive> getAllValueFloatToDerive(String sensorId, Integer startTime, Integer endTime);

}
