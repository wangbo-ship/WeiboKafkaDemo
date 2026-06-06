package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Offerings;

/**
 * Author WJW
 * Date 2023/7/13 10:09
 */
@Mapper
public interface OfferingMapper extends BaseMapper<Offerings> {
    void insertOffering(Offerings offerings);
}
