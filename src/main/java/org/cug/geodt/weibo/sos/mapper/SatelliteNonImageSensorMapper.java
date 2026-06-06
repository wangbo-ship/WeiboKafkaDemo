package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SatelliteNonImageSensor;
import org.springframework.stereotype.Repository;

/**
 * Author WJW
 * Date 2023/7/8 11:06
 */
@Mapper
@Repository
public interface SatelliteNonImageSensorMapper extends BaseMapper<SatelliteNonImageSensor> {

    int deleteSensorById(@Param("sensorId") String sensorId);

    void insertSatelliteNonImageSensor(SatelliteNonImageSensor satelliteNonImageSensor);

    SatelliteNonImageSensor getSensorInfoById(@Param("sensorId") String sensorId);
}
