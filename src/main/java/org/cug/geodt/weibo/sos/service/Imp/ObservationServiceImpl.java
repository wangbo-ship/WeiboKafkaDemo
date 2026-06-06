package org.cug.geodt.weibo.sos.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.xmlbeans.XmlException;
import org.cug.geodt.weibo.sos.common.Result;
import org.cug.geodt.weibo.sos.common.proceduremanage.ProcedureManager;
import org.cug.geodt.weibo.sos.engine.QueryEngine;
import org.cug.geodt.weibo.sos.engine.entity.DeriveDataSpec;
import org.cug.geodt.weibo.sos.engine.entity.Query;
import org.cug.geodt.weibo.sos.exception.BusinessException;
import org.cug.geodt.weibo.sos.exception.Code;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.ObservationMapper;
import org.cug.geodt.weibo.sos.mapper.SpidersDataMapper;
import org.cug.geodt.weibo.sos.pojo.SpidersData;
import org.cug.geodt.weibo.sos.pojo.json.Renders;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorData;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataFloat;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.service.ObservationService;
import org.cug.geodt.weibo.sos.utils.CodebookEncoder;
import org.cug.geodt.weibo.sos.utils.RendersEncoder;
import org.cug.geodt.weibo.sos.utils.StringUtils;
import org.cug.geodt.weibo.sos.utils.parser.getObservationRequest.InsertObservationParser;
import org.cug.geodt.weibo.sos.vo.EntryByDay;
import org.cug.geodt.weibo.sos.vo.EntryByMonth;
import org.cug.geodt.weibo.sos.vo.EntryByYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.cug.geodt.weibo.sos.config.SpidersDataConfig.staticList;


@Service
public class ObservationServiceImpl implements ObservationService {

    @Autowired
    ProcedureManager procedureManager;
    @Autowired
    InsertObservationParser insertObservationParser;
    @Autowired
    private ObservationMapper observationMapper;
    @Autowired
    private Environment environment;
    @Resource
    private QueryEngine queryEngine;
    @Resource
    private InfoMapper infoMapper;

    @Autowired
    private Result result;

    @Autowired
    private SpidersDataMapper spidersDataMapper;
    /*
     * 2.传感器观测详情数据
     * */

    @Override
    public ReturnVO getLatestMetricValueById(List<String> sensorId, Float intervalInMin) {
        idListException(sensorId,"sensorId有误");

        LocalDateTime now = LocalDateTime.now();
        Long end = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;
        LocalDateTime dateStart =now.
                minusSeconds(Long.parseLong(String.valueOf((int)Math.ceil(intervalInMin*60))));
        Long start = dateStart.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;


        List<String> needNames = readFieldName("fieldName.observation.metric-value");

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> da = queryEngine.selectByConditionsAndQueryList(sensorId,start.intValue(),end.intValue(),null,new Query("sensorData",deriveDataSpec));


        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(da, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getLatestMetricValueByIdAndMetricName(List<String> sensorId, Float intervalInMin, String metricName) {
        idListException(sensorId, "sensor_id不正确");

        LocalDateTime now = LocalDateTime.now();
        Long end = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;
        LocalDateTime dateStart =now.
                minusSeconds(Long.parseLong(String.valueOf((int)Math.ceil(intervalInMin*60))));
        Long start = dateStart.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;


        List<String> needNames = readFieldName("fieldName.observation.metric-value");



        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> da = queryEngine.selectByConditionsAndQueryList(sensorId,start.intValue(),end.intValue(),metricName,new Query("sensorData",deriveDataSpec));

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(da, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getMetricValueByIdAndTimeRange(List<String> sensorId, Integer startTime, Integer endTime){
        idListException(sensorId, "sensor_id不正确");


        List<String> needNames = readFieldName("fieldName.observation.metric-value");

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> data = queryEngine.selectByConditionsAndQueryList(sensorId,startTime,endTime,null,new Query("sensorData",deriveDataSpec));


        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(data, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getMetricValueByIdAndTimeRangeAndMetricName(List<String> sensorId, Integer startTime, Integer endTime, String metricName) {
        idListException(sensorId, "sensor_id不正确");


        List<String> needNames = readFieldName("fieldName.observation.metric-value");

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> data  = queryEngine.selectByConditionsAndQueryList(sensorId,startTime,endTime,metricName,new Query("sensorData",deriveDataSpec));

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(data, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    /*
     * 3.传感器组观测详情数据
     *
     * */

    @Override
    public ReturnVO getMetricValueByTypeAndMetricName(String sensorType, List<String> sensorId, Float intervalInMin, String metricName) {
        LocalDateTime now = LocalDateTime.now();
        Long end = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;
        LocalDateTime dateStart =now.
                minusSeconds(Long.parseLong(String.valueOf((int)Math.ceil(intervalInMin*60))));
        Long start = dateStart.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;

        List<String> needNames = readFieldName("fieldName.observation.metric-value");

        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> data = queryEngine.selectByConditionsAndQueryList(sensorIds,start.intValue(),end.intValue(),metricName,new Query("sensorData",deriveDataSpec));

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(data, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getMetricValueByTypeAndTimeRange(String sensorType, List<String> sensorId,Integer startTime, Integer endTime) {


        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }
        List<String> needNames = readFieldName("fieldName.observation.metric-value");

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> data = queryEngine.selectByConditionsAndQueryList(sensorIds,startTime,endTime,null,new Query("sensorData",deriveDataSpec));

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(data, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getMetricValueByTypeAndTimeRangeAndMetricName(String sensorType, List<String> sensorId,Integer startTime, Integer endTime, String metricName) {

        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }

        List<String> needNames = readFieldName("fieldName.observation.metric-value");

        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> data = queryEngine.selectByConditionsAndQueryList(sensorIds,startTime,endTime,metricName,new Query("sensorData",deriveDataSpec));

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(data, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getMetricValueByType(String sensorType,List<String> sensorId, Float intervalInMin) {

        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }

        LocalDateTime now = LocalDateTime.now();
        Long end = now.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;
        LocalDateTime dateStart =now.
                minusSeconds(Long.parseLong(String.valueOf((int)Math.ceil(intervalInMin*60))));
        Long start = dateStart.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()/1000L;

        List<String> needNames = readFieldName("fieldName.observation.metric-value");
        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames,null,null,null);
        List<SensorData> data = queryEngine.selectByConditionsAndQueryList(sensorIds,start.intValue(),end.intValue(),null,new Query("sensorData",deriveDataSpec));

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(data, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line/bar/pie", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }


    public void idListException(List<String> sensor_id, String massage) {
        List<String> sensorIdList = new ArrayList<>();
        for (SensorInfo i : infoMapper.getSensor_idInSensorInfo()) {
            sensorIdList.add(i.getSensorId());
        }
        if (sensor_id.isEmpty() || !new HashSet<>(sensorIdList).containsAll(sensor_id)) {
            throw new BusinessException(massage, Code.BUSINESS_ERR);
        }
    }
    //传感器id异常返回
    public void idException(String sensor_id, String massage) {
        List<String> sensorIdList = new ArrayList<>();
        for (SensorDataFloat i : observationMapper.getSensorIdInFloat()) {
            sensorIdList.add(i.getSensorId());
        }
        for (SensorDataString j : observationMapper.getSensorIdInString()) {
            sensorIdList.add(j.getSensorId());
        }
        if (sensor_id == null || !sensorIdList.contains(sensor_id)) {
            throw new BusinessException(massage, Code.BUSINESS_ERR);
        }
    }

    //读取yml文件中的fieldName
    public List<String> readFieldName(String property) {
        String names = environment.getProperty(property);
        List<String> needNames = Arrays.asList(names.split(","));
        return needNames;
    }

    //判断sensor_id的metrics字段对应的表,按interval_in_min分别查询
    public List<SensorData> minQuery(List<String> tables, String sensor_id, Float interval_in_min) {
        List<SensorData> infos = new ArrayList<>();
        if (tables.contains("sensor_data_float") && !tables.contains("sensor_data_string")) {
            infos.addAll(observationMapper.getLatestMetricValueByIdInFloat(sensor_id, interval_in_min));
        } else if (tables.contains("sensor_data_string") && !tables.contains("sensor_data_float")) {
            infos.addAll(observationMapper.getLatestMetricValueByIdInString(sensor_id, interval_in_min));
        } else {
            List<SensorDataFloat> info_float = observationMapper.getLatestMetricValueByIdInFloat(sensor_id, interval_in_min);
            infos.addAll(info_float);
            List<SensorDataString> info_string = observationMapper.getLatestMetricValueByIdInString(sensor_id, interval_in_min);
            infos.addAll(info_string);
        }
        return infos;
    }

    //判断sensor_id的metrics字段对应的表,按start_time, end_time分别查询
    public List<SensorData> timeQuery(List<String> tables, String sensor_id, Integer start_time, Integer end_time) {
        List<SensorData> infos = new ArrayList<>();
        if (tables.contains("sensor_data_float") && !tables.contains("sensor_data_string")) {
            infos.addAll(observationMapper.getMetricValueByIdAndTimeRangInFloat(sensor_id, start_time, end_time));
        } else if (tables.contains("sensor_data_string") && !tables.contains("sensor_data_float")) {
            infos.addAll(observationMapper.getMetricValueByIdAndTimeRangInString(sensor_id, start_time, end_time));
        } else {
            List<SensorDataFloat> info_float = observationMapper.getMetricValueByIdAndTimeRangInFloat(sensor_id, start_time, end_time);
//            infos.addAll(info_float);
            List<SensorDataString> info_string = observationMapper.getMetricValueByIdAndTimeRangInString(sensor_id, start_time, end_time);
            infos.addAll(info_string);
        }
        return infos;
    }

    @Override
    public ReturnVO insertObservation(String observation) throws IOException, ParserConfigurationException, SAXException, XmlException, JAXBException {
        insertObservationParser.OMConvertToObject(observation);
//        procedureSegmentationStrategy.start();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        executor.submit(() -> {
            try {
                procedureManager.ScanProcedureMap();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return null;
    }

    @Override
    public Result getDataEntryByMonth(int num) {
        List<EntryByMonth> dataEntryByMonth = observationMapper.getDataEntryByMonth(num);
//        List<EntryByMonth> collect = dataEntryByMonth.stream().map(entryByMonth -> {
//            entryByMonth.setYear(StringUtils.catYear(entryByMonth.getYear()));
//            return entryByMonth;
//        }).collect(Collectors.toList());
        Collections.sort(dataEntryByMonth);
        return result.ok(dataEntryByMonth);
    }

    @Override
    public Result getDataEntryByYear(int num) {
        List<EntryByYear> dataEntryByYear = observationMapper.getDataEntryByYear(num);
//        List<EntryByYear> collect = dataEntryByYear.stream().map(entryByYear -> {
//            entryByYear.setYear(StringUtils.catYear(entryByYear.getYear()));
//            return entryByYear;
//        }).collect(Collectors.toList());
        Collections.sort(dataEntryByYear);
        return result.ok(dataEntryByYear);
    }

    @Override
    public Result getDataEntryByDay(int num) {
        List<EntryByDay> dataEntryByDay = observationMapper.getDataEntryByDay(num);
        List<EntryByDay> collect = dataEntryByDay.stream().map(day -> {
            day.setDay(StringUtils.catYear(day.getDay()));
            return day;
        }).collect(Collectors.toList());
        Collections.sort(collect);
        return result.ok(collect);
    }

    @Override
    public Result getSpidersData() {

        //map封装返回信息
        HashMap<String, Object> resultMap = new HashMap<>();
        // 获取当天0点0分0秒的 LocalDateTime 对象
        LocalDate currentDate = LocalDate.now();
        // 获取当天0点0分0秒的 LocalDateTime 对象
        LocalDateTime startOfDay = LocalDateTime.of(currentDate, LocalTime.MIN);
        // 转换为时间戳（秒）
        long startTime = startOfDay.toEpochSecond(ZoneOffset.ofHours(8));
        long endTime = startTime - 6*24*60*60;

        for (String name : staticList) {
            LambdaQueryWrapper<SpidersData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.select(SpidersData::getNumber,SpidersData::getObsTimeStamp).
                    between(SpidersData::getObsTimeStamp,endTime,startTime).eq(SpidersData::getName,name);
            List<SpidersData> spidersData = spidersDataMapper.selectList(lambdaQueryWrapper);
            resultMap.put(name,spidersData);
        }


        return Result.ok(resultMap);
    }

    @Override
    public Result getTodayVolume() {
        // 获取当天0点0分0秒的 LocalDateTime 对象
        LocalDate currentDate = LocalDate.now();
        // 获取当天0点0分0秒的 LocalDateTime 对象
        LocalDateTime startOfDay = LocalDateTime.of(currentDate, LocalTime.MIN);
        // 转换为时间戳（秒）
        long startTime = startOfDay.toEpochSecond(ZoneOffset.ofHours(8));
        long endTime = startTime +6*24*60*60;
        Long todayVolume = observationMapper.getTodayVolume(startTime, endTime);
        return todayVolume == null ? Result.ok(0 + "kb"): Result.ok(todayVolume/1024 + "kb");

    }

    @Override
    public Result getStatisticalDataOnAccessesByDayAndType() {
        return null;
    }

    @Override
    public Result getAllDataEntry() {
        return Result.ok(observationMapper.getTotalDataEntries());
    }

    @Override
    public Result getTotalVolume() {
        Long totalVolume = observationMapper.getTotalVolume();
        return totalVolume == null ? Result.ok(0 + "kb"): Result.ok(totalVolume + "kb");

    }

    @Override
    public Result getTodayEntry() {
        // 获取当天0点0分0秒的 LocalDateTime 对象
        LocalDate currentDate = LocalDate.now();
        // 获取当天0点0分0秒的 LocalDateTime 对象
        LocalDateTime startOfDay = LocalDateTime.of(currentDate, LocalTime.MIN);
        // 转换为时间戳（秒）
        long startTime = startOfDay.toEpochSecond(ZoneOffset.ofHours(8));
        long endTime = startTime +6*24*60*60;
        Long todayEntry = observationMapper.getTodayEntry(startTime,endTime);
        return todayEntry == null ? Result.ok(0): Result.ok(todayEntry);
    }


}
