package org.cug.geodt.weibo.sos.service.Imp;

import org.cug.geodt.weibo.sos.engine.QueryEngine;
import org.cug.geodt.weibo.sos.engine.entity.*;
import org.cug.geodt.weibo.sos.exception.BusinessException;
import org.cug.geodt.weibo.sos.exception.Code;
import org.cug.geodt.weibo.sos.mapper.AggMapper;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.mapper.ObservationMapper;
import org.cug.geodt.weibo.sos.pojo.SensorDerive;
import org.cug.geodt.weibo.sos.pojo.json.Renders;
import org.cug.geodt.weibo.sos.pojo.json.ReturnVO;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.service.AggService;
import org.cug.geodt.weibo.sos.utils.CodebookEncoder;
import org.cug.geodt.weibo.sos.utils.DateUtils;
import org.cug.geodt.weibo.sos.utils.RendersEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AggServiceImpl implements AggService {
    @Autowired
    private AggMapper aggMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private ObservationMapper observationMapper;


    @Autowired
    private QueryEngine queryEngine;


    @Override
    public ReturnVO getAggValueByIdAndMetricName(String aggName, String aggSpan, List<String> sensorIds, String metricName, Integer startTime, Integer endTime) {
        if (endTime < startTime) {
            throw new BusinessException("end_time大于start_time！", Code.BUSINESS_ERR);
        }
        String baseTable = "sensorDerive" + aggSpan.substring(0, 1).toUpperCase() + aggSpan.substring(1);
        List<String> needNames = judgmentNeedName(aggName);

        int aggTime = calculateAggTime(aggSpan);
        List<SensorDerive> info = new ArrayList<>();
        int end = endTime;
        int start = end - aggTime;
        int n = (int)(Math.floor((double) (endTime - startTime) / aggTime)) + 1;
        for (int i = 0;i<n;i++){
            if (startTime>start){
                start = startTime;
            }

//            int e = getEndTimeByAggSpan(aggSpan,end);
//            int s = getStartTimeByAggSapn(aggSpan,start,e);
            if (start==end) break;
//
//            System.out.println(DateUtils.longToDateString(s* 1000L));
//            System.out.println(DateUtils.longToDateString(e* 1000L));


            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", aggName + "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);

            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames, null, null, aggregators);
            List<SensorDerive> all = queryEngine.aggerateByConditionsAndQueryList(sensorIds,start,end,metricName,new Query(baseTable, deriveDataSpec));
            if (all!=null&&!all.isEmpty()){
                info.addAll(all);
            }
            end = start;
            start = end - aggTime;
        }

        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;

    }

    @Override
    public ReturnVO getAggValueById(String aggName, String aggSpan, List<String> sensorId, Integer startTime, Integer endTime) {
        if (endTime < startTime) {
            throw new BusinessException("end_time大于start_time！", Code.BUSINESS_ERR);
        }

        List<String> needNames = judgmentNeedName(aggName);
        String baseTable = "sensorDerive" + aggSpan.substring(0, 1).toUpperCase() + aggSpan.substring(1);
        int aggTime = calculateAggTime(aggSpan);
        List<SensorDerive> info = new ArrayList<>();
        int end = endTime;
        int start = end - aggTime;
        int n = (int)(Math.floor((double) (endTime - startTime) / aggTime)) + 1;
        for (int i = 0;i<n;i++) {
            if (startTime > start) {
                start = startTime;
            }

//            int e = getEndTimeByAggSpan(aggSpan,end);
//            int s = getStartTimeByAggSapn(aggSpan,start,e);
            if (start==end) break;

            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", aggName + "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);

            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames, null, null, aggregators);
            List<SensorDerive> all = queryEngine.aggerateByConditionsAndQueryList(sensorId, start,end,null,new Query(baseTable, deriveDataSpec));
            if (all!=null&&!all.isEmpty()){
                info.addAll(all);
            }
            end = start;
            start = end - aggTime;
        }
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;

    }



    @Override
    public ReturnVO getAggValueBySensorType(String aggName, String aggSpan, String sensorType, List<String> sensorId,Integer startTime, Integer endTime) {
        groupException(sensorType, "group_name不正确");

        List<String> needNames = judgmentNeedName(aggName);
        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }
        if (sensorIds.isEmpty()){
            throw new BusinessException("sensorId不正确", Code.BUSINESS_ERR);
        }
        String baseTable = "sensorDerive" + aggSpan.substring(0, 1).toUpperCase() + aggSpan.substring(1);

        int aggSpanTime = calculateAggTime(aggSpan);
        List<SensorDerive> info = new ArrayList<>();
        int end = endTime;
        int start = end - aggSpanTime;
        int n = (int)(Math.floor((double) (endTime - startTime) / aggSpanTime)) + 1;

        for (int i = 0;i<n;i++) {
            if (startTime > start) {
                start = startTime;
            }
//            int e = getEndTimeByAggSpan(aggSpan,end);
//            int s = getStartTimeByAggSapn(aggSpan,start,e);
            if (start==end) break;
//            System.out.println(DateUtils.longToDateString(s* 1000L));
//            System.out.println(DateUtils.longToDateString(e* 1000L));

            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);


            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames, null, null, aggregators);
            List<SensorDerive> all = queryEngine.aggerateByConditionsAndQueryList(sensorIds,start,end,null, new Query(baseTable, deriveDataSpec));
            if (all!=null&&!all.isEmpty()){
                info.addAll(all);
            }
            end = start;
            start = end - aggSpanTime;
        }
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;

    }


    private int getStartTimeByAggSapn(String aggSpan, int start, int e) {
        if (aggSpan.equals("hour")||aggSpan.equals("quarter")){
            return start;
        }else if (aggSpan.equals("day")){
            long time = DateUtils.getStartOfDayLong2Long(e * 1000L)/1000;
            return (int) time;
        }else if (aggSpan.equals("week")){
            return start;
        }else if (aggSpan.equals("month")){
            long time = DateUtils.getStartOfMonthLong2Long(e * 1000L)/1000;
            return (int)time;
        }else if (aggSpan.equals("year")){
            long time = DateUtils.getStartOfYearLong2Long(e * 1000L)/1000;
            return (int)time;
        }else {
            return start;
        }
    }

    private int getEndTimeByAggSpan(String aggSpan, int end) {
        if (aggSpan.equals("hour")||aggSpan.equals("quarter")){
            return end;
        }else if (aggSpan.equals("day")){
            long time = DateUtils.getEndOfDayLong2Long(end * 1000L)/1000;
            return (int)time;
        }else if (aggSpan.equals("week")){
            return end;
        }else if (aggSpan.equals("month")){
            long time = DateUtils.getEndOfMonthLong2Long(end * 1000L)/1000;
            return (int)time;
        }else if (aggSpan.equals("year")){
            long time = DateUtils.getEndOfYearLong2Long(end * 1000L)/1000;
            return (int)time;
        }else {
            return end;
        }

    }

    @Override
    public ReturnVO getAggValueByGroupAndMetricName(String aggName, String aggSpan, String sensorType, List<String> sensorId,String metricName, Integer startTime, Integer endTime) {
        groupException(sensorType, "传感器类型不正确");

        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }
        if (sensorIds.isEmpty()){
            throw new BusinessException("sensorId不正确", Code.BUSINESS_ERR);
        }
        List<String> needNames = judgmentNeedName(aggName);
        String baseTable = "sensorDerive" + aggSpan.substring(0, 1).toUpperCase() + aggSpan.substring(1);

        int aggTime = calculateAggTime(aggSpan);
        List<SensorDerive> info = new ArrayList<>();
        int end = endTime;
        int start = end - aggTime;
        int n = (int)(Math.floor((double) (endTime - startTime) / aggTime)) + 1;

        for (int i = 0;i<n;i++) {
            if (startTime > start) {
                start = startTime;
            }

//            int e = getEndTimeByAggSpan(aggSpan,end);
//            int s = getStartTimeByAggSapn(aggSpan,start,e);
            if (start==end) break;

            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);

            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(needNames, null, null, aggregators);
            List<SensorDerive> all = queryEngine.aggerateByConditionsAndQueryList(sensorIds, start,end,metricName,new Query(baseTable, deriveDataSpec));
            if (all!=null&&!all.isEmpty()){
                info.addAll(all);
            }
            end = start;
            start = end - aggTime;
        }
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    //根据传感器id获取指定分钟内的指定时间间隔的聚合数据
    //把一段时间的数据查出来
    //按照时间间隔进行分割
    //循环聚合
    @Override
    public ReturnVO getLatestAggValueByMins(String aggName, Float spanMin, List<String> sensorId, Float intervalInMins) {


        List<SensorDerive> info = new ArrayList<>();
        Long endTime = System.currentTimeMillis();
        int end = (int) (endTime/1000L);
        int start = end - (int)(spanMin * 60);
        int n = (int) (intervalInMins / spanMin);

        for (int i = 0; i < n; i++) {

            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);


            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null, null, null, aggregators);
            List<SensorDerive> sensorDerive = queryEngine.aggerateByConditionsAndQueryList(sensorId,start,end,null, new Query("sensorData", deriveDataSpec));
            if (sensorDerive!=null){
                info.addAll(sensorDerive);
            }
            end = start;
            start = end - (int)(spanMin * 60);
        }

        List<String> needNames = judgmentNeedName(aggName);
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    @Override
    public ReturnVO getLatestAggValueByMetricNameAndMins(String aggName, Float spanMin, List<String> sensorId, String metricName, Float intervalInMins) throws NoSuchMethodException {

        List<SensorDerive> info = new ArrayList<>();
        Long endTime = System.currentTimeMillis();
        int end = (int) (endTime/1000L);
        int start = end - (int)(spanMin * 60);
        int n = (int) (intervalInMins / spanMin);

        for (int i = 0; i < n; i++) {

            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);

            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null, null, null, aggregators);
            List<SensorDerive> sensorDerive = queryEngine.aggerateByConditionsAndQueryList(sensorId, start,end,metricName,new Query("sensorData", deriveDataSpec));
            if (sensorDerive!=null){
                info.addAll(sensorDerive);
            }
            end = start;
            start = end - (int)(spanMin * 60);
        }
        List<String> needNames = judgmentNeedName(aggName);
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;

    }

    @Override
    public ReturnVO getAggValueBySensorTypeAndMetricNameAndMins(String aggName, Float spanMin, String sensorType, List<String> sensorId,String metricName, Float intervalInMins) {

        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }
        if (sensorIds.isEmpty()){
            throw new BusinessException("sensorId不正确", Code.BUSINESS_ERR);
        }

        List<SensorDerive> info = new ArrayList<>();
        Long endTime = System.currentTimeMillis();
        int end = (int) (endTime/1000L);
        int start = end - (int)(spanMin * 60);
        int n = (int) (intervalInMins / spanMin);

        for (int i = 0; i < n; i++) {


            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);

            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null, null, null, aggregators);
            List<SensorDerive> sensorDerive = queryEngine.aggerateByConditionsAndQueryList(sensorIds, start,end,metricName,new Query("sensorData", deriveDataSpec));
            if (sensorDerive!=null){
                info.addAll(sensorDerive);
            }
            end = start;
            start = end - (int)(spanMin * 60);
        }

        List<String> needNames = judgmentNeedName(aggName);
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;


    }

    @Override
    public ReturnVO getLatestAggValueBySensorTypeAndMins(String aggName, Float spanMin, String sensorType,List<String> sensorId, Float intervalInMins) throws NoSuchMethodException {


        List<SensorInfo> sensorInfos = queryEngine.getSensorInfoBySensorType(sensorType);
        List<String> sensorIds  = new ArrayList<>();
        for (SensorInfo sensorInfo:sensorInfos){
            if (sensorId.contains(sensorInfo.getSensorId())){
                sensorIds.add(sensorInfo.getSensorId());
            }
        }
        if (sensorIds.isEmpty()){
            throw new BusinessException("sensorId不正确", Code.BUSINESS_ERR);
        }

        List<SensorDerive> info = new ArrayList<>();
        Long endTime = System.currentTimeMillis();
        int end = (int) (endTime/1000L);
        int start = end - (int)(spanMin * 60);
        int n = (int) (intervalInMins / spanMin);

        for (int i = 0; i < n; i++) {

            List<Aggregator> aggregators = new ArrayList<>();
            Aggregator aggregator = new Aggregator();
            List<Agg> aggs = new ArrayList<>();
            aggs.add(new Agg(aggName, "metricValue", "Value"));
            aggregator.setAgg(aggs);
            List<String> groupBy = new ArrayList<>();
            groupBy.add("sensorId");
            groupBy.add("metricName");
            aggregator.setGroupBy(groupBy);
            aggregators.add(aggregator);

            DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null, null, null, aggregators);
            List<SensorDerive> sensorDerive = queryEngine.aggerateByConditionsAndQueryList(sensorIds,start,end,null, new Query("sensorData", deriveDataSpec));
            if (sensorDerive!=null){
                info.addAll(sensorDerive);
            }
            end = start;
            start = end - (int)(spanMin * 60);
        }

        List<String> needNames = judgmentNeedName(aggName);
        CodebookEncoder.EncodeDataset dataset = CodebookEncoder.encodeSensorInfos(info, needNames);
        List<Renders> rendersList = RendersEncoder.rendersDataset("line", "time",
                "datav", true);

        ReturnVO returnVO = new ReturnVO();
        returnVO.setRenders(rendersList);
        returnVO.setDataSet(dataset);
        return returnVO;
    }

    //判断查询
    public List<SensorDerive> judgmentQueryById(String aggSpan, String sensorId, Integer startTime, Integer endTime) {
        //排序加去重
        List<Integer> handStartTime = getStartEndTime(aggSpan, sensorId, "start");
        List<Integer> handEndTime = getStartEndTime(aggSpan, sensorId, "end");
        if (handStartTime.isEmpty() || handEndTime.isEmpty()) {
            return null;
        }

//        List<Integer> obsTimeStamp = obsTimeStampById(sensorId);
        List<SensorDerive> infos = new ArrayList<>();
        if (handStartTime.contains(startTime) && handEndTime.contains(endTime)) {
            infos.addAll(queryOnTheHour(aggSpan, sensorId, startTime, endTime));
        } else {
            try {
                Integer min = Collections.min(handStartTime.stream().filter(s -> s >= startTime).collect(Collectors.toList()));
                Integer max = Collections.max(handEndTime.stream().filter(s -> s <= endTime).collect(Collectors.toList()));
                infos.addAll(aggMapper.getAllValueFloatToDerive(sensorId, startTime, min));
                infos.addAll(queryOnTheHour(aggSpan, sensorId, min, max));
                infos.addAll(aggMapper.getAllValueFloatToDerive(sensorId, max, endTime));
            } catch (NoSuchElementException e) {
                throw new BusinessException("时间不正确", Code.BUSINESS_ERR);
            }
        }
        return infos;

//        Integer a = Collections.min(handStartTime);
//        Integer b = Collections.max(handEndTime);
//        Integer c = Collections.min(obsTimeStamp);
//        Integer d = Collections.max(obsTimeStamp);
//
//        if (endTime < c || startTime > d) {
//            throw new BusinessException("end_time太早了或start_time太晚了", Code.BUSINESS_ERR);
//        }
//
//        List<SensorDerive> infos = new ArrayList<>();
//        if (b <= d) {
//            if (startTime <= b && startTime >= a && endTime <= b) {
//                if (endTime - startTime < 3600) {
//                    List<SensorDerive> info = aggMapper.getAllValueFloatToDerive(sensorId, startTime, endTime);
//                    infos.addAll(info);
//                } else {
//                    Integer startMinDiffValue = ObsTimeStampHandler.getStartMinDiffValue(startTime, handStartTime);
//                    Integer endMinDiffValue = ObsTimeStampHandler.getEndMinDiffValue(endTime, handEndTime);
//                    List<SensorDerive> midInfo = queryOnTheHour(aggSpan, sensorId, startMinDiffValue, endMinDiffValue);
//                    infos.addAll(midInfo);
//                    if (startMinDiffValue - startTime > 0) {
//                        List<SensorDerive> leftInfo = aggMapper.getAllValueFloatToDerive(sensorId, startTime,
//                                startMinDiffValue - 1);
//                        infos.addAll(leftInfo);
//                    }
//                    if (endTime - endMinDiffValue > 0) {
//                        List<SensorDerive> rightInfo = aggMapper.getAllValueFloatToDerive(sensorId, startTime + 1,
//                                endMinDiffValue);
//                        infos.addAll(rightInfo);
//                    }
//                }
//            } else if (startTime <= b && startTime >= a && endTime <= d) {
//                if (b - startTime < 3600) {
//                    List<SensorDerive> info = aggMapper.getAllValueFloatToDerive(sensorId, startTime, endTime);
//                    infos.addAll(info);
//                } else {
//                    Integer startMinDiffValue = ObsTimeStampHandler.getStartMinDiffValue(startTime, handStartTime);
//                    List<SensorDerive> midInfo = queryOnTheHour(aggSpan, sensorId, startMinDiffValue, b);
//                    infos.addAll(midInfo);
//                    if (startMinDiffValue - startTime > 0) {
//                        List<SensorDerive> leftInfo = aggMapper.getAllValueFloatToDerive(sensorId, startTime,
//                                startMinDiffValue - 1);
//                        infos.addAll(leftInfo);
//                    }
//                    List<SensorDerive> rightInfo = aggMapper.getAllValueFloatToDerive(sensorId, b, endTime);
//                    infos.addAll(rightInfo);
//                }
//            } else if (startTime <= b && startTime >= a) {
//                Integer startMinDiffValue = ObsTimeStampHandler.getStartMinDiffValue(startTime, handStartTime);
//                List<SensorDerive> midInfo = queryOnTheHour(aggSpan, sensorId, startMinDiffValue, b);
//                infos.addAll(midInfo);
//                if (startMinDiffValue - startTime > 0) {
//                    List<SensorDerive> leftInfo = aggMapper.getAllValueFloatToDerive(sensorId, startTime,
//                            startMinDiffValue - 1);
//                    infos.addAll(leftInfo);
//                }
//                List<SensorDerive> rightInfo = aggMapper.getAllValueFloatToDerive(sensorId, b, d);
//                infos.addAll(rightInfo);
//            } else if (startTime >= b && endTime <= d) {
//                List<SensorDerive> info = aggMapper.getAllValueFloatToDerive(sensorId, startTime, endTime);
//                infos.addAll(info);
//            } else if (startTime >= b) {
//                List<SensorDerive> info = aggMapper.getAllValueFloatToDerive(sensorId, startTime, d);
//                infos.addAll(info);
//            }
//            return infos;
//        }

    }

    //读取yml文件中的fieldName
    public List<String> readFieldName(String property) {
        String names = environment.getProperty(property);
        List<String> needNames = Arrays.asList(names.split(","));
        return needNames;
    }

    //id异常返回
    public void idListException(List<String> sensorId, String massage) {
        List<String> sensorIdList = new ArrayList<>();
        for (SensorDerive i : aggMapper.getSensorIdInDerive1()) {
            sensorIdList.add(i.getSensorId());
        }
//        if (sensorId.isEmpty() || !sensorIdList.containsAll(sensorId)) {
//            throw new BusinessException(massage, Code.BUSINESS_ERR);
//        }
    }

    //sensor_type异常返回
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

    //metricName异常返回
    public void metricNameException(String metricName, String massage) {
        List<String> metricNameList = new ArrayList<>();
        for (SensorDerive i : aggMapper.getMetricNameInDerive1()) {
            metricNameList.add(i.getMetricName());
        }
//        if (metricName == null || !metricNameList.contains(metricName)) {
//            throw new BusinessException(massage, Code.BUSINESS_ERR);
//        }
    }

    //interval_in_mins异常返回
    public void intervalInMinsException(String sensorId, Float startTime) throws NoSuchMethodException {
//        List<Integer> obsTimeStamp = obsTimeStampById(sensorId);
//        try {
//            if (startTime > Collections.max(obsTimeStamp)) {
//                throw new BusinessException("间隔时间太小，查询结果为null", Code.BUSINESS_ERR);
//            } else if (startTime < Collections.min(obsTimeStamp)) {
//                throw new BusinessException("间隔时间太大，查询结果为null", Code.BUSINESS_ERR);
//            }
//        }catch (NoSuchElementException e){
//            log.warn("此"+ sensorId +"没有观测时间戳");
//        }

    }

    //按agg_name定义返回的needNames字段
    public List<String> judgmentNeedNames(String aggName) {
        if (Objects.equals(aggName, "maxValue")) {
            return readFieldName("fieldName.observation.max-value");
        } else if (Objects.equals(aggName, "minValue")) {
            return readFieldName("fieldName.observation.min-value");
        } else if (Objects.equals(aggName, "avgValue")) {
            return readFieldName("fieldName.observation.avg-value");
        } else throw new BusinessException("agg_name不符合规范", Code.BUSINESS_ERR);
    }


    public List<String> judgmentNeedName(String aggName) {
        if (Objects.equals(aggName, "max")) {
            return readFieldName("fieldName.observation.max-value");
        } else if (Objects.equals(aggName, "min")) {
            return readFieldName("fieldName.observation.min-value");
        } else if (Objects.equals(aggName, "avg")) {
            return readFieldName("fieldName.observation.avg-value");
        } else throw new BusinessException("agg_name不符合规范", Code.BUSINESS_ERR);
    }


    //判断查询start_time和end_time中整时整点的数据
    public List<SensorDerive> queryOnTheHour(String aggSpan, String sensorId, Integer startTime, Integer endTime) {
        List<Integer> handStartTime = getStartEndTime(aggSpan, sensorId, "start");
        List<Integer> handEndTime = getStartEndTime(aggSpan, sensorId, "end");

        List<SensorDerive> info = getBySpan(aggSpan, sensorId, startTime, endTime);
        List<SensorDerive> infos = new ArrayList<>();

        int startIdx = handStartTime.indexOf(startTime);
        while (info.isEmpty()) {
            int endIdx = handEndTime.indexOf(endTime);
            while (info.isEmpty()) {
                info = getBySpan(aggSpan, sensorId, handStartTime.get(startIdx),
                        handEndTime.get(endIdx));
                endIdx = endIdx - 1;
            }

            startIdx = handStartTime.indexOf(handEndTime.get(endIdx + 1));
            infos.addAll(info);
            int endIdx1 = handEndTime.indexOf(endTime);
            info = getBySpan(aggSpan, sensorId, handStartTime.get(startIdx),
                    handEndTime.get(endIdx1));
        }
        if (!info.isEmpty()) {
            infos.addAll(info);
        }
        return infos;
    }

    //获取指定id所有观测时间戳 排序加去重
    public List<Long> obsTimeStampById(String sensorId) {
        List<Long> obsTimeStamp = new ArrayList<>();
        List<SensorDataString> infos = aggMapper.getObsTimeStamp(sensorId);
        for (SensorDataString info : infos) {
            obsTimeStamp.add(info.getObsTimestamp());
        }
        return obsTimeStamp.stream().distinct().collect(Collectors.toList());
    }

    //判断时间跨度进行分表查询
    public List<SensorDerive> getBySpan(String aggSpan, String sensorId, Integer startTime, Integer endTime) {
        List<SensorDerive> allValue = new ArrayList<>();
        switch (aggSpan) {
            case "quarter":
                allValue.addAll(aggMapper.getAllByValueMeeTimeQ(sensorId, startTime, endTime));
                break;
            case "hour":
                allValue.addAll(aggMapper.getAllByValueMeeTimeH(sensorId, startTime, endTime));
                break;
            case "day":
                allValue.addAll(aggMapper.getAllByValueMeeTimeD(sensorId, startTime, endTime));
                break;
            case "week":
                allValue.addAll(aggMapper.getAllByValueMeeTimeW(sensorId, startTime, endTime));
                break;
            case "month":
                allValue.addAll(aggMapper.getAllByValueMeeTimeM(sensorId, startTime, endTime));
                break;
            case "year":
                allValue.addAll(aggMapper.getAllByValueMeeTimeY(sensorId, startTime, endTime));
                break;
            default:
                throw new BusinessException("agg_span不正确", Code.BUSINESS_ERR);
        }
        return allValue;
    }


    public List<Integer> getStartEndTime(String aggSpan, String sensorId, String type) {
        List<SensorDerive> allTime = new ArrayList<>();
        switch (aggSpan) {
            case "quarter":
                allTime.addAll(aggMapper.getTimeQ(sensorId));
                break;
            case "hour":
                allTime.addAll(aggMapper.getTimeH(sensorId));
                break;
            case "day":
                allTime.addAll(aggMapper.getTimeD(sensorId));
                break;
            case "week":
                allTime.addAll(aggMapper.getTimeW(sensorId));
                break;
            case "month":
                allTime.addAll(aggMapper.getTimeM(sensorId));
                break;
            case "year":
                allTime.addAll(aggMapper.getTimeY(sensorId));
                break;
            default:
                throw new BusinessException("agg_span不正确", Code.BUSINESS_ERR);
        }
        if (Objects.equals(type, "start")) {
            return allTime.stream().
                    map(SensorDerive::getStartTime).
                    distinct().sorted().
                    collect(Collectors.toList());
        } else if (Objects.equals(type, "end")) {
            return allTime.stream().
                    map(SensorDerive::getEndTime).
                    distinct().sorted().
                    collect(Collectors.toList());
        }
        return null;
    }

    //处理span_min和interval_in_mins
    public List<SensorDerive> subLatestBySpanMin(String sensorId, Integer startTime, Float spanMin,
                                                 Integer currentTime) {
        int endTime = (int) (startTime + spanMin * 60);
        List<SensorDerive> derive = aggMapper.getAllValueFloatToDerive(sensorId, startTime, endTime);
        List<SensorDerive> derives = new ArrayList<>();
        while (endTime <= currentTime) {
            derives.addAll(derive);
            startTime = endTime;
            endTime = (int) (endTime + spanMin * 60);
            derive = aggMapper.getAllValueFloatToDerive(sensorId, startTime, endTime);
        }

        List<SensorDerive> Derive = aggMapper.getAllValueFloatToDerive(sensorId, startTime, currentTime);
        derives.addAll(Derive);
        return derives;
    }

    private Integer calculateAggTime(String aggSpan) {
        switch (aggSpan){
            case "quarter" : return 900;
            case "hour" : return 3600;
            case "day": return 86400;
            case "week": return 604800;
            case "month": return 2592000;
            case "year": return 31536000;
            default:throw new RuntimeException();
        }
    }

}



