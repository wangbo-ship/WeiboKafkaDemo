package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.MonitorEquipmentMonitorEquipmentSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.MonitorEquipmentMonitorEquipmentSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Author WJW
 * Date 2023/7/8 9:45
 */
//@Component
public class InsertMonitorEquipmentMonitorEquipmentSensor extends InsertSensorInfo implements InsertSpecificSensor {

    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertMonitorEquipmentMonitorEquipmentSensor.parameter = parameter;
    }


    private MonitorEquipmentMonitorEquipmentSensorMapper monitorEquipmentMonitorEquipmentSensorMapper = BeanUtils.getBean(MonitorEquipmentMonitorEquipmentSensorMapper.class);

//    private MonitorEquipmentMonitorEquipmentSensor monitorEquipmentMonitorEquipmentSensor = BeanUtils.getBean(MonitorEquipmentMonitorEquipmentSensor.class);

    @Override
    public int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws Exception {
        int i = super.insertSensorInfo(describeSensorResponseEntity);
        return i;
//        parseSpecificAttribute(describeSensorResponseEntity);
    }

    @Override
    public int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity)
            throws IOException, ParserConfigurationException, SAXException {
        MonitorEquipmentMonitorEquipmentSensor monitorEquipmentMonitorEquipmentSensor = new MonitorEquipmentMonitorEquipmentSensor();
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
                                    if (capabilityEntity.getName().equals(parameter[0])) {
                                        monitorEquipmentMonitorEquipmentSensor.setResolution(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getValue()
                                                        .toString());
                                    } else if (capabilityEntity.getName().equals(parameter[1])) {
                                        monitorEquipmentMonitorEquipmentSensor.setEffectivePixel(
                                                Integer.parseInt(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        monitorEquipmentMonitorEquipmentSensor.setMonitorCameraType(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getValue()
                                                        .toString());
                                    } else if (capabilityEntity.getName().equals(parameter[3])) {
                                        monitorEquipmentMonitorEquipmentSensor.setClarity(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[4])) {
                                        monitorEquipmentMonitorEquipmentSensor
                                                .setSignalToNoiseRatio(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString());
                                    } else if (capabilityEntity.getName().equals(parameter[5])) {
                                        monitorEquipmentMonitorEquipmentSensor.setHorizontalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[6])) {
                                        monitorEquipmentMonitorEquipmentSensor.setVerticalFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[7])) {
                                        monitorEquipmentMonitorEquipmentSensor.setVideoFrameRate(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        monitorEquipmentMonitorEquipmentSensor.setSensorId(
                describeSensorResponseEntity.getIdentifier());
        monitorEquipmentMonitorEquipmentSensorMapper.insertMonitorEquipmentMonitorEquipmentSensor(
                monitorEquipmentMonitorEquipmentSensor);
        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setSensorId(map.get("identifier").toString());
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
        //                            if (capability.get("name").equals("Resolution")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setResolution(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Effective Pixel")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setEffectivePixel(Integer.parseInt(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Monitor Camera Type")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setMonitorCameraType(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Clarity")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setClarity(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Signal To Noise Ratio")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setSignalToNoiseRatio(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Horizontal Fov")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setHorizontalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Vertical Fov")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setVerticalFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Video Frame Rate")) {
        //
        // monitorEquipmentMonitorEquipmentSensor.setVideoFrameRate(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }
        return 1;
    }
}
