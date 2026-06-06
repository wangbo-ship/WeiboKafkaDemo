package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.cug.geodt.weibo.sos.pojo.sensor.info.MonitorEquipmentMonitorEquipmentSensor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author WJW
 * Date 2023/7/8 11:05
 */
@Mapper
@Repository
public interface MonitorEquipmentMonitorEquipmentSensorMapper extends BaseMapper<MonitorEquipmentMonitorEquipmentSensor> {


    int deleteSensorById(@Param("sensorId") String sensorId);
    MonitorEquipmentMonitorEquipmentSensor getSensorInfoById(@Param("sensorId") String sensorId);

    @Select("SELECT MAX(CAST(SUBSTRING(sensor_id, 13) AS INTEGER)) FROM monitor_equipment_monitor_equipment_sensor_ground_stay")
    Integer getMaxNumberInMonitor();

    @Select("SELECT sensor_id FROM monitor_equipment_monitor_equipment_sensor_ground_stay")
    List<String> selectAllSensorId();

    @Select("SELECT monitor_equipment_full_name," +
            "sensor_id," +
            "monitor_camera_type," +
            "obs_theme," +
            "intend_application," +
            "equipment_manufacturer," +
            "image_sensor_type," +
            "resolution," +
            "effective_pixel," +
            "clarity," +
            "signal_to_noise_ratio," +
            "horizontal_fov," +
            "vertical_fov," +
            "video_frame_rate FROM monitor_equipment_monitor_equipment_sensor_ground_stay")
    List<MonitorEquipmentMonitorEquipmentSensor> selectAll();

    @Select("SELECT monitor_equipment_full_name," +
            "sensor_id," +
            "monitor_camera_type," +
            "obs_theme," +
            "intend_application," +
            "equipment_manufacturer," +
            "image_sensor_type," +
            "resolution," +
            "effective_pixel," +
            "clarity," +
            "signal_to_noise_ratio," +
            "horizontal_fov," +
            "vertical_fov," +
            "video_frame_rate FROM monitor_equipment_monitor_equipment_sensor_ground_stay WHERE sensor_id = #{sensorId}")
    MonitorEquipmentMonitorEquipmentSensor selectBySensorId(String sensorId);

    @Update("UPDATE monitor_equipment_monitor_equipment_sensor_ground_stay " +
            "SET" +
            "    monitor_equipment_full_name = #{monitorEquipmentFullName}," +
            "    monitor_camera_type = #{monitorCameraType}," +
            "    obs_theme = #{obsTheme}," +
            "    intend_application = #{intendApplication}," +
            "    equipment_manufacturer = #{equipmentManufacturer}," +
            "    image_sensor_type = #{imageSensorType}," +
            "    resolution = #{resolution}," +
            "    effective_pixel = #{effectivePixel}," +
            "    clarity = #{clarity}," +
            "    signal_to_noise_ratio = #{signalToNoiseRatio}," +
            "    horizontal_fov = #{horizontalFov}," +
            "    vertical_fov = #{verticalFov}," +
            "    video_frame_rate = #{videoFrameRate} " +
            "WHERE sensor_id = #{sensorId}")
    int updateBySensorId(MonitorEquipmentMonitorEquipmentSensor sensor);

    @Delete("DELETE FROM monitor_equipment_monitor_equipment_sensor_ground_stay " +
            "WHERE sensor_id = #{sensorId}")
    int deleteBySensorId(String sensorId);

    int insertMonitorEquipmentMonitorEquipmentSensor(
            MonitorEquipmentMonitorEquipmentSensor monitorEquipmentMonitorEquipmentSensor);

}
