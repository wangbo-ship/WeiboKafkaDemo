package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.GroundStationFixSensor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:04
 */
@Mapper
@Repository
public interface GroundStationFixSensorMapper extends BaseMapper<GroundStationFixSensor> {

    int insertGroundStationFixSensor(GroundStationFixSensor groundStationFixSensor);

    GroundStationFixSensor getSensorInfoById(@Param("sensorId") String sensorId);

    int deleteSensorById(@Param("sensorId") String sensorId);

    List<String> selectAllSensorId();
}
