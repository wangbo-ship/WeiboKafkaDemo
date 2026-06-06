package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Datasets;

/**
 * Author WJW
 * Date 2023/7/13 10:07
 */
@Mapper
public interface DatasetMapper extends BaseMapper<Datasets> {
    public void insertDataset(Datasets datasets);
}
