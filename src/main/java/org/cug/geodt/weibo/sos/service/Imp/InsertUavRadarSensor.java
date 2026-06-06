package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.UavRadarSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.UavRadarSensor;
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
public class InsertUavRadarSensor extends InsertSensorInfo implements InsertSpecificSensor {


    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertUavRadarSensor.parameter = parameter;
    }

    private UavRadarSensorMapper uavRadarSensorMapper = BeanUtils.getBean(UavRadarSensorMapper.class);

//    private UavRadarSensor uavRadarSensor = BeanUtils.getBean(UavRadarSensor.class);

    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);

    @Override
    public int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        int i = super.insertSensorInfo(describeSensorResponseEntity);
        int i1 = parseSpecificAttribute(describeSensorResponseEntity);
        return i & i1;
    }

    @Override
    public int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity)
            throws IOException, ParserConfigurationException, SAXException {
        UavRadarSensor uavRadarSensor = new UavRadarSensor();
        if (describeSensorResponseEntity.getCapabilitiesEntity() != null
                && describeSensorResponseEntity.getCapabilitiesEntity().size() > 0
                && describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                != null) {
            describeSensorResponseEntity
                    .getCapabilitiesEntity()
                    .get(0)
                    .getCapabilityEntity()
                    .forEach(
                            (capabilityEntity) -> {
                                if (capabilityEntity.getName() != null
                                        && capabilityEntity.getQuantityEntity() != null
                                        && capabilityEntity.getQuantityEntity().getValue()
                                        != null) {
                                    if (capabilityEntity.getName().equals(parameter[0])) {
                                        uavRadarSensor.setMinBandFrequencyRange(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[1])) {
                                        uavRadarSensor.setMaxBandFrequencyRange(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        uavRadarSensor.setWireBundlesNumber(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[3])) {
                                        uavRadarSensor.setVerticalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[4])) {
                                        uavRadarSensor.setHorizontalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        uavRadarSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = uavRadarSensorMapper.selectAllSensorId();
        if (strings.contains(uavRadarSensor.getSensorId())) {
            int update = uavRadarSensorMapper.updateById(uavRadarSensor);
            return update;
        } else {
            int insert = uavRadarSensorMapper.insertUavRadarSensor(uavRadarSensor);;
            return insert;
        }


        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //            uavRadarSensor.setSensorId(map.get("identifier").toString());
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
        //                            if (capability.get("name").equals("Min Band Frequency Range"))
        // {
        //
        // uavRadarSensor.setMinBandFrequencyRange(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Max Band Frequency Range"))
        // {
        //
        // uavRadarSensor.setMaxBandFrequencyRange(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Wire Bundles Number")) {
        //
        // uavRadarSensor.setWireBundlesNumber(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Vertical Fov")) {
        //
        // uavRadarSensor.setVerticalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Horizontal Fov")) {
        //
        // uavRadarSensor.setHorizontalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }

    }
}
