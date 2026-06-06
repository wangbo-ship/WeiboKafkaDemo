package org.cug.geodt.weibo.sos.service.Imp;

import net.opengis.sensorml.x20.PhysicalComponentDocument;
import org.cug.geodt.weibo.sos.codec.decode.SensorMLDecode;
import org.cug.geodt.weibo.sos.codec.encode.DescribeSensorConverterV2;
import org.cug.geodt.weibo.sos.domain.describeSensor.*;
import org.cug.geodt.weibo.sos.domain.observation.QuantityEntity;
import org.cug.geodt.weibo.sos.mapper.*;
import org.cug.geodt.weibo.sos.pojo.sensor.info.MonitorEquipmentMonitorEquipmentSensor;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.GroundStation;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.Satellite;
import org.cug.geodt.weibo.sos.pojo.sensor.platform.UAV;
import org.cug.geodt.weibo.sos.service.PlatformService;
import org.cug.geodt.weibo.sos.utils.GroupSortExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName : PlatformServiceImpl  //类名
 * @Description :   //描述
 * @Author : cyx //作者
 * @Date: 2023/8/2  22:21
 */
@Service
public class PlatformServiceImpl<T> implements PlatformService {
    private static final String SATELLITE = "urn:satellite";
    private static final String GROUNDSTATION = "urn:groundStation";
    private static final String UAV = "urn:uav";
    private static final String MONITER = "urn:monitor";

    @Autowired
    private GroundStationMapper groundStationMapper;
    @Autowired
    private SatelliteMapper satelliteMapper;
    @Autowired
    private UavMapper uavMapper;
    @Autowired
    private MonitorEquipmentMonitorEquipmentSensorMapper sensorMapper;
    @Autowired
    private InfoMapper infoMapper;

    @Override
    public List<IdAndName> getAllIdAndName() {
        List<IdAndName> idAndNameList = new ArrayList<>();
        Map<String, List<IdAndName>> idAndNameMap= new HashMap<>();
        List<Satellite> satellites = satelliteMapper.selectAll();
        if (satellites != null) {
            for (Satellite satellite : satellites) {
                IdAndName idAndName = new IdAndName();
                idAndName.setPlatformId(satellite.getPlatformId());
                idAndName.setName(satellite.getSatelliteFullName());
                idAndNameList.add(idAndName);
            }
        }

        List<GroundStation> groundStations = groundStationMapper.selectAll();
        if (groundStations != null) {
            for (GroundStation groundStation : groundStations) {
                IdAndName idAndName = new IdAndName();
                idAndName.setPlatformId(groundStation.getPlatformId());
                idAndName.setName(groundStation.getStationFullName());
                idAndNameList.add(idAndName);
            }
        }

        List<UAV> uavs = uavMapper.selectAll();
        if (uavs != null) {
            for (UAV uav : uavs) {
                IdAndName idAndName = new IdAndName();
                idAndName.setPlatformId(uav.getPlatformId());
                idAndName.setName(uav.getUavName());
                idAndNameList.add(idAndName);
            }
        }

        List<MonitorEquipmentMonitorEquipmentSensor> monitorEquipmentSensors = sensorMapper.selectAll();
        if (monitorEquipmentSensors != null) {
            for (MonitorEquipmentMonitorEquipmentSensor monitorEquipmentSensor : monitorEquipmentSensors) {
                String sensorId = monitorEquipmentSensor.getSensorId();
                SensorInfo sensorInfo = infoMapper.selectBySensorId(sensorId);

                IdAndName idAndName = new IdAndName();
                idAndName.setPlatformId(sensorId);
                idAndName.setName(sensorInfo.getSensorLongName());
                idAndNameList.add(idAndName);
            }
        }


        // 使用 Comparator 接口定义排序规则
        Comparator<IdAndName> objectComparator = (obj1, obj2) -> {
            String type1 = GroupSortExample.extractType(obj1.getPlatformId());
            String type2 = GroupSortExample.extractType(obj2.getPlatformId());
            int typeComparison = type1.compareTo(type2);

            if (typeComparison != 0) {
                return typeComparison;
            } else {
                int number1 = GroupSortExample.extractNumber(obj1.getPlatformId());
                int number2 = GroupSortExample.extractNumber(obj2.getPlatformId());
                return Integer.compare(number1, number2);
            }
        };

        // 使用 Collections.sort 方法进行排序
        Collections.sort(idAndNameList, objectComparator);

        return idAndNameList;
    }

    @Override
    public List<IdAndName> getAllIdAndNameByType(String type) {
        List<IdAndName> allIdAndName = getAllIdAndName();
        return allIdAndName.stream()
                .filter(s -> GroupSortExample.extractType(s.getPlatformId()).equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> objConvertToXml(String platformId) {
        return satelliteToXml();
    }

    public List<String> satelliteToXml() {
        List<Satellite> satellites = satelliteMapper.selectAll();

        //存储所有xml
        List<String> xmlList = new ArrayList<>();
        if (satellites != null) {
            for (Satellite satellite : satellites) {
                //转换需要的对象
                DescribeSensorResponseEntity describeSensorResponseEntity = new DescribeSensorResponseEntity();

                //添加id
                describeSensorResponseEntity.setIdentifier(satellite.getPlatformId());

                //准备添加IdentifierEntity信息
                List<IdentifierEntity> identifierEntityList = new ArrayList<>();
                IdentifierEntity identifierEntity = new IdentifierEntity();
                List<TermEntity> termEntityList = new ArrayList<>();

                //准备添加capabilitiesEntity信息
                List<CapabilitiesEntity> capabilitiesEntityList = new ArrayList<>();
                CapabilitiesEntity capabilitiesEntity = new CapabilitiesEntity();
                List<CapabilityEntity> capabilityEntityList = new ArrayList<>();

                // 使用反射获取类的所有属性
                Field[] fields = satellite.getClass().getDeclaredFields();

                // 遍历属性，并打印不为null的属性及其值
                for (Field field : fields) {
                    try {
                        field.setAccessible(true); // 设置属性为可访问，即使是私有属性也可以访问

                        //获取属性名
                        String name = field.getName();

                        // 获取属性的值
                        Object value = field.get(satellite);

                        // 如果值不为null，则打印属性名和值
                        if (value != null && !name.equals("platformId")) {
                            //转为sensorml命名
                            String label = displayOutputName(name);
                            if (value.getClass() == Float.class || value.getClass() == Double.class) {
                                CapabilityEntity capabilityEntity = convertCapabilityEntity(label, value);
                                capabilityEntityList.add(capabilityEntity);
                            } else {
                                TermEntity termEntity = convertIdentifierEntity(label, value);
                                termEntityList.add(termEntity);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                //开始添加
                identifierEntity.setTermEntityList(termEntityList);
                identifierEntityList.add(identifierEntity);

                capabilitiesEntity.setCapabilityEntity(capabilityEntityList);
                capabilitiesEntityList.add(capabilitiesEntity);

                describeSensorResponseEntity.setIdentifierEntityList(identifierEntityList);
                describeSensorResponseEntity.setCapabilitiesEntity(capabilitiesEntityList);

                PhysicalComponentDocument convert = DescribeSensorConverterV2.convert(describeSensorResponseEntity);
                xmlList.add(convert.toString());
            }
        }

        return xmlList;
    }

    //转换TermEntity
    public TermEntity convertIdentifierEntity(String label, Object value) {
        TermEntity termEntity = new TermEntity();
        termEntity.setLabel(label);
        termEntity.setValue(value.toString());

        return termEntity;
    }

    //转换capabilityEntity
    public CapabilityEntity convertCapabilityEntity(String label, Object value) {
        CapabilityEntity capabilityEntity = new CapabilityEntity();
        QuantityEntity quantityEntity = new QuantityEntity();
        TextEntity textEntity = new TextEntity();
        textEntity.setLabel(label);
        Float f = (Float) value;
        textEntity.setValue(f.toString());
        capabilityEntity.setQuantityEntity(quantityEntity);
        capabilityEntity.setTextEntity(textEntity);

        return capabilityEntity;
    }

    @Transactional
    @Override
    public String xmlToObj(String xml) throws Exception {
        DescribeSensorResponseEntity entity =  SensorMLDecode.convert(xml);

        //转换实体类
        Object platformInstance = convertXml(entity);
        //返回主键
        String platformId = null;
        //判断入库
        if (platformInstance instanceof Satellite) {
            Satellite satellite = (Satellite) platformInstance;
            platformId = satellite.getPlatformId();
            satelliteMapper.insert(satellite);
        } else if (platformInstance instanceof GroundStation) {
            GroundStation groundStation = (GroundStation) platformInstance;
            platformId = groundStation.getPlatformId();
            groundStationMapper.insert(groundStation);
        } else if (platformInstance instanceof UAV) {
            UAV uav = (UAV) platformInstance;
            platformId = uav.getPlatformId();
            uavMapper.insert(uav);
        } else if (platformInstance instanceof MonitorEquipmentMonitorEquipmentSensor) {
            MonitorEquipmentMonitorEquipmentSensor sensor = (MonitorEquipmentMonitorEquipmentSensor) platformInstance;
            platformId = sensor.getSensorId();
            sensorMapper.insert(sensor);
            //snesorInfo入库
            InsertMonitorEquipmentMonitorEquipmentSensor equipmentSensor = new InsertMonitorEquipmentMonitorEquipmentSensor();
            entity.setIdentifier(platformId);
            equipmentSensor.insertSensor(entity);
        } else {
            throw new IllegalArgumentException("Unsupported object type");
        }
        return platformId;
    }

    @Override
    public Object selectByPlatformId(String platformId) throws ParseException {
        assert platformId != null;

        if (platformId.contains(SATELLITE)) {
            return satelliteMapper.selectById(platformId);
        } else if (platformId.contains(GROUNDSTATION)) {
            return groundStationMapper.selectById(platformId);
        } else if (platformId.contains(UAV)) {
            return uavMapper.selectById(platformId);
        } else if (platformId.contains(MONITER)) {
            MonitorEquipmentMonitorEquipmentSensor sensor = sensorMapper.selectBySensorId(platformId);
            if(sensor.getObsTheme() != null){
                String obsTheme = sensor.getObsTheme();
                String[] split = obsTheme.split(",");
                sensor.setObsThemeList(split);
            }

            SensorInfo sensor1 = infoMapper.selectBySensorId(platformId);
            MonitorEquipmentMonitorEquipmentSensor sensor2 = new MonitorEquipmentMonitorEquipmentSensor(sensor, sensor1);
            return sensor2;
        }
        return null;
    }

    @Override
    public int updateByPlatformId(String platformId, String xml) throws Exception {
        DescribeSensorResponseEntity entity =  SensorMLDecode.convert(xml);

        //转换实体类
        Object platformInstance = convertXml(entity);

        //判断是否插入成功
        int isSuccess = 0;

        //判断编辑
        if (platformInstance instanceof Satellite) {
            Satellite satellite = (Satellite) platformInstance;
            satellite.setPlatformId(platformId);
            isSuccess = satelliteMapper.updateById(satellite);
        } else if (platformInstance instanceof GroundStation) {
            GroundStation groundStation = (GroundStation) platformInstance;
            groundStation.setPlatformId(platformId);
            isSuccess = groundStationMapper.updateById(groundStation);
        } else if (platformInstance instanceof UAV) {
            UAV uav = (UAV) platformInstance;
            uav.setPlatformId(platformId);
            isSuccess = uavMapper.updateById(uav);
        } else if (platformInstance instanceof MonitorEquipmentMonitorEquipmentSensor) {
            MonitorEquipmentMonitorEquipmentSensor sensor = (MonitorEquipmentMonitorEquipmentSensor) platformInstance;
            sensor.setSensorId(platformId);
            isSuccess = sensorMapper.updateBySensorId(sensor);
            //snesorInfo更新
            InsertMonitorEquipmentMonitorEquipmentSensor equipmentSensor = new InsertMonitorEquipmentMonitorEquipmentSensor();
            entity.setIdentifier(platformId);
            equipmentSensor.insertSensor(entity);
        } else {
            throw new IllegalArgumentException("Unsupported object type");
        }
        return isSuccess;
    }

    @Override
    @Transactional
    public int deleteByPlatformId(String platformId) {
        //判断是否删除成功
        int isSuccess = 0;
        //判断删除
        if(platformId.contains(SATELLITE)){
            isSuccess = satelliteMapper.deleteById(platformId);
        } else if (platformId.contains(GROUNDSTATION)) {
            isSuccess = groundStationMapper.deleteById(platformId);
        } else if (platformId.contains(UAV)) {
            isSuccess = uavMapper.deleteById(platformId);
        } else if (platformId.contains(MONITER)) {
            int i = sensorMapper.deleteBySensorId(platformId);
            int i1 = infoMapper.deleteBySensorId(platformId);
            if(i == i1){
                isSuccess = i;
            }
        }
        return isSuccess;
    }

    @Override
    public List<SensorInfo> getAllSensorDetailByPlatformId(List platformId) {
        List allSensorDetail = infoMapper.getAllSensorDetailByPlatformId(platformId);
        return allSensorDetail;
    }


    @Transactional
    public Object convertXml(DescribeSensorResponseEntity entity) throws Exception {
        //判断对应的哪张表
        String identifier = entity.getIdentifier();
        // 使用反射获取类的所有属性
        Class<?> Class = null;
        //找到不同表对应的主键值的最大值
        Integer maxNumber = null;
        //获取主键
        String platformId = null;
        String sensorId = null;
        if (identifier != null) {
            if (identifier.contains(SATELLITE)) {
                Class = Satellite.class;
                maxNumber = satelliteMapper.getMaxNumberInSatellite();
                platformId = generateId(maxNumber, SATELLITE);
            } else if (identifier.contains(GROUNDSTATION)) {
                Class = GroundStation.class;
                maxNumber = groundStationMapper.getMaxNumberInGroundStation();
                platformId = generateId(maxNumber, GROUNDSTATION);
            } else if (identifier.contains(UAV)) {
                Class = UAV.class;
                maxNumber = uavMapper.getMaxNumberInUAV();
                platformId = generateId(maxNumber, UAV);
            } else if (identifier.contains(MONITER)) {
                Class = MonitorEquipmentMonitorEquipmentSensor.class;
                maxNumber = sensorMapper.getMaxNumberInMonitor();
                sensorId = generateId(maxNumber, MONITER);
            }
        }

        List<String> nameList = new ArrayList<>();
        // 遍历属性
        assert Class != null;
        // 创建一个实例
        Object platformInstance = Class.newInstance();
        for (Field field : Class.getDeclaredFields()) {
            field.setAccessible(true); // 设置属性为可访问，即使是私有属性也可以访问
            //获取属性名
            String name = field.getName();
            nameList.add(name);
        }

        //设置主键
        if (identifier.equals(MONITER)) {
            setPropertyValue(platformInstance, "sensorId", sensorId);
        } else {
            setPropertyValue(platformInstance, "platformId", platformId);
        }

        //判断添加String类型
        if (entity.getIdentifierEntityList() != null) {
            for (IdentifierEntity identifierEntity : entity.getIdentifierEntityList()) {
                //遍历String类型
                for (TermEntity termEntity : identifierEntity.getTermEntityList()) {
                    String label = displayInputName(termEntity.getLabel());
                    if (nameList.contains(label)) {
                        //判断是否是日期类型
                        if (label.equals("launchDate")) {
                            if (termEntity.getValue() != null && !termEntity.getValue().equals("null")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date convertedDate = dateFormat.parse(termEntity.getValue());
                                //设置属性值
                                setPropertyValue(platformInstance, label, convertedDate);
                            }
                        } else {
                            //设置属性值
                            setPropertyValue(platformInstance, label, termEntity.getValue());
                        }
                    }
                }
            }
        }

        if (entity.getCapabilitiesEntity() != null) {
            //遍历double类型
            for (CapabilitiesEntity capabilitiesEntity : entity.getCapabilitiesEntity()) {
                for (CapabilityEntity capabilityEntity : capabilitiesEntity.getCapabilityEntity()) {
                    QuantityEntity quantityEntity = capabilityEntity.getQuantityEntity();
                    String actName = null;
                    if (capabilityEntity.getName() != null && !capabilityEntity.getName().equals("null")) {
                        actName = capabilityEntity.getName();
                    } else {
                        actName = quantityEntity.getLabel();
                    }
                    String label = displayInputName(actName);
                    if (nameList.contains(label)) {
                        //设置属性值
                        setPropertyValue(platformInstance, label, quantityEntity.getValue());
                    }
                }
            }
        }

        if (entity.getPositionEntityList() != null) {
            for (PositionEntity positionEntity : entity.getPositionEntityList()) {
                for (CoordinateEntity coordinateEntity : positionEntity.getCoordinateEntity()) {
                    QuantityEntity quantityEntity = coordinateEntity.getQuantityEntity();
                    String actName = null;
                    if (coordinateEntity.getName() != null) {
                        actName = coordinateEntity.getName();
                    } else {
                        actName = quantityEntity.getLabel();
                    }
                    String label = displayInputName(actName);
                    if (nameList.contains(label)) {
                        //设置属性值
                        setPropertyValue(platformInstance, label, quantityEntity.getValue());
                    }
                }
            }
        }

        if (entity.getCharacteristicsEntity() != null) {
            for (CharacteristicsEntity characteristicsEntity : entity.getCharacteristicsEntity()) {
                for (CharacteristicEntity characteristicEntity : characteristicsEntity.getCharacteristicEntityList()) {
                    TextEntity textEntity = characteristicEntity.getTextEntity();
                    String label = displayInputName(textEntity.getLabel());
                    if (nameList.contains(label)) {
                        //设置属性值
                        setPropertyValue(platformInstance, label, textEntity.getValue());
                    }
                }
            }
        }

//        if(entity.getContactsEntity() != null){
//            for (ContactsEntity contactsEntity : entity.getContactsEntity()) {
//                for (ContactEntity contactEntity : contactsEntity.getContactEntities()) {
//                    //先遍历ContactInfoEntity
//                    ContactInfoEntity contactInfoEntity = contactEntity.getContactInfoEntity();
//                    AddressEntity addressEntity = contactInfoEntity.getAddressEntity();
//                }
//            }
//        }

        return platformInstance;
    }


    //转换sensorMl命名
    public static String displayOutputName(String output) {
        StringBuilder displayName = new StringBuilder();

        for (char c : output.toCharArray()) {
            if (Character.isUpperCase(c)) {
                displayName.append(' ');
            }
            displayName.append(c);
        }

        String result = displayName.toString().trim();
        return Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }

    public static String displayInputName(String input) {
        // 去掉空格并将首字母变为小写
        String formattedStr = input.replace(" ", "");
        char firstChar = formattedStr.charAt(0);
        char lowerFirstChar = Character.toLowerCase(firstChar);

        // 构建结果字符串
        return lowerFirstChar + formattedStr.substring(1);
    }

    private static void setPropertyValue(Object target, String propertyName, Object value) throws Exception {
        Class<?> targetClass = target.getClass();
        String setterMethodName = "set" + capitalizeFirstLetter(propertyName);
        if (value != null && value.getClass() == Double.class) {
            value = ((Double) value).floatValue();
        }
        Method setterMethod = findMethod(targetClass, setterMethodName, value != null ? value.getClass() : null);
        if (setterMethod != null) {
            setterMethod.invoke(target, value);
        } else {
            Method setterMethodString = findMethod(targetClass, setterMethodName, value != null ? value.toString().getClass() : null);
            if (setterMethodString != null) {
                setterMethodString.invoke(target, value.toString());
            } else {
                if (value != null) {
                    Integer integerValue = null;
                    if (value.getClass() == String.class) {
                        integerValue = Integer.valueOf((String) value);
                    }else {
                        integerValue = Math.round((Float) value);
                    }
                    Method setterMethodInteger = findMethod(targetClass, setterMethodName, integerValue != null ? integerValue.getClass() : null);
                    if (setterMethodInteger != null) {
                        setterMethodInteger.invoke(target, integerValue);
                    }
                }
            }
        }
    }

    private static Method findMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    //自动生成id
    public static synchronized String generateId(Integer maxCounter, String prefix) {
        if (maxCounter == null) {
            maxCounter = 0;
        }
        int newCounter = maxCounter + 1;
        return prefix + ":" + newCounter;
    }

    public static class IdAndName {
        private String name;
        private String platformId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }
    }




}
