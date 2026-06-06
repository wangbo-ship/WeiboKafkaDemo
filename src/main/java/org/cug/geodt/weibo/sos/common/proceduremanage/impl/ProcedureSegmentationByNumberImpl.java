package org.cug.geodt.weibo.sos.common.proceduremanage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.cug.geodt.weibo.sos.common.proceduremanage.ProcedureSegmentationStrategy;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/11 15:20
 */
@Component
public class ProcedureSegmentationByNumberImpl implements ProcedureSegmentationStrategy {

//    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);


    @Autowired
    InfoMapper infoMapper;

    public static  HashMap<String,Long> procedureSegmentationMap;


//单例
//    public static ProcedureSegmentationByNumberImpl getInstance() {
//        if (procedureSegmentationByNumberImpl == null) {
//            synchronized (ProcedureSegmentationByNumberImpl.class) {
//                if (procedureSegmentationByNumberImpl == null) {
//                    procedureSegmentationByNumberImpl = new ProcedureSegmentationByNumberImpl();
//                }
//            }
//        }
//        return procedureSegmentationByNumberImpl;
//    }

    public ProcedureSegmentationByNumberImpl() {
        procedureSegmentationMap = new HashMap<String,Long>();
    }

    public HashMap<String,Long> getProcedureSegmentationMap() {
        return procedureSegmentationMap;
    }

    @Override
    public void updateProcedureSegmentationMap(String key,Long number) {
        procedureSegmentationMap.put(key,procedureSegmentationMap.get(key)+number);
    }

    @Override
    public void resetProcedureSegmentationMap(String key) {
        procedureSegmentationMap.put(key,procedureSegmentationMap.get(key)%1000);
    }

    @Override
    @PostConstruct
    public void start() {
        QueryWrapper<SensorInfo> wrapper = Wrappers.query();
        wrapper.select("distinct sensor_id");
        List<SensorInfo> newSensorInfoList = infoMapper.selectList(wrapper);
//        System.out.println(newSensorInfoList);
        for (SensorInfo newSensorInfo : newSensorInfoList) {
            procedureSegmentationMap.put(newSensorInfo.getSensorId(),0L);
        }
    }


}
