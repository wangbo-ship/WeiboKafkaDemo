package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.GroundStation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName : GroupStationMapper  //接口名
 * @Description :  GroupStationMapperDao //描述
 * @Author : cyx //作者
 * @Date: 2023/8/2  22:13
 */
@Repository
@Mapper
public interface GroundStationMapper extends BaseMapper<GroundStation> {
    @Select("SELECT MAX(CAST(SUBSTRING(platform_id, 19) AS INTEGER)) FROM ground_station")
    Integer getMaxNumberInGroundStation();

    @Select("SELECT * FROM ground_station")
    List<GroundStation> selectAll();

}
