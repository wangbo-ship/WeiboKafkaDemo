package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Procedures;

/**
 * Author WJW
 * Date 2023/7/13 10:08
 */
@Mapper
public interface ProcedureMapper extends BaseMapper<Procedures> {
    void insertProcedure(Procedures procedures);

}
