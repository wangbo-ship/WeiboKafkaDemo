package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.SatelliteImageRadarSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SatelliteImageRadarSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 9:41
 */
//@Component
public class InsertSatelliteImageTypeRadarSensor extends InsertSensorInfo implements InsertSpecificSensor {


    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertSatelliteImageTypeRadarSensor.parameter = parameter;
    }

    private SatelliteImageRadarSensorMapper satelliteImageRadarSensorMapper = BeanUtils.getBean(SatelliteImageRadarSensorMapper.class);

//    private SatelliteImageRadarSensor satelliteImageRadarSensor = BeanUtils.getBean(SatelliteImageRadarSensor.class);


    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);
    @Override
    public int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException {
        int i1 = super.insertSensorInfo(describeSensorResponseEntity);
        int i = parseSpecificAttribute(describeSensorResponseEntity);
        return i & i1;
    }

    @Override
    public int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity)
            throws IOException, ParserConfigurationException, SAXException {
        SatelliteImageRadarSensor satelliteImageRadarSensor = new SatelliteImageRadarSensor();
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
                                        satelliteImageRadarSensor.setPolarizationType(
                                                capabilityEntity
                                                        .getQuantityEntity()
                                                        .getDefinition());
                                    } else if (capabilityEntity.getName().equals(parameter[1])) {
                                        satelliteImageRadarSensor.setResolution(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[2])) {
                                        satelliteImageRadarSensor.setImageWidth(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[3])) {
                                        satelliteImageRadarSensor.setMinBandFrequencyRange(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (capabilityEntity.getName().equals(parameter[4])) {
                                        satelliteImageRadarSensor.setMaxBandFrequencyRange(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        satelliteImageRadarSensor.setSensorId(describeSensorResponseEntity.getIdentifier());
        List<String> strings = satelliteImageRadarSensorMapper.selectAllSensorId();
        if (strings.contains(satelliteImageRadarSensor.getSensorId())) {
            int update = satelliteImageRadarSensorMapper.updateById(satelliteImageRadarSensor);
            return update;
        } else {
            int insert = satelliteImageRadarSensorMapper.insertSatelliteImageRadarSensor(satelliteImageRadarSensor);
            return insert;
        }

        //        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
        //
        //        if (map.containsKey("identifier")) {
        //            satelliteImageRadarSensor.setSensorId(map.get("identifier").toString());
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
        //                            if (capability.get("name").equals("Polarization Type")) {
        //
        // satelliteImageRadarSensor.setPolarizationType(quantity.get("value").toString());
        //                            }
        //                            if (capability.get("name").equals("Resolution")) {
        //
        // satelliteImageRadarSensor.setResolution(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Image Width")) {
        //
        // satelliteImageRadarSensor.setImageWidth(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Min Band Frequency Range"))
        // {
        //
        // satelliteImageRadarSensor.setMinBandFrequencyRange(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                            if (capability.get("name").equals("Max Band Frequency Range"))
        // {
        //
        // satelliteImageRadarSensor.setMaxBandFrequencyRange(Float.parseFloat(quantity.get("value").toString()));
        //                            }
        //                        }
        //                    }
        //
        //                }
        //            }
        //        }

    }
}
