package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.sensor.SensorPublish;

import java.util.List;


/**
 * @FileName SensorPublishMapper
 * @Author WJW
 * @Date 2023/8/25 20:59
 * @Description
 */
@Mapper
public interface SensorPublishMapper extends BaseMapper<SensorPublish> {

    int insertPublishedData(SensorPublish sensorPublish);


    List<String> getAllPublishedIds();
}
