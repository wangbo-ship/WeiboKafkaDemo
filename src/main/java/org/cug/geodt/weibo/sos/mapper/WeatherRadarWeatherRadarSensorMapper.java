package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.WeatherRadarWeatherRadarSensor;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:08
 */
@Mapper
public interface WeatherRadarWeatherRadarSensorMapper extends BaseMapper<WeatherRadarWeatherRadarSensor> {


    int insertWeatherRadarWeatherRadarSensor(
            WeatherRadarWeatherRadarSensor weatherRadarWeatherRadarSensor);

    WeatherRadarWeatherRadarSensor getSensorInfoById(@Param("sensorId") String sensorId);

    int deleteSensorById(@Param("sensorId") String sensorId);

    List<String> selectAllSensorId();
}
