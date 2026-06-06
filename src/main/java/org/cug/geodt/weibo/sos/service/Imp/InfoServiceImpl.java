package org.cug.geodt.weibo.sos.service.Imp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.codec.decode.SensorMLDecode;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.common.constants.SatelliteType;
import org.cug.geodt.weibo.sos.common.constants.UavType;
import org.cug.geodt.weibo.sos.config.SensorTypeConfig;
import org.cug.geodt.weibo.sos.domain.describeSensor.DescribeSensorResponseEntity;
import org.cug.geodt.weibo.sos.engine.QueryEngine;
import org.cug.geodt.weibo.sos.engine.entity.*;
import org.cug.geodt.weibo.sos.exception.BusinessException;
import org.cug.geodt.weibo.sos.exception.Code;
import org.cug.geodt.weibo.sos.expression.aggregator.node.AggregatorResult;
import org.cug.geodt.weibo.sos.mapper.*;
import org.cug.geodt.weibo.sos.pojo.json.Renders;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.pojo.sensor.info.*;
import org.cug.geodt.weibo.sos.service.InfoService;
import org.cug.geodt.weibo.sos.utils.*;
import org.cug.geodt.weibo.sos.vo.DataEntry;
import org.cug.geodt.weibo.sos.vo.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InfoServiceImpl implements InfoService {

    @Value("#{'${fieldName.info.sensor-type}'.split(',')}")
    private String[] sensorTypes;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private GroundStationFixSensorMapper groundStationFixSensorMapper;

    @Autowired
    private MonitorEquipmentMonitorEquipmentSensorMapper monitorEquipmentMonitorEquipmentSensorMapper;

    @Autowired
    private SatelliteImagePhotographicSensorMapper satelliteImagePhotographicSensorMapper;

    @Autowired
    private SatelliteImageRadarSensorMapper satelliteImageRadarSensorMapper;

    @Autowired
    private SatelliteImageScanningSensorMapper satelliteImageScanningSensorMapper;

    @Autowired
    private SatelliteNonImageSensorMapper satelliteNonImageSensorMapper;

    @Autowired
    private UavCameraSensorMapper uavCameraSensorMapper;

    @Autowired
    private UavFixSensorMapper uavFixSensorMapper;

    @Autowired
    private UavRadarSensorMapper uavRadarSensorMapper;

    @Autowired
    private UavScanSensorMapper uavScanSensorMapper;

    @Autowired
    private WeatherRadarWeatherRadarSensorMapper weatherRadarWeatherRadarSensorMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private SensorTypeConvertUtil sensorTypeConvertUtil;

    @Autowired
    private QueryEngine queryEngine;

    @Autowired
    private ReturnVO returnVO;

    /*
     * 传感器基本信息查询
     * */
    @Override
    public ReturnVO getAllSensorInfo() {
        List<String> needNames = readFieldName("fieldName.info.sensor");

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        Query query = new Query("sensorInfo",deriveDataSpec);
        List all = queryEngine.selectBySensorIdsAndQueryList(null,query,null,null);

        CodebookEncoder.EncodeDataset dataSet = CodebookEncoder.encodeSensorInfos(all, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataSet);
        return returnVO;
    }

    @Override
    public ReturnVO getSensorInfoById(List<String> sensorId) {
        idListException(sensorId, "sensor_id不正确");
        List<String> needNames = readFieldName("fieldName.info.sensor");
        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List all = queryEngine.selectBySensorIdsAndQueryList(sensorId,new Query("sensorInfo",deriveDataSpec),null,null);

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(all, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO r = new ReturnVO();
        r.setRenders(rendersList);
        r.setDataSet(dataset);
        return r;
    }

    @Override
    public ReturnVO getAllMetricsById(List<String> sensorId) {
        idListException(sensorId, "sensor_id不正确");
        List<SensorDataString> infos = new ArrayList<>();
        for (String s : sensorId) {
            List<SensorInfo> sensorInfos = infoMapper.getAllMetricsById(s);
            List<String> tables = LocationHandle.metricNameHandle(sensorInfos);
            for (String table : tables) {
                SensorDataString sensorDataString = new SensorDataString();
                sensorDataString.setSensorId(s);
                sensorDataString.setMetricName(table);
                infos.add(sensorDataString);
            }
        }

        List<String> needNames = readFieldName("fieldName.info.metric");
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(infos, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getMetricsByMetricName(String sensorId, String metricName) {
        idException(sensorId, "sensor_id不正确");

        List<String> sensors = new ArrayList<>();
        sensors.add(sensorId);
        List<Filter> filters = new ArrayList<>();
        List<ReferValue> referValues = new ArrayList<>();
        ReferValue referValue  = new ReferValue("string",metricName);
        referValues.add(referValue);
        Filter filter = new Filter("equal","metricName",referValues,false);
        filters.add(filter);

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,filters,null,null);
        List<SensorData> all = queryEngine.selectBySensorIdsAndQueryList(sensors,new Query("sensorData",deriveDataSpec),null,null);

        List<String> needNames = readFieldName("fieldName.info.metrics");
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(all, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getLatlonById(List<String> sensorId) {
        idListException(sensorId, "sensor_id不正确");
        List<String> needNames = readFieldName("fieldName.info.latlon");
        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorInfo> all = queryEngine.selectBySensorIdsAndQueryList(sensorId,new Query("sensorInfo",deriveDataSpec),null,null);


        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(all, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getAllSensorGroup() {
        List<String> needNames = readFieldName("fieldName.info.group");
        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorInfo> all = queryEngine.selectBySensorIdsAndQueryList(null,new Query("sensorInfo",deriveDataSpec),null,null);

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(all, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getSensorInfoBySensorType(String groupName) {
        groupException(groupName, "group_name不正确");
        List<String> sensor_typeList = new ArrayList<>();
        for (SensorInfo i : infoMapper.getAllSensorGroup()) {
            sensor_typeList.add(i.getSensorType());
        }
        if (groupName == null || !sensor_typeList.contains(groupName)) {
            throw new BusinessException("group_name不正确", Code.BUSINESS_ERR);
        }

        List<String> needNames = readFieldName("fieldName.info.group");
        List<Filter> filters = new ArrayList<>();
        List<ReferValue> referValues = new ArrayList<>();

        ReferValue referValue = new ReferValue("string",groupName);
        referValues.add(referValue);
        Filter filter = new Filter("equal","sensorType",referValues,false);
        filters.add(filter);

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,filters,null,null);
        List<SensorInfo> all = queryEngine.selectBySensorIdsAndQueryList(null,new Query("sensorInfo",deriveDataSpec),null,null);

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(all, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getOldestObsTimeById(List<String> sensorId) {
        idListException(sensorId, "sensor_id不正确");
        List<Aggregator> aggregators = new ArrayList<>();
        List<Agg> aggs = new ArrayList<>();

        Agg agg = new Agg("min","obsTimestamp","obsTimestampMin");
        aggs.add(agg);
        List<String> groupBy = new ArrayList<>();
        groupBy.add("sensorId");
        Aggregator aggregator = new Aggregator(groupBy,aggs);
        aggregators.add(aggregator);


        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,null,null,aggregators);
        Map<String,List<AggregatorResult>>  aggregate = queryEngine.aggregate(sensorId,new Query("sensorData",deriveDataSpec),null,null);

        List<SensorDataString> infos = new ArrayList<>();
        List<AggregatorResult> aggregatorResults = aggregate.get("obsTimestampMin");
        for (String s : sensorId) {
            SensorDataString dataString = new SensorDataString();
            dataString.setSensorId(s);
            List<AggregatorResult> target = aggregatorResults.stream().filter(aggregatorResult -> aggregatorResult.getGroupName().contains(s)).collect(Collectors.toList());
            dataString.setObsTimestamp(Long.parseLong(target.get(0).getValue().toString()));
            dataString.setObsTime(conversionTime(dataString.getObsTimestamp(), null));;
            infos.add(dataString);
        }

        List<String> needNames = readFieldName("fieldName.info.obs-time");

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(infos, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getLatestObsTimeById(List<String> sensorId) {
        idListException(sensorId, "sensor_id不正确");
        List<Agg> aggs = new ArrayList<>();
        Agg agg = new Agg("max","obsTimestamp","obsTimestampMax");
        aggs.add(agg);
        List<String> groupBy = new ArrayList<>();
        groupBy.add("sensorId");
        Aggregator aggregator = new Aggregator(groupBy,aggs);
        List<Aggregator> aggregators = new ArrayList<>();
        aggregators.add(aggregator);


        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,null,null,aggregators);
        Map<String,List<AggregatorResult>>  aggregate = queryEngine.aggregate(sensorId,new Query("sensorData",deriveDataSpec),null,null);


        List<SensorDataString> infos = new ArrayList<>();
        List<AggregatorResult> aggregatorResults = aggregate.get("obsTimestampMax");
        for (String s : sensorId) {
            SensorDataString dataString = new SensorDataString();
            dataString.setSensorId(s);
            List<AggregatorResult> target = aggregatorResults.stream().filter(aggregatorResult -> aggregatorResult.getGroupName().contains(s)).collect(Collectors.toList());
            dataString.setObsTimestamp(Long.parseLong(target.get(0).getValue().toString()));
            dataString.setObsTime(conversionTime(dataString.getObsTimestamp(), null));;
            infos.add(dataString);
        }

        List<String> needNames = readFieldName("fieldName.info.obs-time");

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(infos, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;

    }

    @Override
    public ReturnVO getLatestObsTimeStampByIdAndDate(List<String> sensorId, String date) {
        idListException(sensorId, "sensor_id不正确");
        List<Filter> filters = new ArrayList<>();

        List<ReferValue> referValues = new ArrayList<>();
        ReferValue referValue = new ReferValue("string",DateUtils.getStartOfDay10Format2String(date));
        referValues.add(referValue);
        Filter filter = new Filter("gte","obsTimestamp",referValues,false);
        filters.add(filter);

        referValues = new ArrayList<>();
        referValue = new ReferValue("string",DateUtils.getEndOfDay10Format2String(date).toString());
        referValues.add(referValue);
        filter = new Filter("lte","obsTimestamp",referValues,false);
        filters.add(filter);

        List<Aggregator> aggregators = new ArrayList<>();
        List<Agg> aggs = new ArrayList<>();
        Agg agg = new Agg("max","obsTimestamp","obsTimestampMax");
        aggs.add(agg);
        List<String> groupBy = new ArrayList<>();
        groupBy.add("sensorId");
        Aggregator aggregator = new Aggregator(groupBy,aggs);
        aggregators.add(aggregator);


        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,filters,null,aggregators);
        Map<String,List<AggregatorResult>>  aggregate = queryEngine.aggregate(sensorId,new Query("sensorData",deriveDataSpec),null,null);


        List<AggregatorResult> aggregatorResults = aggregate.get("obsTimestampMax");
        if (aggregatorResults.isEmpty()){
            throw new BusinessException("该日期下没有数据", Code.BUSINESS_ERR);
        }else {
            List<SensorDataString> infos = new ArrayList<>();
            for (String s : sensorId) {
                SensorDataString dataString = new SensorDataString();
                dataString.setSensorId(s);
                List<AggregatorResult> target = aggregatorResults.stream().filter(aggregatorResult -> aggregatorResult.getGroupName().contains(s)).collect(Collectors.toList());
                dataString.setObsTimestamp(Long.parseLong((target.get(0).getValue().toString())));
                dataString.setObsTime(conversionTime(dataString.getObsTimestamp(), null));;
                infos.add(dataString);
            }

            List<String> needNames = readFieldName("fieldName.info.obs-timestamp");

            CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(infos, needNames);
            List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                    "datav", true);

            ReturnVO returnVO = new ReturnVO();
            returnVO.setRenders(rendersList);
            returnVO.setDataSet(dataset);
            return returnVO;
        }
    }

    @Override
    public ReturnVO getOldestObsTimeStampByIdAndDate(List<String> sensorId, String date) {
        idListException(sensorId, "sensor_id不正确");
        List<Filter> filters = new ArrayList<>();

        List<ReferValue> referValues = new ArrayList<>();
        ReferValue referValue = new ReferValue("string",DateUtils.getStartOfDay10Format2String(date));
        referValues.add(referValue);
        Filter filter = new Filter("gte","obsTimestamp",referValues,false);
        filters.add(filter);

        referValues = new ArrayList<>();
        referValue = new ReferValue("string", DateUtils.getEndOfDay10Format2String(date));
        referValues.add(referValue);
        filter = new Filter("lte","obsTimestamp",referValues,false);
        filters.add(filter);

        List<Aggregator> aggregators = new ArrayList<>();

        List<Agg> aggs = new ArrayList<>();
        Agg agg = new Agg("min","obsTimestamp","obsTimestampMin");
        aggs.add(agg);
        List<String> groupBy = new ArrayList<>();
        groupBy.add("sensorId");
        Aggregator aggregator = new Aggregator(groupBy,aggs);
        aggregators.add(aggregator);

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,filters,null,aggregators);
        Map<String,List<AggregatorResult>>  aggregate = queryEngine.aggregate(sensorId,new Query("sensorData",deriveDataSpec),null,null);


        List<AggregatorResult> aggregatorResults = aggregate.get("obsTimestampMin");
        if (aggregatorResults.size()==0){
            throw new BusinessException("该日期下没有数据", Code.BUSINESS_ERR);
        }else {
            List<SensorDataString> infos = new ArrayList<>();
            for (String s : sensorId) {
                SensorDataString dataString = new SensorDataString();
                dataString.setSensorId(s);
                List<AggregatorResult> target = aggregatorResults.stream().filter(aggregatorResult -> aggregatorResult.getGroupName().contains(s)).collect(Collectors.toList());
                dataString.setObsTimestamp(Long.parseLong(target.get(0).getValue().toString()));
                dataString.setObsTime(conversionTime(dataString.getObsTimestamp(), null));;
                infos.add(dataString);
            }

            List<String> needNames = readFieldName("fieldName.info.obs-timestamp");

            CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(infos, needNames);
            List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                    "datav", true);

            ReturnVO returnVO = new ReturnVO();
            returnVO.setRenders(rendersList);
            returnVO.setDataSet(dataset);
            return returnVO;
        }
    }

    @Override
    public ReturnVO getSensorIdInSensorInfo() {
        List<String> needNames = readFieldName("fieldName.info.sensor-id");
        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorInfo>  all = queryEngine.selectBySensorIdsAndQueryList(null,new Query("sensorInfo",deriveDataSpec),null,null);

        CodebookEncoder.EncodeDataset dataSet = CodebookEncoder.encodeSensorInfos(all, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataSet);
        return returnVO;
    }

    @Override
    public ReturnVO getSensorNumBySensorType() {
        List<Aggregator> aggregators = new ArrayList<>();

        List<String> groupBy = new ArrayList<>();
        List<Agg> aggs = new ArrayList<>();
        Agg agg = new Agg("count","sensorType","sensorTypeNum");
        aggs.add(agg);
        groupBy.add("sensorType");
        Aggregator aggregator = new Aggregator(groupBy,aggs);
        aggregators.add(aggregator);

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,null,null,aggregators);
        Query query = new Query("sensorInfo",deriveDataSpec);
        Map<String, List<AggregatorResult>> map = queryEngine.aggregate(null,query,null,null);
        List<AggregatorResult> aggregatorResults =map.get("sensorTypeNum");
        List<SensorInfo> sensorInfos = new ArrayList<>();
        for (AggregatorResult result : aggregatorResults){
            SensorInfo sensorInfo = new SensorInfo();
            sensorInfo.setSensorType(result.getGroupName().get(0));
            sensorInfo.setNumber(Integer.parseInt(result.getValue().toString()));
            sensorInfos.add(sensorInfo);
        }
        List<String> needNames = readFieldName("fieldName.info.sensor-num");


        CodebookEncoder.EncodeDataset dataSet = CodebookEncoder.encodeSensorInfos(sensorInfos, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataSet);
        return returnVO;
    }

    @Override
    public ReturnVO getMetricsBySensorType(String groupName) {
        groupException(groupName, "group_name不正确");

        List<Filter> filters = new ArrayList<>();
        List<ReferValue> referValues = new ArrayList<>();
        ReferValue referValue = new ReferValue("string",groupName);
        referValues.add(referValue);
        Filter filter = new Filter("equal","sensorType",referValues,false);
        filters.add(filter);

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,filters,null,null);

        List<SensorInfo> sensorInfos = queryEngine.selectBySensorIdsAndQueryList(null,new Query("sensorInfo",deriveDataSpec),null,null);
        List<SensorDataString> info = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            List<String> tables = LocationHandle.metricNameHandleOne(sensorInfo);
            for (String table : tables) {
                SensorDataString sensorDataString = new SensorDataString();
                sensorDataString.setSensorType(groupName);
                sensorDataString.setSensorId(sensorInfo.getSensorId());
                sensorDataString.setMetricName(table);
                info.add(sensorDataString);
            }
        }

        List<String> needNames = readFieldName("fieldName.info.group-metric");
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;

    }

    @Override
    public String insertSensor(String describeSensor,String sensorType) throws XmlException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DescribeSensorResponseEntity describeSensorResponseEntity = SensorMLDecode.convert(describeSensor);
        String id = sensorTypeConvertUtil.InsertSpecificTable(sensorType, describeSensorResponseEntity);
        return id;
    }

    @Override
    public Object getBasicSensorInfoById(String sensorId) throws InvocationTargetException, IllegalAccessException {
        String sensorType = StringUtils.SensorIdParseToSensorType(sensorId);
        SensorInfo sensorInfo = infoMapper.getSensorInfoById(sensorId);
        switch (sensorType) {
            case  SensorTypeConfig.WWR:
                WeatherRadarWeatherRadarSensor weatherRadarWeatherRadarSensor = weatherRadarWeatherRadarSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(weatherRadarWeatherRadarSensor, sensorInfo);
                return weatherRadarWeatherRadarSensor;
            case SensorTypeConfig.SIP:
                SatelliteImagePhotographicSensor satelliteImagePhotographicSensor = satelliteImagePhotographicSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(satelliteImagePhotographicSensor, sensorInfo);
                return satelliteImagePhotographicSensor;
            case SensorTypeConfig.US:
                UavScanSensor uavScanSensor = uavScanSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(uavScanSensor, sensorInfo);
                return uavScanSensor;
            case SensorTypeConfig.UR:
                UavRadarSensor uavRadarSensor = uavRadarSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(uavRadarSensor, sensorInfo);
                return uavRadarSensor;
            case SensorTypeConfig.UF:
                UavFixSensor uavFixSensor = uavFixSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(uavFixSensor, sensorInfo);
                return uavFixSensor;
            case SensorTypeConfig.UC:
                UavCameraSensor uavCameraSensor = uavCameraSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(uavCameraSensor, sensorInfo);
                return uavCameraSensor;
            case SensorTypeConfig.SN:
//                SatelliteNonImageSensor satelliteNonImageSensor = satelliteNonImageSensorMapper.getSensorInfoById(sensorId);
//                BeanUtils.copyProperties(satelliteNonImageSensor, sensorInfo);
                return sensorInfo;
            case SensorTypeConfig.SIS:
                SatelliteImageScanningSensor satelliteImageScanningSensor = satelliteImageScanningSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(satelliteImageScanningSensor, sensorInfo);
                return satelliteImageScanningSensor;
            case SensorTypeConfig.SIR:
                SatelliteImageRadarSensor satelliteImageRadarSensor = satelliteImageRadarSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(satelliteImageRadarSensor, sensorInfo);
                return satelliteImageRadarSensor;
            case SensorTypeConfig.MEM:
                MonitorEquipmentMonitorEquipmentSensor monitorEquipmentMonitorEquipmentSensor = monitorEquipmentMonitorEquipmentSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(monitorEquipmentMonitorEquipmentSensor, sensorInfo);
                return monitorEquipmentMonitorEquipmentSensor;
            case SensorTypeConfig.GF:
                GroundStationFixSensor groundStationFixSensor = groundStationFixSensorMapper.getSensorInfoById(sensorId);
                BeanUtils.copyProperties(groundStationFixSensor, sensorInfo);
                return groundStationFixSensor;
        }
        return null;
    }

    @Override
    public int deleteSensorBySensorId(String sensorId) throws InvocationTargetException, IllegalAccessException {
        String sensorType = StringUtils.SensorIdParseToSensorType(sensorId);
        int i = infoMapper.deleteBySensorId(sensorId);
        switch (sensorType) {
            case  SensorTypeConfig.WWR:
                int result1 = weatherRadarWeatherRadarSensorMapper.deleteSensorById(sensorId);
                return  result1 & i;
            case SensorTypeConfig.SIP:
                int result2 = satelliteImagePhotographicSensorMapper.deleteSensorById(sensorId);
                return  result2 & i;
            case SensorTypeConfig.US:
                int result3 = uavScanSensorMapper.deleteSensorById(sensorId);
                return  result3 & i;
            case SensorTypeConfig.UR:
                int result4 = uavRadarSensorMapper.deleteSensorById(sensorId);
                return  result4 & i;
            case SensorTypeConfig.UF:
                int result5 = uavFixSensorMapper.deleteSensorById(sensorId);
                return  result5 & i;
            case SensorTypeConfig.UC:
                int result6 = uavCameraSensorMapper.deleteSensorById(sensorId);
                return  result6 & i;
            case SensorTypeConfig.SN:
                int result7 = satelliteNonImageSensorMapper.deleteSensorById(sensorId);
                return  result7 & i;
            case SensorTypeConfig.SIS:
                int result8 = satelliteImageScanningSensorMapper.deleteSensorById(sensorId);
                return  result8 & i;
            case SensorTypeConfig.SIR:
                int result9 = satelliteImageRadarSensorMapper.deleteSensorById(sensorId);
                return  result9 & i;
            case SensorTypeConfig.MEM:
                int result10 = monitorEquipmentMonitorEquipmentSensorMapper.deleteSensorById(sensorId);
                return  result10 & i;
            case SensorTypeConfig.GF:
                int result11 = groundStationFixSensorMapper.deleteSensorById(sensorId);
                return  result11 & i;
        }
        return 0;
    }

    @Override
    public int updateSensorById(String sensorType, String xml) throws XmlException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DescribeSensorResponseEntity sensorInfo =  SensorMLDecode.convert(xml);
        int i = sensorTypeConvertUtil.updateSpecificTable(sensorType, sensorInfo);
        return i;
    }

    @Override
    public SensorInfo getSensorBasicById(String sensorId) {
        SensorInfo sensorBasic = infoMapper.getSensorBasicById(sensorId);
        return sensorBasic;
    }

    @Override
    public Result getSatelliteStatisticalDataByType() {
        ArrayList<DataEntry> satelliteStatisticalDataByType = infoMapper.getSatelliteStatisticalDataByType();
        Long satelliteRecords = infoMapper.getSatelliteRecords();
        //用于丰富卫星类型

        List<String> satelliteTypes = satelliteStatisticalDataByType.stream().map(dataEntry -> {
            return dataEntry.getType();
        }).collect(Collectors.toList());

        if(!satelliteTypes.contains(SatelliteType.probeSatellite)) {
            DataEntry dataEntry = new DataEntry(SatelliteType.probeSatellite, 0);
            satelliteStatisticalDataByType.add(dataEntry);
        }

        if(!satelliteTypes.contains(SatelliteType.astronomicalObservationSatellite)) {
            DataEntry dataEntry = new DataEntry(SatelliteType.astronomicalObservationSatellite, 0);
            satelliteStatisticalDataByType.add(dataEntry);
        }

        if(!satelliteTypes.contains(SatelliteType.spaceTelescopeSatellite)) {
            DataEntry dataEntry = new DataEntry(SatelliteType.spaceTelescopeSatellite, 0);
            satelliteStatisticalDataByType.add(dataEntry);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("records",satelliteRecords);
        map.put("satelliteStatisticalDataByType",satelliteStatisticalDataByType);
        return Result.ok(map);

    }

    @Override
    public Result getUavStatisticalDataByType() {
        ArrayList<DataEntry> uavStatisticalDataByType = infoMapper.getUavStatisticalDataByType();
        List<String> uavTypes = uavStatisticalDataByType.stream().map(dataEntry -> {
            return dataEntry.getType();
        }).collect(Collectors.toList());

        if(!uavTypes.contains(UavType.compositeWing)) {
            DataEntry dataEntry = new DataEntry(UavType.compositeWing, 0);
            uavStatisticalDataByType.add(dataEntry);
        }
        Long uavRecords = infoMapper.getUavRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("records",uavRecords);
        map.put("uavStatisticalDataByType",uavStatisticalDataByType);
        return Result.ok(map);
    }

    @Override
    public Result getGroundStationStatisticalDataByType() {
        ArrayList<DataEntry> groundStationStatisticalDataByType = infoMapper.getGroundStationStatisticalDataByType();
        int monitorEquipment = infoMapper.getMonitorEquipment();
        DataEntry dataEntry = new DataEntry();
        dataEntry.setType("监控设备");
        dataEntry.setNumber(monitorEquipment);
        groundStationStatisticalDataByType.add(dataEntry);
        Long groundStationRecords = infoMapper.getGroundStationRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("records",groundStationRecords);
        map.put("groundStationStatisticalDataByType",groundStationStatisticalDataByType);
        return Result.ok(map);
    }

    @Override
    public Result getOceanStatisticalDataByType() {
        ArrayList<DataEntry> oceanStatisticalDataByType = infoMapper.getOceanStatisticalDataByType();
        Long oceanRecords = infoMapper.getOceanRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("records",oceanRecords);
        map.put("oceanStatisticalDataByType",oceanStatisticalDataByType);
        return Result.ok(map);
    }


    //传感器id异常返回
    public void idException(String sensor_id, String massage) {
        List<String> sensorIdList = new ArrayList<>();
        for (SensorInfo i : infoMapper.getSensor_idInSensorInfo()) {
            sensorIdList.add(i.getSensorId());
        }
        if (sensor_id == null || !sensorIdList.contains(sensor_id)) {
            throw new BusinessException(massage, Code.BUSINESS_ERR);
        }

    }

    //多个传感器id异常返回
    public void idListException(List<String> sensor_id, String massage) {
        List<String> sensorIdList = new ArrayList<>();
        for (SensorInfo i : infoMapper.getSensor_idInSensorInfo()) {
            sensorIdList.add(i.getSensorId());
        }
        if (sensor_id.isEmpty() || !new HashSet<>(sensorIdList).containsAll(sensor_id)) {
            throw new BusinessException(massage, Code.BUSINESS_ERR);
        }
    }

    //读取yml文件中的fieldName
    public List<String> readFieldName(String property) {
        String names = environment.getProperty(property);
        List<String> needNames = Arrays.asList(names.split(","));
        return needNames;
    }

    //metric_name异常返回
    public void metricException(String metrics) {
        List<String> metricList = new ArrayList<>();

    }

    //判断sensor_id的metrics字段对应的表,分别查询
    public List<SensorDataString> judgmentQuery(List<String> tables, String sensor_id) {
        List<SensorDataString> infos = new ArrayList<>();
        if (tables.contains("sensor_data_float") && !tables.contains("sensor_data_string")) {
            infos = infoMapper.getSensorInfoByIdInFloat(sensor_id);
        } else if (tables.contains("sensor_data_string") && !tables.contains("sensor_data_float")) {
            infos = infoMapper.getSensorInfoByIdInString(sensor_id);
        } else {
            List<SensorDataString> info_float = infoMapper.getSensorInfoByIdInFloat(sensor_id);
            infos.addAll(info_float);
            List<SensorDataString> info_string = infoMapper.getSensorInfoByIdInString(sensor_id);
            infos.addAll(info_string);
        }
        return infos;
    }

    //将时间戳转化为时间格式
    public String conversionTime(Long seconds, String format) {
//        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
//            return "";
//        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(seconds + "000")));
    }

    //将时间格式转化为时间戳
    public Integer convertToTimestamp(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long timestamp = cal.getTimeInMillis();
            return Math.toIntExact(timestamp / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void groupException(String sensorGroup, String massage) {
        List<SensorInfo> allSensorGroups = infoMapper.getAllSensorGroup();
        List<String> allSensorGroup = new ArrayList<>();
        for (SensorInfo sensorInfo : allSensorGroups) {
            allSensorGroup.add(sensorInfo.getSensorType());
        }
        if (sensorGroup == null || !allSensorGroup.contains(sensorGroup)) {
            throw new BusinessException(massage, Code.BUSINESS_ERR);
        }
    }


    public List<SensorDataString> matchData(List<SensorDataString> infos, String date) {
        Integer timestamp1 = convertToTimestamp(date);
        Integer timestamp2 = convertToTimestamp(date) + 86400;

        List<SensorDataString> infoList = new ArrayList<>();
        for (SensorDataString info : infos) {
            if (info.getObsTimestamp() <= timestamp2 && info.getObsTimestamp() >= timestamp1) {
                infoList.add(info);
            }
        }
        return infoList;
    }
}
