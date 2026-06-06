package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.ObservationMapper;
import org.cug.geodt.weibo.sos.mapper.SensorPublishMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.SensorPublish;
import org.cug.geodt.weibo.sos.pojo.sensor.sos.SensorPublishedResponse;
import org.cug.geodt.weibo.sos.service.InfoService;
import org.cug.geodt.weibo.sos.service.SosService;
import org.cug.geodt.weibo.sos.utils.LocationHandle;
import org.cug.geodt.weibo.sos.vo.CswMetaDataOnSos;
import org.cug.geodt.weibo.sos.vo.SchemaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @FileName SosServiceImpl @Author WJW @Date 2023/8/12 10:50 @Description */
@Service
public class SosServiceImpl implements SosService {



    @Autowired private InfoService infoService;

    @Autowired private Result result;

    @Autowired private InfoMapper infoMapper;

    @Autowired private SensorPublishMapper sensorPublishMapper;

    @Autowired
    private ObservationMapper observationMapper;

    @Autowired
    private CswMetaDataOnSos cswMetaDataOnSos;


    @Override
    public List<SensorPublishedResponse> getPublishedInfo(int pageNum, int pageSize) {
        List<SensorPublishedResponse> publishedInfo = infoMapper.getPublishedInfo((pageNum - 1) * pageSize, pageSize);
        return publishedInfo;
    }

    @Override
    public List<String> getAllSensorId() {
        List<String> allSensorId = infoMapper.getAllSensorId();
        return allSensorId;
    }

    @Override
    public  List<SensorPublishedResponse> getSensorIsPublished(String sensorId) {
        return infoMapper.getSensorIsPublished(sensorId);
    }

    @Override
    public int insertPublishedData(SensorPublish sensorPublish) {
        return sensorPublishMapper.insertPublishedData(sensorPublish);
    }

    @Override
    public int deletePublishedSensor(String sensorId) {
        int i = sensorPublishMapper.deleteById(sensorId);
        return i;
    }

    @Override
    public int getTotalRecordCount() {
        int num = infoMapper.getTotalRecordCount();
        return num;
    }

    @Override
    public List<String> getAllSensorType() {
        List<String> sensorTypes = infoMapper.getAllSensorTypes();
        return sensorTypes;
    }

    @Override
    public List<String> getAllSensorIdBySensorType(String sensorType) {
        List<String> sensorIds = infoMapper.getSensorIdBySensorType(sensorType);
        return sensorIds;
    }

    @Override
    public List<String> getMetricsBySensorId(String sensorId) {
        String metrics = infoMapper.getMetricBySensorId(sensorId);
        List<String> strings = LocationHandle.metricNameHandleOne(metrics);
        return strings;
    }

    @Override
    public Long getDataTimeBySensorId(String sensorId) {
        Long timeStamp = infoMapper.getMaxTimeStampById(sensorId);
        return timeStamp;
    }

    @Override
    public List<String> getNotPublishedIds(List<String> sensorIds) {
        List<String> allPublishedIds = sensorPublishMapper.getAllPublishedIds();
        allPublishedIds.retainAll(sensorIds);
        sensorIds.removeAll(allPublishedIds);
        return sensorIds;
    }

    @Override
    public Result getCswMetaDataOnSos() {
        List<SchemaInfo> schemaInfoList = infoMapper.getSchemaInfo();
        long totalDataEntriesSize =  observationMapper.getTotalVolume();
        cswMetaDataOnSos.setEntryNumbers(observationMapper.getTotalDataEntries());
        cswMetaDataOnSos.setEntrySize(totalDataEntriesSize/(1024*1024) + "MB");
        cswMetaDataOnSos.setSchemaInfoList(schemaInfoList);
        return Result.OK(cswMetaDataOnSos);
    }

//    @Override
//    public SensorPublishedResponse getSensorInfoBySensorType(String sensorType) {
//        infoMapper.get
//        return null;
//    }
}
