package org.cug.geodt.weibo.sos.utils;

import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.service.Imp.*;
import org.cug.geodt.weibo.sos.service.InsertSpecificSensor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Author WJW
 * Date 2023/7/7 21:08
 */
//@Slf4j
@Repository
@Component
public class SensorTypeConvertUtil {
    @Value("#{'${fieldName.info.sensor-type}'.split(',')}")
    private  String[] sensorTypes;
    private  Map<String,Class<? extends InsertSpecificSensor>> SensorTableConvertMap;
    @PostConstruct
    public void initSensorType () {
        SensorTableConvertMap = new HashMap<>();
        SensorTableConvertMap.put(sensorTypes[0], InsertWeatherRadarWeatherRadarSensor.class);
        SensorTableConvertMap.put(sensorTypes[1], InsertUavScanningSensor.class);
        SensorTableConvertMap.put(sensorTypes[2], InsertUavRadarSensor.class);
        SensorTableConvertMap.put(sensorTypes[3], InsertUavFixSensor.class);
        SensorTableConvertMap.put(sensorTypes[4], InsertUavCameraSensor.class);
        SensorTableConvertMap.put(sensorTypes[5], InsertSatelliteNonImageSensor.class);
        SensorTableConvertMap.put(sensorTypes[6], InsertSatelliteImageTypeScanningSensor.class);
        SensorTableConvertMap.put(sensorTypes[7], InsertSatelliteImageTypeRadarSensor.class);
        SensorTableConvertMap.put(sensorTypes[8], InsertSatelliteImageTypePhotographicSensor.class);
        SensorTableConvertMap.put(sensorTypes[9], InsertMonitorEquipmentMonitorEquipmentSensor.class);
        SensorTableConvertMap.put(sensorTypes[10], InsertGroundStationFixSensor.class);
    }
    public String InsertSpecificTable(String sensorType, DescribeSensorResponseEntity describeSensorResponseEntity) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String id = StringUtils.SensorIdGenerator(sensorType);
        describeSensorResponseEntity.setIdentifier(id);
        Class clazz = SensorTableConvertMap.get(sensorType);
        Method insertSensor = clazz.getDeclaredMethod("insertSensor",DescribeSensorResponseEntity.class);
        insertSensor.setAccessible(true);
        insertSensor.invoke(clazz.newInstance(),describeSensorResponseEntity);
        return id;
    }

    public int updateSpecificTable(String sensorType, DescribeSensorResponseEntity describeSensorResponseEntity) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clazz = SensorTableConvertMap.get(sensorType);
        Method insertSensor = clazz.getDeclaredMethod("insertSensor",DescribeSensorResponseEntity.class);
        insertSensor.setAccessible(true);
        int id = (int) insertSensor.invoke(clazz.newInstance(), describeSensorResponseEntity);
        return id;
    }



}
