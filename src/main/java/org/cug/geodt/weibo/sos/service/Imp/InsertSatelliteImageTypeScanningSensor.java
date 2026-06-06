package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.SatelliteImageScanningSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SatelliteImageScanningSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 9:40
 */
//@Component
public class InsertSatelliteImageTypeScanningSensor extends InsertSensorInfo implements InsertSpecificSensor {

    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertSatelliteImageTypeScanningSensor.parameter = parameter;
    }

    private SatelliteImageScanningSensorMapper satelliteImageScanningSensorMapper = BeanUtils.getBean(SatelliteImageScanningSensorMapper.class);

//    private SatelliteImageScanningSensor satelliteImageScanningSensor = BeanUtils.getBean(SatelliteImageScanningSensor.class);


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
        SatelliteImageScanningSensor satelliteImageScanningSensor = new SatelliteImageScanningSensor();
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
                                        satelliteImageScanningSensor.setScanningType(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getDefinition());
                                    } else if (capabilityEntity.getName().equals(parameter[1])) {
                                        satelliteImageScanningSensor.setResolution(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        satelliteImageScanningSensor.setImageWidth(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[3])) {
                                        satelliteImageScanningSensor.setFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[4])) {
                                        satelliteImageScanningSensor.setSideAngle(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[5])) {
                                        satelliteImageScanningSensor.setNadirResolution(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        satelliteImageScanningSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = satelliteImageScanningSensorMapper.selectAllSensorId();
        if (strings.contains(satelliteImageScanningSensor.getSensorId())) {
            int update = satelliteImageScanningSensorMapper.updateById(satelliteImageScanningSensor);
            return update;
        } else {
            int insert = satelliteImageScanningSensorMapper.insertSatelliteImageScanningSensor(
                    satelliteImageScanningSensor);
            return insert;
        }



        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //            satelliteImageScanningSensor.setSensorId(map.get("identifier").toString());
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
        //                            if (capability.get("name").equals("Scanning Type")) {
        //
        // satelliteImageScanningSensor.setScanningType(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Resolution")) {
        //
        // satelliteImageScanningSensor.setResolution(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Image Width")) {
        //
        // satelliteImageScanningSensor.setImageWidth(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Fov")) {
        //
        // satelliteImageScanningSensor.setFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Side Angle")) {
        //
        // satelliteImageScanningSensor.setSideAngle(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Nadir Resolution")) {
        //
        // satelliteImageScanningSensor.setNadirResolution(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }
    }
}
