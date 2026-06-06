package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.UavScanSensor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:08
 */
@Mapper
@Repository
public interface UavScanSensorMapper extends BaseMapper<UavScanSensor> {

    int deleteSensorById(@Param("sensorId") String sensorId);
    int insertUavScanSensor(UavScanSensor uavScanSensor);

    UavScanSensor getSensorInfoById(@Param("sensorId") String sensorId);

    List<String> selectAllSensorId();
}
