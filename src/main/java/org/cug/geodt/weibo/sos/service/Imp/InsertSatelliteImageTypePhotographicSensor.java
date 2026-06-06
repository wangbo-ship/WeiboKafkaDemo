package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.SatelliteImagePhotographicSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SatelliteImagePhotographicSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 9:42
 */
//@Component
public class InsertSatelliteImageTypePhotographicSensor extends InsertSensorInfo implements InsertSpecificSensor {

    private static String[] parameter;


    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertSatelliteImageTypePhotographicSensor.parameter = parameter;
    }


    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);
    private SatelliteImagePhotographicSensorMapper satelliteImagePhotographicSensorMapper = BeanUtils.getBean(SatelliteImagePhotographicSensorMapper.class);

//    private SatelliteImagePhotographicSensor satelliteImagePhotographicSensor = BeanUtils.getBean(SatelliteImagePhotographicSensor.class);

    @Override
    public int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        int i1 = super.insertSensorInfo(describeSensorResponseEntity);
        int i = parseSpecificAttribute(describeSensorResponseEntity);
        return i & i1;
    }

    @Override
    public int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity)
            throws IOException, ParserConfigurationException, SAXException {
        SatelliteImagePhotographicSensor satelliteImagePhotographicSensor = new SatelliteImagePhotographicSensor();
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
                                        satelliteImagePhotographicSensor.setPhotographyType(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getDefinition());
                                    } else if (capabilityEntity.getName().equals(parameter[1])) {
                                        satelliteImagePhotographicSensor.setResolution(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        satelliteImagePhotographicSensor.setImageWidth(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[3])) {
                                        satelliteImagePhotographicSensor.setFov(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[4])) {
                                        satelliteImagePhotographicSensor.setFocalLength(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[5])) {
                                        satelliteImagePhotographicSensor.setForwardOverlap(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        satelliteImagePhotographicSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = satelliteImagePhotographicSensorMapper.selectAllSensorId();
        if (strings.contains(satelliteImagePhotographicSensor.getSensorId())) {
            int update = satelliteImagePhotographicSensorMapper.updateById(satelliteImagePhotographicSensor);
            return update;
        } else {
            int insert = satelliteImagePhotographicSensorMapper.insertSatelliteImagePhotographicSensor(
                    satelliteImagePhotographicSensor);
            return insert;
        }

        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //
        // satelliteImagePhotographicSensor.setSensorId(map.get("identifier").toString());
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
        //                            if (capability.get("name").equals("Photography Type")) {
        //
        // satelliteImagePhotographicSensor.setPhotographyType(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Resolution")) {
        //
        // satelliteImagePhotographicSensor.setResolution(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Image Width")) {
        //
        // satelliteImagePhotographicSensor.setImageWidth(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Fov")) {
        //
        // satelliteImagePhotographicSensor.setFov(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Focal Length")) {
        //
        // satelliteImagePhotographicSensor.setFocalLength(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Forward Overlap")) {
        //
        // satelliteImagePhotographicSensor.setForwardOverlap(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }

    }
}
