package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.UavCameraSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.UavCameraSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 9:39
 */
//@Component
public class InsertUavCameraSensor extends InsertSensorInfo implements InsertSpecificSensor {


    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertUavCameraSensor.parameter = parameter;
    }

    private UavCameraSensorMapper uavCameraSensorMapper = BeanUtils.getBean(UavCameraSensorMapper.class);

//    private UavCameraSensor uavCameraSensor = BeanUtils.getBean(UavCameraSensor.class);


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
        UavCameraSensor uavCameraSensor = new UavCameraSensor();
        if (describeSensorResponseEntity.getCapabilitiesEntity().size() > 0
                && describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                != null) {
            describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity()
                    .stream()
                    .forEach(
                            (capabilityEntity) -> {
                                if (capabilityEntity.getQuantityEntity() != null
                                        && capabilityEntity.getName() != null
                                        && capabilityEntity.getQuantityEntity().getValue()
                                        != null) {
                                    if (capabilityEntity.getName().equals(parameter[0]) && capabilityEntity.getQuantityEntity().getDefinition() != null) {
                                        uavCameraSensor.setImageSensorType(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getDefinition());
                                    } else if (capabilityEntity.getName().equals(parameter[1]) && capabilityEntity.getQuantityEntity().getDefinition() != null) {
                                        uavCameraSensor.setCameraModel(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getDefinition());
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        uavCameraSensor.setHorizontalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[3])) {
                                        uavCameraSensor.setVerticalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[4])) {
                                        uavCameraSensor.setEquivalentFocalDistance(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[5])) {
                                        uavCameraSensor.setMaxPixel(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        uavCameraSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = uavCameraSensorMapper.selectAllSensorId();
        if (strings.contains(uavCameraSensor.getSensorId())) {
            int update = uavCameraSensorMapper.updateById(uavCameraSensor);
            return update;
        } else {
            int insert = uavCameraSensorMapper.insertUavCameraSensor(uavCameraSensor);
            return insert;
        }

        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //            uavCameraSensor.setSensorId(map.get("identifier").toString());
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
        //                            if (capability.get("name").equals("Image Sensor Type")) {
        //
        // uavCameraSensor.setImageSensorType(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Camera Model")) {
        //
        // uavCameraSensor.setCameraModel(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Horizontal Fov")) {
        //
        // uavCameraSensor.setHorizontalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Vertical Fov")) {
        //
        // uavCameraSensor.setVerticalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Equivalent Focal
        // Distance")) {
        //
        // uavCameraSensor.setEquivalentFocalDistance(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Max Pixel")) {
        //
        // uavCameraSensor.setMaxPixel(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }

    }
}
