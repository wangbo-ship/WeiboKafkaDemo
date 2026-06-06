package org.cug.geodt.weibo.sos.utils;

import org.cug.geodt.weibo.sos.service.Imp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @FileName ValueReaderUtils
 * @Author WJW
 * @Date 2023/7/25 11:28
 * @Description 由于使用反射调用类的时候，被调的类中@Value注解不生效。
 * 遂采用ValueReaderUtils工具类，即第三方通过@Component注解交由Spring管理，
 * 达到给各个类型的传感器参数数组初始化的目的
 */
@Component
public class ValueReaderUtils {

    @Value("#{'${fieldName.info.sensor-type}'.split(',')}")
    private String[] weatherRadarWeatherRadarSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.groundStation_fix_sensor}'.split(',')}")
    private String[] groundStationFixSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.monitor_equipment_monitor_equipment_sensor}'.split(',')}")
    private String[] monitorEquipmentMonitorEquipmentSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.satellite_imagetype_photographic_sensor}'.split(',')}")
    private String[] satelliteImageTypePhotographicSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.satellite_imagetype_radar_sensor}'.split(',')}")
    private String[] satelliteImageTypeRadarSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.satellite_imagetype_scanning_sensor}'.split(',')}")
    private String[] satelliteImageTypeScanningSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.satellite_nonimage_sensor}'.split(',')}")
    private String[] satelliteNonImageSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.uav_camera_sensor}'.split(',')}")
    private String[] uavCameraSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.uav_fix_sensor}'.split(',')}")
    private String[] uavFixSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.uav_radar_sensor}'.split(',')}")
    private String[] uavRadarSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.uav_scanning_sensor}'.split(',')}")
    private String[] uavScanningSensorParameter;

    @Value("#{'${fieldName.info.sensor-fields.identifierList}'.split(',')}")
    private  String[] identifierArray;

    @Value("#{'${fieldName.info.sensor-fields.characteristicList}'.split(',')}")
    private  String[] characteristicArray;

    @Value("#{'${fieldName.info.sensor-fields.capabilityList}'.split(',')}")
    private  String[] capabilityArray;

    @Value("#{'${fieldName.info.sensor-fields.contactList}'.split(',')}")
    private  String[] contactArray;

    @Value("#{'${fieldName.info.sensor-fields.eventList}'.split(',')}")
    private  String[] eventArray;

    @Value("#{'${fieldName.info.sensor-fields.parameterList}'.split(',')}")
    private String[] parameterArray;

    @Value("#{'${fieldName.info.sensor-fields.position}'.split(',')}")
    private  String[] positionArray;

    @PostConstruct
    public void init() {
        InsertWeatherRadarWeatherRadarSensor.setParameter(weatherRadarWeatherRadarSensorParameter);
        InsertGroundStationFixSensor.setParameter(groundStationFixSensorParameter);
        InsertMonitorEquipmentMonitorEquipmentSensor.setParameter(monitorEquipmentMonitorEquipmentSensorParameter);
        InsertSatelliteImageTypePhotographicSensor.setParameter(satelliteImageTypePhotographicSensorParameter);
        InsertSatelliteImageTypeRadarSensor.setParameter(satelliteImageTypeRadarSensorParameter);
        InsertSatelliteImageTypeScanningSensor.setParameter(satelliteImageTypeScanningSensorParameter);
        InsertSatelliteNonImageSensor.setParameter(satelliteNonImageSensorParameter);
        InsertUavCameraSensor.setParameter(uavCameraSensorParameter);
        InsertUavFixSensor.setParameter(uavFixSensorParameter);
        InsertUavRadarSensor.setParameter(uavRadarSensorParameter);
        InsertUavScanningSensor.setParameter(uavScanningSensorParameter);
        InsertSensorInfo.setIdentifierArray(identifierArray);
        InsertSensorInfo.setCharacteristicArray(characteristicArray);
        InsertSensorInfo.setCapabilityArray(capabilityArray);
        InsertSensorInfo.setContactArray(contactArray);
        InsertSensorInfo.setEventArray(eventArray);
        InsertSensorInfo.setParameterArray(parameterArray);
        InsertSensorInfo.setPositionArray(positionArray);
    }

}
