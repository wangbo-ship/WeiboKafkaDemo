package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.SatelliteNonImageSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SatelliteNonImageSensor;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Author WJW
 * Date 2023/7/8 9:39
 */
//@Component
public class InsertSatelliteNonImageSensor extends InsertSensorInfo implements InsertSpecificSensor {

    private static String[] parameter;

    public static String[] getParameter() {
        return parameter;
    }

    public static void setParameter(String[] parameter) {
        InsertSatelliteNonImageSensor.parameter = parameter;
    }

    private SatelliteNonImageSensorMapper satelliteNonImageSensorMapper = BeanUtils.getBean(SatelliteNonImageSensorMapper.class);

    private SatelliteNonImageSensor satelliteNonImageSensor = BeanUtils.getBean(SatelliteNonImageSensor.class);

    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);
    @Override
    public int insertSensor(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        int i = super.insertSensorInfo(describeSensorResponseEntity);
        parseSpecificAttribute(describeSensorResponseEntity);
        return i;
    }

    @Override
    public int parseSpecificAttribute(DescribeSensorResponseEntity describeSensorResponseEntity) throws IOException, ParserConfigurationException, SAXException {
//        if (describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity() != null) {
//            describeSensorResponseEntity.getCapabilitiesEntity().get(0).getCapabilityEntity().stream().forEach(
//                    (capabilityEntity) -> {
//                        if (capabilityEntity.getName().equals(parameter[0])) {
//                            satelliteNonImageSensor.setP(capabilityEntity.getTextEntity().getValue());
//                        } else if (capabilityEntity.getName().equals(parameter[1])) {
//                            satelliteNonImageSensor.set(Float.parseFloat(capabilityEntity.getQuantityEntity().getValue().toString()));
//                        } else if (capabilityEntity.getName().equals(parameter[2])) {
//                            satelliteNonImageSensor.setTimeResolution(Float.parseFloat(capabilityEntity.getQuantityEntity().getValue().toString()));
//                        }
//                    }
//            );
//        }
//        satelliteNonImageSensorMapper.insert(satelliteNonImageSensor);
//        HashMap<String, ?> map = StringUtils.XmlToHashMap(xml);
//
//        if (map.containsKey("identifier")) {
//            satelliteNonImageSensor.setSensorId(map.get("identifier").toString());
////            newSensorInfo.setSensorType(map.get("identifier").toString());
//        }
//
//        if(map.containsKey("capabilities")) {
//            HashMap<String, ?> capabilities = (HashMap<String, ?>) map.get("capabilities");
//            if (capabilities.containsKey("CapabilityList")) {
//                HashMap<String, ?> capabilityList = (HashMap<String, ?>) capabilities.get("CapabilityList");
//                if (capabilityList.containsKey("capability")) {
//                    for (HashMap<String, ?> capability : (List<HashMap<String, ?>>) capabilityList.get("capability")) {
//                        if (capability.containsKey("Quantity") && capability.containsKey("name")) {
//                            HashMap<String, ?> quantity = (HashMap<String, ?>) capability.get("Quantity");
//                            if (capability.get("name").equals("Polarization Description")) {
//                                satelliteNonImageSensor.setPolarizationDescription(quantity.get("value").toString());
//                            }
//                            if (capability.get("name").equals("Max Sensing Distance")) {
//                                satelliteNonImageSensor.setMaxSensingDistance(Float.parseFloat(quantity.get("value").toString()));
//                            }
//                            if (capability.get("name").equals("Time Resolution")) {
//                                satelliteNonImageSensor.setTimeResolution(Float.parseFloat(quantity.get("value").toString()));
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
    return  1;
    }
}
