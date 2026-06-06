package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;

/**
 * @FileName SensorDataStringMapper
 * @Author WJW
 * @Date 2023/7/27 16:27
 * @Description
 */
@Mapper
public interface SensorDataStringMapper extends BaseMapper<SensorDataString> {
    @Select(
            "SELECT Max(obs_timestamp) FROM sensor_data_string where sensor_id = #{sensorId} and fk_dataset_id is null")
    Long selectMaxObsTimeStamp(@Param("sensorId") String sensorId);

    @Select(
            "SELECT Min(obs_timestamp) FROM sensor_data_string where sensor_id = #{sensorId} and fk_dataset_id is null")
    Long selectMinObsTimeStamp(@Param("sensorId") String sensorId);
}
