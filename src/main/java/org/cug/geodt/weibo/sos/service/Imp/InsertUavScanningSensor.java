package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.UavScanSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.UavScanSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 9:37
 */
//@Component
public class InsertUavScanningSensor extends InsertSensorInfo implements InsertSpecificSensor {

    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertUavScanningSensor.parameter = parameter;
    }

//    private UavScanSensor uavScanSensor = BeanUtils.getBean(UavScanSensor.class);

    private UavScanSensorMapper uavScanSensorMapper = BeanUtils.getBean(UavScanSensorMapper.class);

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
        UavScanSensor uavScanSensor = new UavScanSensor();
        if (describeSensorResponseEntity.getCapabilitiesEntity().size() > 0
                && describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                != null) {
            describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                    .forEach(
                            (capabilityEntity) -> {
                                if (capabilityEntity.getName() != null
                                        && capabilityEntity.getQuantityEntity() != null
                                        && capabilityEntity.getQuantityEntity().getValue()
                                        != null) {
                                    if (capabilityEntity.getName().equals(parameter[0])) {
                                        uavScanSensor.setScanResolution(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[1])) {
                                        uavScanSensor.setMeasureSection(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        uavScanSensor.setScanPointNumber(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[3])) {
                                        uavScanSensor.setVerticalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[4])) {
                                        uavScanSensor.setHorizontalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        uavScanSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = uavScanSensorMapper.selectAllSensorId();
        if (strings.contains(uavScanSensor.getSensorId())) {
            int update = uavScanSensorMapper.updateById(uavScanSensor);
            return update;
        } else {
            int insert = uavScanSensorMapper.insertUavScanSensor(uavScanSensor);
            return insert;
        }

        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //            uavScanSensor.setSensorId(map.get("identifier").toString());
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
        //                            if (capability.get("name").equals("Scan Resolution")) {
        //
        // uavScanSensor.setScanResolution(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Measure Section")) {
        //
        // uavScanSensor.setMeasureSection(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Scan Point Number")) {
        //
        // uavScanSensor.setScanPointNumber(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Vertical Fov")) {
        //
        // uavScanSensor.setVerticalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Horizontal Fov")) {
        //
        // uavScanSensor.setHorizontalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }

    }
}
