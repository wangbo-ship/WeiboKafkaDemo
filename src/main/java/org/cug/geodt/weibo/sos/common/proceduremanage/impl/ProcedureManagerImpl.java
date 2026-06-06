package org.cug.geodt.weibo.sos.common.proceduremanage.impl;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.sos.common.proceduremanage.ProcedureManager;
import org.cug.geodt.weibo.sos.mapper.DatasetMapper;
import org.cug.geodt.weibo.sos.mapper.OfferingMapper;
import org.cug.geodt.weibo.sos.mapper.ProcedureMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Datasets;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Offerings;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Procedures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * Author WJW
 * Date 2023/7/13 9:38
 */
@Slf4j
@Component
public class ProcedureManagerImpl implements ProcedureManager {

    @Value("scanFrequency")
    String scanFrequency;

    @Autowired
    ProcedureSegmentationByNumberImpl procedureSegmentationByNumber;

    @Autowired
    Datasets dataset;

    @Autowired
    Offerings offering;

    @Autowired
    Procedures procedure;

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    OfferingMapper offeringMapper;

    @Autowired
    ProcedureMapper procedureMapper;

    @Override
    public void ScanProcedureMap() throws InterruptedException {
        while (true) {
            Thread.sleep(Long.parseLong(scanFrequency));
            Set<String> keys = ProcedureSegmentationByNumberImpl.procedureSegmentationMap.keySet();
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                log.info(""+it+"记录条数为"+ProcedureSegmentationByNumberImpl.procedureSegmentationMap.get(keys));
//                log.info("device50的记录条数为:"+ProcedureSegmentationByNumberImpl.procedureSegmentationMap.get("device50"));
                String key = (String) it.next();
                if (ProcedureSegmentationByNumberImpl.procedureSegmentationMap.get(key) > 1000L) {
                    //入库
//                    offering.set
//                    dataset.setDatasetType();
//                    dataset.setObservationType();
//                    dataset.setFkProcedureId();
//                    dataset.setFkOfferingId();
//                    dataset.setFkFeatureId();
//                    dataset.setFkSensorId(key);
//                    dataset.setFkPlatformId();
                    log.info("生成一条procedure记录:!!!!!!!!!!!!!!");
//                    datasetMapper.insert(dataset);
//                    offeringMapper.insert(offering);
//                    procedureMapper.insert(procedure);
                    procedureSegmentationByNumber.resetProcedureSegmentationMap(key);
                }
            }
        }
    }
}
