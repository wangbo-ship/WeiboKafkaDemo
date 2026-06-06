package org.cug.geodt.weibo.sos.service.Imp;

import com.alibaba.fastjson.JSON;
import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.MonitorEquipmentMonitorEquipmentSensorMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.info.Metric;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.utils.BeanUtils;
import org.cug.geodt.weibo.sos.utils.DateUtils;
import org.cug.geodt.weibo.sos.utils.parser.insertSensorRequest.InsertSensorParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author WJW
 * Date 2023/7/17 10:36
 *
 * @Description 传感器元信息主表插入操作
 */

//@Component
public class InsertSensorInfo {

    private MonitorEquipmentMonitorEquipmentSensorMapper sensorMapper = BeanUtils.getBean(MonitorEquipmentMonitorEquipmentSensorMapper.class);

    private static String[] identifierArray;

    private static String[] characteristicArray;

    private static String[] capabilityArray;

    private static String[] contactArray;

    private static String[] eventArray;

    private static String[] parameterArray;

    private static String[] positionArray;

    private static String[] valueTypeTableArray;


//    private SensorInfo newSensorInfo = BeanUtils.getBean(SensorInfo.class);


    private InfoMapper infoMapper = BeanUtils.getBean(InfoMapper.class);


    private InsertSensorParser insertSensorParser = BeanUtils.getBean(InsertSensorParser.class);

//    private Metric metric = BeanUtils.getBean(Metric.class);


    public int insertSensorInfo(DescribeSensorResponseEntity describeSensorResponseEntity) {
//        try {
//            BeanUtils.setFieldsToNull(newSensorInfo);
//            BeanUtils.setProxyToNull(newSensorInfo);
//            BeanUtils.setCallbackToNull(newSensorInfo);
//        } catch (Exception e) {
//
//        }
        SensorInfo newSensorInfo = new SensorInfo();
        newSensorInfo.setSensorId(describeSensorResponseEntity.getIdentifier());
        newSensorInfo.setDescription(describeSensorResponseEntity.getDescription());
//        newSensorInfo.setSensorType(StringUtils.SensorIdParseToSensorType(describeSensorResponseEntity.getIdentifier()));
        if (describeSensorResponseEntity.getIdentifierEntityList().size() > 0
                && describeSensorResponseEntity.getIdentifierEntityList().get(0).getTermEntityList()
                != null) {
            describeSensorResponseEntity
                    .getIdentifierEntityList()
                    .get(0)
                    .getTermEntityList()
                    .forEach(
                            (termEntity) -> {
                                if (termEntity.getValue() != null) {
                                    if (termEntity.getLabel().equals(identifierArray[0])) {
                                        newSensorInfo.setCreateUserId(termEntity.getValue());
                                    } else if (termEntity.getLabel().equals(identifierArray[1])) {
                                        newSensorInfo.setFkPlatform(termEntity.getValue());
                                    } else if (termEntity.getLabel().equals(identifierArray[2])) {
                                        newSensorInfo.setSensorLongName(termEntity.getValue());
                                    } else if (termEntity.getLabel().equals(identifierArray[3])) {
                                        newSensorInfo.setSensorShortName(termEntity.getValue());
                                    } else if (termEntity.getLabel().equals(identifierArray[4])) {
                                        newSensorInfo.setEquipmentManufacturer(
                                                termEntity.getValue());
                                    } else if (termEntity.getLabel().equals(identifierArray[5])) {
                                        newSensorInfo.setSensorType(
                                                termEntity.getValue());
                                    }
                                }
                            });
        }
        if (describeSensorResponseEntity.getCharacteristicsEntity().size() > 0
                && describeSensorResponseEntity
                .getCharacteristicsEntity()
                .get(0)
                .getCharacteristicEntityList()
                != null) {
            describeSensorResponseEntity
                    .getCharacteristicsEntity()
                    .get(0)
                    .getCharacteristicEntityList()
                    .forEach(
                            (textEntity) -> {
                                if (textEntity.getTextEntity() != null
                                        && textEntity.getTextEntity().getValue() != null) {
                                    if (textEntity
                                            .getTextEntity()
                                            .getLabel()
                                            .equals(characteristicArray[0])) {
                                        newSensorInfo.setSensorLength(
                                                Float.parseFloat(
                                                        textEntity.getTextEntity().getValue()));
                                    } else if (textEntity
                                            .getTextEntity()
                                            .getLabel()
                                            .equals(characteristicArray[1])) {
                                        newSensorInfo.setSensorHeight(
                                                Float.parseFloat(
                                                        textEntity.getTextEntity().getValue()));
                                    } else if (textEntity
                                            .getTextEntity()
                                            .getLabel()
                                            .equals(characteristicArray[2])) {
                                        newSensorInfo.setSensorWidth(
                                                Float.parseFloat(
                                                        textEntity.getTextEntity().getValue()));
                                    } else if (textEntity
                                            .getTextEntity()
                                            .getLabel()
                                            .equals(characteristicArray[3])) {
                                        newSensorInfo.setSensorWeight(
                                                Float.parseFloat(
                                                        textEntity.getTextEntity().getValue()));
                                    } else if (textEntity
                                            .getTextEntity()
                                            .getLabel()
                                            .equals(characteristicArray[4])) {
                                        newSensorInfo.setArcrole(
                                                        textEntity.getTextEntity().getValue());
                                    } else if (textEntity
                                            .getTextEntity()
                                            .getLabel()
                                            .equals(characteristicArray[5])) {
                                        newSensorInfo.setDataFormat(
                                                textEntity.getTextEntity().getValue());
                                    } else if (textEntity
                                            .getTextEntity()
                                            .getLabel()
                                            .equals(characteristicArray[6])) {
                                        newSensorInfo.setObsTheme(
                                                textEntity.getTextEntity().getValue());
                                    }
                                }
                            });
        }
        if (describeSensorResponseEntity.getCapabilitiesEntity().size() > 0
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
                                            if (capabilityEntity
                                            .getName()
                                            .equals(capabilityArray[0])) {
                                        newSensorInfo.setObsRadius(
                                                Float.parseFloat(
                                                        capabilityEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }
        if (describeSensorResponseEntity.getContactsEntity().size() > 0
                && describeSensorResponseEntity.getContactsEntity().get(0).getContactEntities()
                != null) {
            describeSensorResponseEntity
                    .getContactsEntity()
                    .get(0)
                    .getContactEntities()
                    .forEach(
                            (contactEntities) -> {
                                if (contactEntities.getOrganisationNameEntity() != null
                                        && contactEntities
                                        .getOrganisationNameEntity()
                                        .getOrganisationName()
                                        != null) {
                                    newSensorInfo.setOrganisationName(
                                            contactEntities
                                                    .getOrganisationNameEntity()
                                                    .getOrganisationName());
                                }
                                if (contactEntities.getContactInfoEntity() != null) {
                                    if (contactEntities.getContactInfoEntity().getAddressEntity()
                                            != null) {
                                        if (contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getCountry()
                                                != null) {
                                            newSensorInfo.setCountry(
                                                    contactEntities
                                                            .getContactInfoEntity()
                                                            .getAddressEntity()
                                                            .getCountry());
                                        }
                                        if (contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getDeliveryPoint() != null &&
                                                contactEntities
                                                        .getContactInfoEntity()
                                                        .getAddressEntity()
                                                        .getDeliveryPoint().size() >0
                                                ) {
                                            newSensorInfo.setDeliveryPoint(
                                                    contactEntities
                                                            .getContactInfoEntity()
                                                            .getAddressEntity()
                                                            .getDeliveryPoint().get(0));
                                        }
                                        if (contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getAdministrativeArea()
                                                != null) {
                                            newSensorInfo.setAdministrativeArea(
                                                    contactEntities
                                                            .getContactInfoEntity()
                                                            .getAddressEntity()
                                                            .getAdministrativeArea());
                                        }
                                        if (contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getCity()
                                                != null) {
                                            newSensorInfo.setCity(
                                                    contactEntities
                                                            .getContactInfoEntity()
                                                            .getAddressEntity()
                                                            .getCity());
                                        }
                                        if (contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getPostalCode()
                                                != null) {
                                            newSensorInfo.setPostalCode(
                                                    contactEntities
                                                            .getContactInfoEntity()
                                                            .getAddressEntity()
                                                            .getPostalCode());
                                        }
                                        if (contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getEmail()
                                                != null
                                                && contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getEmail()
                                                .size()
                                                > 0
                                                && contactEntities
                                                .getContactInfoEntity()
                                                .getAddressEntity()
                                                .getEmail()
                                                .get(0)
                                                != null) {
                                            newSensorInfo.setEmail(
                                                    contactEntities
                                                            .getContactInfoEntity()
                                                            .getAddressEntity()
                                                            .getEmail()
                                                            .get(0));
                                        }
                                    }
                                    if (contactEntities.getContactInfoEntity().getPhoneEntity()
                                            != null) {
                                        if (contactEntities
                                                .getContactInfoEntity()
                                                .getPhoneEntity()
                                                .getVoiceList()
                                                != null
                                                && contactEntities
                                                .getContactInfoEntity()
                                                .getPhoneEntity()
                                                .getVoiceList()
                                                .size()
                                                > 0
                                                && contactEntities
                                                .getContactInfoEntity()
                                                .getPhoneEntity()
                                                .getVoiceList()
                                                .get(0)
                                                != null) {
                                            newSensorInfo.setTelephone(
                                                    contactEntities
                                                            .getContactInfoEntity()
                                                            .getPhoneEntity()
                                                            .getVoiceList()
                                                            .get(0));
                                        }
                                    }
                                }
                            });
        }
        if (describeSensorResponseEntity.getHistoryEntity().size() > 0
                && describeSensorResponseEntity.getHistoryEntity().get(0).getEventEntityList()
                != null) {
            describeSensorResponseEntity
                    .getHistoryEntity()
                    .get(0)
                    .getEventEntityList()
                    .forEach(
                            (eventEntity) -> {
                                if (eventEntity.getLabel() != null) {
                                    if (eventEntity.getLabel().equals(eventArray[0])) {
                                        newSensorInfo.setSensorInstallTime(
                                                DateUtils.dateToLong(
                                                        eventEntity
                                                                .getTimeEntity()
                                                                .getTimeInstantEntity()
                                                                .getTimePosition()));
                                    } else if (eventEntity.getLabel().equals(eventArray[1])) {
                                        newSensorInfo.setSensorDeployTime(
                                                DateUtils.dateToLong(
                                                        eventEntity
                                                                .getTimeEntity()
                                                                .getTimeInstantEntity()
                                                                .getTimePosition()));
                                    }
                                }
                            });
        }
        if (describeSensorResponseEntity.getParameterEntity() != null
                && describeSensorResponseEntity.getParameterEntity().getParameterEntityList()
                != null) {
            describeSensorResponseEntity
                    .getParameterEntity()
                    .getParameterEntityList()
                    .forEach(
                            (parameterEntity) -> {
                                if (parameterEntity.getQuantityEntity() != null) {
                                    if (parameterEntity
                                            .getQuantityEntity()
                                            .getLabel()
                                            .equals(parameterArray[0])) {
                                        newSensorInfo.setSampleDuration(
                                                Long.valueOf(
                                                        parameterEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .longValue()));
                                    } else if (parameterEntity
                                            .getQuantityEntity()
                                            .getLabel()
                                            .equals(parameterArray[1])) {
                                        newSensorInfo.setSampleInterval(
                                                Long.valueOf(
                                                        parameterEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .longValue()));
                                    }
                                }
                            });
        }
        if (describeSensorResponseEntity.getPositionEntityList().size() > 0
                && describeSensorResponseEntity.getPositionEntityList().get(0).getCoordinateEntity()
                != null) {
            describeSensorResponseEntity
                    .getPositionEntityList()
                    .get(0)
                    .getCoordinateEntity()
                    .forEach(
                            (coordinateEntity) -> {
                                if (coordinateEntity.getQuantityEntity() != null
                                        && coordinateEntity.getName() != null) {
                                    if (coordinateEntity.getName().equals(positionArray[0])) {
                                        newSensorInfo.setSensorLatitude(
                                                Float.parseFloat(
                                                        coordinateEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (coordinateEntity
                                            .getName()
                                            .equals(positionArray[1])) {
                                        newSensorInfo.setSensorLongitude(
                                                Float.parseFloat(
                                                        coordinateEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    } else if (coordinateEntity
                                            .getName()
                                            .equals(positionArray[2])) {
                                        newSensorInfo.setSensorAltitude(
                                                Float.parseFloat(
                                                        coordinateEntity
                                                                .getQuantityEntity()
                                                                .getValue()
                                                                .toString()));
                                    }
                                }
                            });
        }

        if (describeSensorResponseEntity.getOutputEntities() != null
                && describeSensorResponseEntity.getOutputEntities().getIOEntityList() != null
                && describeSensorResponseEntity.getOutputEntities().getIOEntityList().size() > 0) {
            List<Metric> metricList = new ArrayList<>();
            describeSensorResponseEntity
                    .getOutputEntities()
                    .getIOEntityList()
                    .forEach(
                            (iOEntity) -> {
                                Metric metric = new Metric();
                                if (iOEntity.getName() != null
                                        && iOEntity.getQuantityEntity() != null) {
                                    metric.setMetricName(iOEntity.getName());
                                    if (iOEntity.getQuantityEntity().getUom() != null) {
                                        metric.setUnits(iOEntity.getQuantityEntity().getUom());
                                    }
                                    if (iOEntity.getQuantityEntity().getUom().equals("string")) {
                                        metric.setMetricType("string");
                                        metric.setLocation(valueTypeTableArray[1]);
                                    } else {
                                        metric.setMetricType("float");
                                        metric.setLocation(valueTypeTableArray[0]);
                                    }
                                    metric.setPrecision("");
                                }
                                metricList.add(metric);
                            });
            String metric = JSON.toJSONString(metricList);
            newSensorInfo.setMetrics(metric);
        }

        //判断是否是插入或更新
        List<String> strings = infoMapper.selectAllSensorId();
        if (strings.contains(newSensorInfo.getSensorId())) {
            int update = infoMapper.updateById(newSensorInfo);
            return update;
        } else {
            int insert = infoMapper.insert(newSensorInfo);
            return insert;
        }

    }

    public static String[] getIdentifierArray() {
        return identifierArray;
    }

    public static void setIdentifierArray(String[] identifierArray) {
        InsertSensorInfo.identifierArray = identifierArray;
    }

    public static String[] getCharacteristicArray() {
        return characteristicArray;
    }

    public static void setCharacteristicArray(String[] characteristicArray) {
        InsertSensorInfo.characteristicArray = characteristicArray;
    }

    public static String[] getCapabilityArray() {
        return capabilityArray;
    }

    public static void setCapabilityArray(String[] capabilityArray) {
        InsertSensorInfo.capabilityArray = capabilityArray;
    }

    public static String[] getContactArray() {
        return contactArray;
    }

    public static void setContactArray(String[] contactArray) {
        InsertSensorInfo.contactArray = contactArray;
    }

    public static String[] getEventArray() {
        return eventArray;
    }

    public static void setEventArray(String[] eventArray) {
        InsertSensorInfo.eventArray = eventArray;
    }

    public static String[] getParameterArray() {
        return parameterArray;
    }

    public static void setParameterArray(String[] parameterArray) {
        InsertSensorInfo.parameterArray = parameterArray;
    }

    public static String[] getPositionArray() {
        return positionArray;
    }

    public static void setPositionArray(String[] positionArray) {
        InsertSensorInfo.positionArray = positionArray;
    }

    public static String[] getvalueTypeTableArray() {
        return valueTypeTableArray;
    }

    public static void setValueTypeTableArray(String[] valueTypeTableArray) {
        InsertSensorInfo.valueTypeTableArray = valueTypeTableArray;
    }
}
