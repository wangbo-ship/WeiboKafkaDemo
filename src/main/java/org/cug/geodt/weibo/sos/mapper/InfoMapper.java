package org.cug.geodt.weibo.sos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.SensorPublishedResponse;
import org.cug.geodt.weibo.sos.vo.DataEntry;
import org.cug.geodt.weibo.sos.vo.SchemaInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface InfoMapper extends BaseMapper<SensorInfo> {
    /*
    * 一.传感器基本信息查询
    *    辅助查询
    * */

    //1.获取所有传感器的基本信息
    List<SensorInfo> getAllSensorInfo();

    //2.获取指定传感器的基础信息
    List<SensorInfo> getSensorInfoById(List<String> sensor_id);

    SensorInfo getSensorInfoById(String sensor_id);

    //3.获取指定传感器所有可用的观测信息
    List<SensorInfo> getAllMetricsById(String sensorId);

    //4.获取指定传感器、指定观测的详细信息
    List<SensorInfo> getMetricsByMetricName(String sensorId, String metricName);

    //5.查询指定传感器的经纬度
    List<SensorInfo> getLatlonById(List<String> sensorId);

    //6.获取所有传感器分组的基本信息
    List<SensorInfo> getAllSensorGroup();

    //7.获取指定分组的传感器的详细信息
    List<SensorInfo> getSensorInfoBySensorType(String groupName);

    //8.获取指定传感器最早观测时间
    List<SensorInfo> getOldestObsTimeById(String sensorId);

    //9.获取指定传感器最近观测时间
    List<SensorInfo> getLatestObsTimeById(String sensorId);

    //10.获取指定传感器指定观测日期最近观测时间戳
    List<SensorInfo> getLatestObsTimestampByIdAndDate(String sensorId, String date);

    //11.获取指定传感器指定观测日期最早观测时间戳
    List<SensorInfo> getOldestObsTimestampByIdAndDate(String sensorId, String date);

    //12.获取sensor_info表中所有sensor_id
    List<SensorInfo> getSensor_idInSensorInfo();

    //13.获取所有传感器分组的传感器的不同数量
    List<SensorInfo> getSensorNumBySensorType();

    //14.获取指定分组下传感器的测值项名称
    List<SensorInfo> getMetricsBySensorType(String groupName);

    //辅助查询(根据sensor_id获取sensor_data_string表中所有信息)
    List<SensorDataString> getSensorInfoByIdInString(String sensorId);

    //辅助查询(根据sensor_id获取sensor_data_float表中所有信息)
    List<SensorDataString> getSensorInfoByIdInFloat(String sensorId);

    //查询表sensor_data_float中所有sensor_id
    public List<SensorDataString> getSensor_id();

    //根据id获取sensor_info
    public List<SensorInfo> getBySensorId(String sensorId);

    //按json中location字段为对应的string类型的数据进行查询
    public List<SensorDataString> getByString();

    //查询指定传感器传感器最近5分钟的数据
    public List<SensorDataString> getByTime(List<String> list);

    //查询所有传感器最新观测值
    public List<SensorDataString> getLatest();

    //平台需要
    SensorInfo selectBySensorId(String sensorId);

    List<String> selectAllSensorId();

    int deleteBySensorId(String sensorId);

    // 插入传感器
    void insertSensor(SensorInfo sensorInfo);

    List<SensorInfo> getAllSensorDetailByPlatformId(List<String> platformId);

    List<SensorPublishedResponse> getPublishedInfo(int pageNum, int pageSize);

    List<String> getAllSensorId();

    List<SensorPublishedResponse> getSensorIsPublished(String sensorId);

    int getTotalRecordCount();

    SensorInfo getSensorBasicById(String sensorId);

    List<String> getAllSensorTypes();

    List<String> getSensorIdBySensorType(String sensorType);

    String getMetricBySensorId(String sensorId);

    Long getMaxTimeStampById(String sensorId);

    ArrayList<DataEntry> getSatelliteStatisticalDataByType();

    Long getSatelliteRecords();

    ArrayList<DataEntry> getUavStatisticalDataByType();

    ArrayList<DataEntry> getGroundStationStatisticalDataByType();

    ArrayList<DataEntry> getOceanStatisticalDataByType();

    Long getUavRecords();

    Long getGroundStationRecords();

    Long getOceanRecords();

    int getMonitorEquipment();

    List<SchemaInfo> getSchemaInfo();
}
