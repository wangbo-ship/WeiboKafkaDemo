package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.UAV;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName : UavMapper  //接口名
 * @Description :   //描述
 * @Author : cyx //作者
 * @Date: 2023/8/10  15:29
 */
@Repository
@Mapper
public interface UavMapper extends BaseMapper<UAV> {

    @Select("SELECT MAX(CAST(SUBSTRING(platform_id, 9) AS INTEGER)) FROM uav")
    Integer getMaxNumberInUAV();

    @Select("SELECT * FROM uav")
    List<UAV> selectAll();

}
