package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.mapper.DatasetMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Datasets;
import org.cug.geodt.weibo.sos.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @FileName insertDataset
 * @Author WJW
 * @Date 2023/7/28 9:00
 * @Description 用于封装dataset相关操作
 */
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    Datasets datasets;

    @Override
    public void insertDataset(String key) {

    }
}
