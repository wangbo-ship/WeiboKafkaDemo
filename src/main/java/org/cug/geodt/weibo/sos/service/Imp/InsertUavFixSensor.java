package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.UavFixSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.UavFixSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 9:38
 */
//@Component
public class InsertUavFixSensor extends InsertSensorInfo implements InsertSpecificSensor {


    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertUavFixSensor.parameter = parameter;
    }

    private UavFixSensorMapper uavFixSensorMapper = BeanUtils.getBean(UavFixSensorMapper.class);

//    private UavFixSensor uavFixSensor = BeanUtils.getBean(UavFixSensor.class);

    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);
    @Override
    public int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException {
        int i = super.insertSensorInfo(describeSensorResponseEntity);
        int i1 = parseSpecificAttribute(describeSensorResponseEntity);
        return i & i1;
    }

    @Override
    public int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity)
            throws IOException, ParserConfigurationException, SAXException {
        UavFixSensor uavFixSensor = new UavFixSensor();
        if (describeSensorResponseEntity.getCapabilitiesEntity().size() > 0
                && describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                != null) {
            describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                    .stream()
                    .forEach(
                            (capabilityEntity) -> {
                                if (capabilityEntity.getName() != null
                                        && capabilityEntity.getQuantityEntity() != null
                                        && capabilityEntity.getQuantityEntity().getValue() != null) {
                                    if (capabilityEntity.getName().equals(parameter[0])) {
                                        uavFixSensor.setSampleResponseTime(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                    if (capabilityEntity.getName().equals(parameter[1])) {
                                        uavFixSensor.setDetectionAccuracy(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                    if (capabilityEntity.getName().equals(parameter[2])) {
                                        uavFixSensor.setSampleCycle(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }

                            });
        }
        uavFixSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = uavFixSensorMapper.selectAllSensorId();
        if (strings.contains(uavFixSensor.getSensorId())) {
            int update = uavFixSensorMapper.updateById(uavFixSensor);
            return update;
        } else {
            int insert = uavFixSensorMapper.insertUavFixSensor(uavFixSensor);
            return insert;
        }

        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //            uavFixSensor.setSensorId(map.get("identifier").toString());
        ////            newSensorInfo.setSensorType(map.get("identifier").toString());
        //        }
        //
        //        if(map.containsKey("capabilities")) {
        //            HashMap<String, ?> capabilities = (HashMap<String, ?>)
        // map.get("capabilities");
        //            if (capabilities.containsKey("CapabilityList")) {
        //                HashMap<String, ?> capabilityList = (HashMap<String, ?>)
        // capabilities.get("CapabilityList");
        //                if (capabilityList.containsKey("capability")) {
        //                    for (HashMap<String, ?> capability : (List<HashMap<String, ?>>)
        // capabilityList.get("capability")) {
        //                        if (capability.containsKey("Quantity") &&
        // capability.containsKey("name")) {
        //                            HashMap<String, ?> quantity = (HashMap<String, ?>)
        // capability.get("Quantity");
        //                            if (capability.get("name").equals("Sample Response Time")) {
        //
        // uavFixSensor.setSampleResponseTime(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Detection Accuracy")) {
        //
        // uavFixSensor.setDetectionAccuracy(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Sample Cycle")) {
        //
        // uavFixSensor.setSampleCycle(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }
    }
}
