package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SatelliteImageRadarSensor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:05
 */
@Mapper
@Repository
public interface SatelliteImageRadarSensorMapper extends BaseMapper<SatelliteImageRadarSensor> {

    int deleteSensorById(@Param("sensorId") String sensorId);
    int insertSatelliteImageRadarSensor(SatelliteImageRadarSensor satelliteImageRadarSensor);

    SatelliteImageRadarSensor getSensorInfoById(@Param("sensorId") String sensorId);

    List<String> selectAllSensorId();
}
