package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.UavCameraSensor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:07
 */
@Mapper
@Repository
public interface UavCameraSensorMapper extends BaseMapper<UavCameraSensor> {

    int deleteSensorById(@Param("sensorId") String sensorId);
    int insertUavCameraSensor(UavCameraSensor uavCameraSensor);

    UavCameraSensor getSensorInfoById(@Param("sensorId") String sensorId);

    List<String> selectAllSensorId();
}
