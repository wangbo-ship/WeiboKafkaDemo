package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SatelliteImageScanningSensor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:06
 */
@Mapper
@Repository
public interface SatelliteImageScanningSensorMapper extends BaseMapper<SatelliteImageScanningSensor> {

    int deleteSensorById(@Param("sensorId") String sensorId);
    int insertSatelliteImageScanningSensor(
            SatelliteImageScanningSensor satelliteImageScanningSensor);

    SatelliteImageScanningSensor getSensorInfoById(@Param("sensorId") String sensorId);

    List<String> selectAllSensorId();
}
