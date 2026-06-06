package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.UavFixSensor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:07
 */
@Mapper
@Repository
public interface UavFixSensorMapper extends BaseMapper<UavFixSensor> {

    int deleteSensorById(@Param("sensorId") String sensorId);
    int insertUavFixSensor(UavFixSensor uavFixSensor);

    UavFixSensor getSensorInfoById(@Param("sensorId") String sensorId);

    List<String> selectAllSensorId();
}
