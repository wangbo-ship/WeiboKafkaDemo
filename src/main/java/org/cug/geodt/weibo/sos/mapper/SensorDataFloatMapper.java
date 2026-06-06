package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataFloat;

/**
 * Author WJW
 * Date 2023/7/13 15:41
 */
@Mapper
public interface SensorDataFloatMapper extends BaseMapper<SensorDataFloat> {

    Long selectMaxObsTimeStamp(@Param("sensorId") String sensorId);

    //    @Select("SELECT Min(obs_timestamp) FROM sensor_data_float where sensor_id = #{sensorId}
    // and fk_dataset_id is null")
    Long selectMinObsTimeStamp(@Param("sensorId") String sensorId);

}
