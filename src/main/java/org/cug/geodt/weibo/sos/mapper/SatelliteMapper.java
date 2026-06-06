package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.Satellite;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName : SatelliteMapper  //类名
 * @Description :   //描述
 * @Author : cyx //作者
 * @Date: 2023/8/10  15:28
 */
@Repository
@Mapper
public interface SatelliteMapper extends BaseMapper<Satellite> {

    @Select("SELECT MAX(CAST(SUBSTRING(platform_id, 15) AS INTEGER)) FROM satellite")
    Integer getMaxNumberInSatellite();

    @Select("SELECT * FROM satellite")
    List<Satellite> selectAll();

}
