package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.mapper.OfferingMapper;
import org.cug.geodt.weibo.sos.mapper.SensorDataFloatMapper;
import org.cug.geodt.weibo.sos.mapper.SensorDataStringMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.Offerings;
import org.cug.geodt.weibo.sos.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @FileName insertOffering
 * @Author WJW
 * @Date 2023/7/28 8:59
 * @Description 用于封装offering相关操作
 */
@Component
public class OfferingServiceImpl implements OfferingService {

    @Value("${fieldName.offering}")
    String offeringPrefix;
    @Autowired
    Offerings offering;

    @Autowired
    SensorDataFloatMapper sensorDataFloatMapper;

    @Autowired
    SensorDataStringMapper sensorDataStingMapper;

    @Autowired
    OfferingMapper offeringMapper;

    @Override
    public void insertOffering(String key) {
//        String sensorType = StringUtils.SensorIdParseToSensorType(key);
//        offering.setDescription(offeringPrefix+sensorType);
//        offering.setName(sensorType);
//        offering.setDescription(sensorType+"package");
//        long samplingTimeEnd = Math.max(sensorDataFloatMapper.selectMaxObsTimeStamp(key), sensorDataStingMapper.selectMaxObsTimeStamp(key));
//        long samplingTimeStart = Math.min(sensorDataFloatMapper.selectMaxObsTimeStamp(key), sensorDataStingMapper.selectMinObsTimeStamp(key));
//        offering.setSamplingTimeStart(samplingTimeStart);
//        offering.setSamplingTimeEnd(samplingTimeEnd);
////        offering.setResultTimeStart();
////        offering.setResultTimeEnd();
//        offeringMapper.insert(offering);
    }

}
