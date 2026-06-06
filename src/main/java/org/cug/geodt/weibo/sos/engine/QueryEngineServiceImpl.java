package org.cug.geodt.weibo.sos.engine;

import lombok.extern.slf4j.Slf4j;
import net.hydromatic.linq4j.Enumerable;
import net.hydromatic.linq4j.Linq4j;
import net.hydromatic.linq4j.function.Function1;
import org.cug.geodt.weibo.sos.engine.entity.*;
import org.cug.geodt.weibo.sos.engine.mapper.QueryEngineMapper;
import org.cug.geodt.weibo.sos.exception.BusinessException;
import org.cug.geodt.weibo.sos.exception.Code;
import org.cug.geodt.weibo.sos.expression.aggregator.node.AggregatorResult;
import org.cug.geodt.weibo.sos.expression.aggregator.node.GroupByValue;
import org.cug.geodt.weibo.sos.expression.filter.FilterUtils;
import org.cug.geodt.weibo.sos.mapper.InfoMapper;
import org.cug.geodt.weibo.sos.pojo.SensorDerive;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorData;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataFloat;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorDataString;
import org.cug.geodt.weibo.sos.pojo.sensor.info.SensorInfo;
import org.cug.geodt.weibo.sos.utils.FunctionUtils;
import org.cug.geodt.weibo.sos.utils.LocationHandle;
import org.cug.geodt.weibo.sos.utils.PropertyAccessor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine
 * @Description
 * @date 2023/5/10 14:37
 */
@Service
@Slf4j
public class QueryEngineServiceImpl implements QueryEngine{
    @Resource
    QueryEngineMapper queryEngineMapper;
    @Resource
    InfoMapper infoMapper;

    private List queryBySensorIdAndTimeRangeAndBaseData(List<String> sensorIds,String baseDataIdentifier,Integer startTime, Integer endTime){
        List res;
        switch (baseDataIdentifier) {
            case "sensorData":
                return getObservationData(sensorIds);
            case "sensorInfo":
                return queryEngineMapper.getSensorInfoByIds(sensorIds);
            case "sensorDeriveQuarter":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndQuarter(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getObservationData(sensorIds);
                }
            case "sensorDeriveHour":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndHour(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getObservationData(sensorIds);
                }
            case "sensorDeriveDay":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndDay(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getObservationData(sensorIds);
                }
            case "sensorDeriveWeek":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndWeek(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getObservationData(sensorIds);
                }
            case "sensorDeriveMonth":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndMonth(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getObservationData(sensorIds);
                }
            case "sensorDeriveYear":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndYear(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getObservationData(sensorIds);
                }
            default:
                throw new RuntimeException();
        }
    }

    private Map<Class,List> queryAgg(List<String> sensorIds,String baseDataIdentifier,Integer startTime, Integer endTime){
        Map<Class,List> map = new HashMap<>();
        List res;
        switch (baseDataIdentifier) {
            case "sensorData":
                res = getObservationDataAgg(sensorIds);
                map.put(SensorData.class,res);
                return map;
            case "sensorInfo":
                res = queryEngineMapper.getSensorInfoByIds(sensorIds);
                map.put(SensorInfo.class,res);
                return map;
            case "sensorDeriveQuarter":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndQuarter(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getObservationDataAgg(sensorIds));
                }
                return map;
            case "sensorDeriveHour":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndHour(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getObservationDataAgg(sensorIds));
                }
                return map;
            case "sensorDeriveDay":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndDay(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getObservationDataAgg(sensorIds));
                }
                return map;
            case "sensorDeriveWeek":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndWeek(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getObservationDataAgg(sensorIds));
                }
                return map;
            case "sensorDeriveMonth":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndMonth(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getObservationDataAgg(sensorIds));
                }
                return map;
            case "sensorDeriveYear":
                res= queryEngineMapper.getSensorDeriveBySensorIdAndYear(sensorIds,startTime,endTime);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getObservationDataAgg(sensorIds));
                }
                return map;
            default:
                throw new RuntimeException();
        }
    }


    private List getObservationData(List<String> sensorIds){
        List<SensorInfo> sensorInfos = queryEngineMapper.getSensorInfoByIds(sensorIds);
        Set<String> tables = LocationHandle.locationHandle(sensorInfos);
        List data = new ArrayList<>();
        if (tables.contains("sensor_data_float") && !tables.contains("sensor_data_string")) {
            data = queryEngineMapper.getSensorDataFloatByIds(sensorIds);
        } else if (tables.contains("sensor_data_string") && !tables.contains("sensor_data_float")) {
            data = queryEngineMapper.getSensorDataStringByIds(sensorIds);
        } else {
            List<SensorDataString> data_string = queryEngineMapper.getSensorDataStringByIds(sensorIds);
            data.addAll(data_string);
            List<SensorDataFloat> data_float = queryEngineMapper.getSensorDataFloatByIds(sensorIds);
            data.addAll(data_float);
        }
        return data;
    }

    private Class getDataClass(String baseDataIdentifier){
        switch (baseDataIdentifier) {
            case "sensorData":
                return SensorData.class;
            case "sensorInfo":
                return SensorInfo.class;
            default:
                return SensorDerive.class;
        }
    }

    List filter(Class clazz, List<Filter> filter, List data){
        Function1 function1 = FilterUtils.generateFilterTree(clazz,filter);
        Enumerable enumerable = Linq4j.asEnumerable(data).where(FunctionUtils.toPredicate(function1));
        return enumerable.toList();
    }

    @Override
    public List selectBySensorIdsAndQueryList(List<String> sensorIds, Query query, Integer startTime, Integer endTime) {
        Class clazz = getDataClass(query.getBaseDataIdentifier());
        List data = queryBySensorIdAndTimeRangeAndBaseData(sensorIds,query.getBaseDataIdentifier(),startTime,endTime);
        if (confirmQueryFilter(query)){
            data = filter(clazz,query.getDeriveDataSpec().getFilters(),data);
        }
        if (data.isEmpty()){
            throw new BusinessException("查询结果为空", Code.BUSINESS_ERR);
        }
        return data;
    }

    @Override
    public List selectByConditionsAndQueryList(List<String> sensorIds, Integer startTime, Integer endTime, String metricName, Query query) {
        Class clazz = getDataClass(query.getBaseDataIdentifier());
        List data = queryByConditionsAndBaseData(sensorIds,startTime,endTime, metricName,query.getBaseDataIdentifier());
        if (confirmQueryFilter(query)){
            data = filter(clazz,query.getDeriveDataSpec().getFilters(),data);
        }
        if (data.isEmpty()){
            throw new BusinessException("查询结果为空", Code.BUSINESS_ERR);
        }
        return data;
    }

    private List queryByConditionsAndBaseData(List<String> sensorIds, Integer startTime, Integer endTime, String metricName, String baseDataIdentifier) {
        List res;
        switch (baseDataIdentifier) {
            case "sensorData":
                return getSensorDataByConditions(sensorIds,startTime,endTime,metricName);
            case "sensorInfo":
                return queryEngineMapper.getSensorInfoByIds(sensorIds);
            case "sensorDeriveQuarter":
                res= queryEngineMapper.getSensorDeriveByConditionsAndQuarter(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName);
                }
            case "sensorDeriveHour":
                res= queryEngineMapper.getSensorDeriveByConditionsAndHour(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName);
                }
            case "sensorDeriveDay":
                res= queryEngineMapper.getSensorDeriveByConditionsAndDay(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName);
                }
            case "sensorDeriveWeek":
                res= queryEngineMapper.getSensorDeriveByConditionsAndWeek(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName);
                }
            case "sensorDeriveMonth":
                res= queryEngineMapper.getSensorDeriveByConditionsAndMonth(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName);
                }
            case "sensorDeriveYear":
                res= queryEngineMapper.getSensorDeriveByConditionsAndYear(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    return res;
                }else {
                    return getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName);
                }
            default:
                throw new RuntimeException();
        }
    }

    private List getSensorDataByConditionsAgg(List<String> sensorIds, Integer startTime, Integer endTime, String metricName) {
        return queryEngineMapper.getSensorDataFloatByConditions(sensorIds, startTime, endTime, metricName);
    }

    private List getSensorDataByConditions(List<String> sensorIds, Integer startTime, Integer endTime, String metricName) {
        List<SensorInfo> sensorInfos = queryEngineMapper.getSensorInfoByIds(sensorIds);
        Set<String> tables = LocationHandle.locationHandle(sensorInfos);
        List data = new ArrayList<>();
        if (tables.contains("sensor_data_float") && !tables.contains("sensor_data_string")) {
            data = queryEngineMapper.getSensorDataFloatByConditions(sensorIds, startTime, endTime, metricName);
        } else if (tables.contains("sensor_data_string") && !tables.contains("sensor_data_float")) {
            data = queryEngineMapper.getSensorDataStringByConditions(sensorIds, startTime, endTime, metricName);
        } else {
            List<SensorDataString> data_string = queryEngineMapper.getSensorDataStringByConditions(sensorIds, startTime, endTime, metricName);
            data.addAll(data_string);
            List<SensorDataFloat> data_float = queryEngineMapper.getSensorDataFloatByConditions(sensorIds, startTime, endTime, metricName);
            data.addAll(data_float);
        }
        return data;
    }




    @Override
    public List selectBySensorIdsAndQueryList(SensorQuery sensorQuery) {
        Query query = sensorQuery.getQuery();
        List<String> sensorIds = sensorQuery.getSensorIds();
        Integer startTime = sensorQuery.getStartTime();
        Integer endTime = sensorQuery.getEndTime();
        Class clazz = getDataClass(query.getBaseDataIdentifier());
        List data = queryBySensorIdAndTimeRangeAndBaseData(sensorIds,query.getBaseDataIdentifier(),startTime,endTime);
        if (confirmQueryFilter(query)){
            data = filter(clazz,query.getDeriveDataSpec().getFilters(),data);
        }
        if (data.isEmpty()){
            return new ArrayList();
        }
        return data;
    }

    @Override
    public List selectBySensorIdsAndQueryList(SensorQueryLambda sensorQueryLambda) {
        List<String> sensorIds = sensorQueryLambda.getSensorIds();
        Function1 function1 = sensorQueryLambda.getFunction();
        List data = queryBySensorIdAndTimeRangeAndBaseData(sensorIds, sensorQueryLambda.getTargetInfo(), null,null);
        Enumerable enumerable = Linq4j.asEnumerable(data).where(FunctionUtils.toPredicate(function1));
        return enumerable.toList();
    }

    private List<SensorDataFloat> getObservationDataAgg(List<String> sensorIds){
        return queryEngineMapper.getSensorDataFloatByIds(sensorIds);
    }


    /*根据字符串取查不同的表格*/
    private List query(List<String> sensorIds,String baseDataIdentifier,Integer startTime, Integer endTime){
        switch (baseDataIdentifier) {
            case "sensorData":
                return getObservationData(sensorIds);
            case "sensorInfo":
                return queryEngineMapper.getSensorInfoByIds(sensorIds);
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Map<String,List<AggregatorResult>> aggregate(List<String> sensorIds,Query query,Integer startTime,Integer endTime) {
        Map<String,List<AggregatorResult>> resultMap = new HashMap<>();
        Class clazz = getDataClass(query.getBaseDataIdentifier());
//        List<String> keepNames = query.getDeriveDataSpec().getKeepFieldNames();
        List data = query(sensorIds,query.getBaseDataIdentifier(),startTime,endTime);

        if (confirmQueryFilter(query)){
            Function1 function1 = FilterUtils.generateFilterTree(clazz,query.getDeriveDataSpec().getFilters());
            Enumerable enumerable = Linq4j.asEnumerable(data).where(FunctionUtils.toPredicate(function1));
//            System.out.println(enumerable.toList());
            data = enumerable.toList();
        }
        if (confirmQueryAggregators(query)){
            data = filterNullDataBeforeAgg(clazz,data,query);
            //聚合之前删除需要聚合的空值
            for (Aggregator aggregator : query.getDeriveDataSpec().getAggregators()){
                for (Agg agg:aggregator.getAgg()){
                    GroupByValue groupByValue = new GroupByValue(aggregator.getGroupBy(),agg.getAggOp(),agg.getTargetFieldName(),clazz);
                    List<AggregatorResult> aggregatorResults = (List<AggregatorResult>) groupByValue.apply(Linq4j.asEnumerable(data));
                    resultMap.put(agg.getOutputFieldName(),aggregatorResults);
                }
            }
        }
        if (resultMap.isEmpty()){
            throw new BusinessException("查询结果为空", Code.BUSINESS_ERR);
        }
        return resultMap;
    }

    private List filterNullDataBeforeAgg(Class clazz, List data, Query query) {
        if (clazz.equals(SensorData.class)) return data;
        else {
            List<Aggregator> aggregators = query.getDeriveDataSpec().getAggregators();
            Set<String> groupByNames = new HashSet<>();
            for (Aggregator aggregator:aggregators){
                if (aggregator!=null&&!aggregator.getGroupBy().isEmpty()){
                    groupByNames.addAll(aggregator.getGroupBy());
                }
            }
            List<SensorInfo> sensorInfos = data;
            List<SensorInfo> target = new ArrayList();
            for (SensorInfo sensorInfo:sensorInfos){
                boolean flag = true;
                for (String s:groupByNames){
                    PropertyAccessor propertyAccessor = new PropertyAccessor(clazz,s);
                    Object value = propertyAccessor.apply(sensorInfo);
                    if (value==null){
                        flag = false;
                    }
                }
                if (flag){
                    target.add(sensorInfo);
                }

            }
            return target;
        }
    }

    @Override
    public List<SensorDerive> aggerateBySensorIdsAndTimeAndQueryList(List<String> sensorIds, Query query,Integer startTime,Integer endTime) {
        List<String> keepNames = query.getDeriveDataSpec().getKeepFieldNames();
        Map<Class,List> data = queryAgg(sensorIds,query.getBaseDataIdentifier(),startTime,endTime);
        if (data.get(SensorDerive.class)!=null){
            List result = data.get(SensorDerive.class);
            if (confirmQueryFilter(query)) {
                //去掉两个时间的过滤器
                List<Filter> filters = query.getDeriveDataSpec().getFilters().stream().filter(filter -> !filter.getTargetFieldName().equals("obsTimestamp")).collect(Collectors.toList());
                if (filters.size()>0){
                    Function1 function1 = FilterUtils.generateFilterTree(SensorDerive.class, filters);
                    Enumerable enumerable = Linq4j.asEnumerable(result).where(FunctionUtils.toPredicate(function1));
                    result = enumerable.toList();
                }
            }
            return result;
        }else if (data.get(SensorData.class)!=null){
            List result = data.get(SensorData.class);
            if (confirmQueryFilter(query)) {
                Function1 function1 = FilterUtils.generateFilterTree(SensorData.class, query.getDeriveDataSpec().getFilters());
                Enumerable enumerable = Linq4j.asEnumerable(result).where(FunctionUtils.toPredicate(function1));
                result = enumerable.toList();
            }
            if (confirmQueryAggregators(query)){
                List<AggregatorResult> aggregatorResults = new ArrayList<>();
                for (Aggregator aggregator : query.getDeriveDataSpec().getAggregators()){
                    for (Agg agg:aggregator.getAgg()){
                        GroupByValue groupByValue = new GroupByValue(aggregator.getGroupBy(),agg.getAggOp(),agg.getTargetFieldName(),SensorData.class);
                        aggregatorResults.addAll((List<AggregatorResult>)groupByValue.apply(Linq4j.asEnumerable(result)));
                    }
                }
                List<SensorDerive> sensorDerives = generateAggSensorDerives(aggregatorResults,startTime,endTime);
                return sensorDerives;
            }
        }
        return null;
    }

    private List<SensorDerive> generateAggSensorDerives(List<AggregatorResult> aggregatorResults,Integer startTime, Integer endTime) {
        if (aggregatorResults.isEmpty()){
            return null;
        }
        Set<String> groupName = new HashSet<>();
        for (AggregatorResult result:aggregatorResults){
            groupName.add(result.getGroupNameStr());
        }
        List<SensorDerive> result = new ArrayList<>();
        for (String s:groupName){
            SensorDerive sensorDerive = new SensorDerive();
            List<AggregatorResult> aggResult = aggregatorResults.stream().filter(aggregatorResult -> aggregatorResult.getGroupNameStr().equals(s)).collect(Collectors.toList());
            System.out.println(aggResult);
            if (aggResult.stream().anyMatch(obj -> obj.getOp().equals("min"))){
                AggregatorResult minR = aggResult.stream().filter(obj -> obj.getOp().equals("min")).findFirst().get();
                sensorDerive.setMinValue(Double.parseDouble(minR.getValue().toString()));
            }else {
                sensorDerive.setMinValue(Double.NaN);
            }

            if (aggResult.stream().anyMatch(obj -> obj.getOp().equals("max"))){
                AggregatorResult maxR = aggResult.stream().filter(obj -> obj.getOp().equals("max")).findFirst().get();
                sensorDerive.setMaxValue(Double.parseDouble(maxR.getValue().toString()));
            }else {
                sensorDerive.setMaxValue(Double.NaN);
            }

            if (aggResult.stream().anyMatch(obj -> obj.getOp().equals("average"))){
                AggregatorResult avgR = aggResult.stream().filter(obj -> obj.getOp().equals("average")).findFirst().get();
                sensorDerive.setAvgValue(Double.parseDouble(avgR.getValue().toString()));
            }else {
                sensorDerive.setAvgValue(Double.NaN);
            }

            AggregatorResult temp = aggResult.get(0);
            if (temp.getGroupAttr()!=null&& temp.getGroupAttr().contains("sensorId")){
                int sensorIdIndex = temp.getGroupAttr().indexOf("sensorId");
                sensorDerive.setSensorId(temp.getGroupName().get(sensorIdIndex));
            }else {
                sensorDerive.setSensorId("");
            }

            if (temp.getGroupAttr()!=null&& temp.getGroupAttr().contains("metricName")){
                int metricNameIndex = temp.getGroupAttr().indexOf("metricName");
                sensorDerive.setMetricName(temp.getGroupName().get(metricNameIndex));
            }else {
                sensorDerive.setMetricName("");
            }

            sensorDerive.setStartTime(startTime);
            sensorDerive.setEndTime(endTime);
            result.add(sensorDerive);
        }
        return result;
    }


    private Boolean confirmQueryFilter(Query query){
        return query.getDeriveDataSpec() != null && query.getDeriveDataSpec().getFilters() != null;
    }

    private Boolean confirmQueryAggregators(Query query){
        return query.getDeriveDataSpec() != null && query.getDeriveDataSpec().getAggregators() != null;
    }

    private Boolean confirmQueryTransformers(Query query){
        return query.getDeriveDataSpec() != null && query.getDeriveDataSpec().getTransformers()!= null;
    }


    //辅助查询

    @Override
    public List<SensorInfo> getSensorInfoBySensorType(String sensorType) {
        return queryEngineMapper.getSensorInfoBySensorType(sensorType);
    }

    @Override
    public List<SensorDerive> aggerateByConditionsAndQueryList(List<String> sensorIds, Integer startTime, Integer endTime, String metricName, Query query) {
        List<String> keepNames = query.getDeriveDataSpec().getKeepFieldNames();
        Map<Class,List> data = queryAggConditions(sensorIds,query.getBaseDataIdentifier(),startTime,endTime,metricName);
        if (data.get(SensorDerive.class)!=null){
            List result = data.get(SensorDerive.class);
            if (confirmQueryFilter(query)) {
                //去掉两个时间的过滤器
                List<Filter> filters = query.getDeriveDataSpec().getFilters().stream().filter(filter -> !filter.getTargetFieldName().equals("obsTimestamp")).collect(Collectors.toList());
                if (filters.size()>0){
                    Function1 function1 = FilterUtils.generateFilterTree(SensorDerive.class, filters);
                    Enumerable enumerable = Linq4j.asEnumerable(result).where(FunctionUtils.toPredicate(function1));
                    result = enumerable.toList();
                }
            }
            return result;
        }else if (data.get(SensorData.class)!=null){
            List result = data.get(SensorData.class);
            if (confirmQueryFilter(query)) {
                Function1 function1 = FilterUtils.generateFilterTree(SensorData.class, query.getDeriveDataSpec().getFilters());
                Enumerable enumerable = Linq4j.asEnumerable(result).where(FunctionUtils.toPredicate(function1));
                result = enumerable.toList();
            }
            if (confirmQueryAggregators(query)){

                List<AggregatorResult> aggregatorResults = new ArrayList<>();
                for (Aggregator aggregator : query.getDeriveDataSpec().getAggregators()){
                    for (Agg agg:aggregator.getAgg()){
                        GroupByValue groupByValue = new GroupByValue(aggregator.getGroupBy(),agg.getAggOp(),agg.getTargetFieldName(),SensorData.class);
                        aggregatorResults.addAll((List<AggregatorResult>)groupByValue.apply(Linq4j.asEnumerable(result)));
                    }
                }
                List<SensorDerive> sensorDerives = generateAggSensorDerives(aggregatorResults,startTime,endTime);
                return sensorDerives;
            }
        }
        return null;
    }

    private Map<Class, List> queryAggConditions(List<String> sensorIds, String baseDataIdentifier, Integer startTime, Integer endTime, String metricName) {
        Map<Class,List> map = new HashMap<>();
        List res;
        switch (baseDataIdentifier) {
            case "sensorData":
                res = getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName);
                map.put(SensorData.class,res);
                return map;
            case "sensorInfo":
                res = queryEngineMapper.getSensorInfoByIds(sensorIds);
                map.put(SensorInfo.class,res);
                return map;
            case "sensorDeriveQuarter":
                res= queryEngineMapper.getSensorDeriveByConditionsAndQuarter(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName));
                }
                return map;
            case "sensorDeriveHour":
                res= queryEngineMapper.getSensorDeriveByConditionsAndHour(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName));
                }
                return map;
            case "sensorDeriveDay":
                res= queryEngineMapper.getSensorDeriveByConditionsAndDay(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName));
                }
                return map;
            case "sensorDeriveWeek":
                res= queryEngineMapper.getSensorDeriveByConditionsAndWeek(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName));
                }
                return map;
            case "sensorDeriveMonth":
                res= queryEngineMapper.getSensorDeriveByConditionsAndMonth(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName));
                }
                return map;
            case "sensorDeriveYear":
                res= queryEngineMapper.getSensorDeriveByConditionsAndYear(sensorIds,startTime,endTime,metricName);
                if (!res.isEmpty()){
                    map.put(SensorDerive.class,res);
                }else {
                    map.put(SensorData.class,getSensorDataByConditionsAgg(sensorIds,startTime,endTime,metricName));
                }
                return map;
            default:
                throw new RuntimeException();
        }
    }
}
