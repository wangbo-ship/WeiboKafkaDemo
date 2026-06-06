package org.cug.geodt.weibo.sos.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.GroundStation;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.Satellite;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.UAV;
import org.springframework.stereotype.Repository;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.mapper
 * @Description
 * @date 2023/7/7 16:44
 */
@Mapper
@Repository
public interface DescribeSensorMapper{
    SensorInfo getNewSensorInfoById(String sensorId);

    GroundStation getGroundStationByPlatformId(String platformId);
    UAV getUAVByPlatformId(String platformId);
    Satellite getSatelliteByPlatformId(String platformId);

}
