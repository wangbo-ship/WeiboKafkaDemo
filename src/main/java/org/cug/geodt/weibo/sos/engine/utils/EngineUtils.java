package org.cug.geodt.weibo.sos.engine.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.cug.geodt.weibo.sos.engine.entity.*;
import org.cug.geodt.weibo.sos.factory.entity.*;
import org.cug.geodt.weibo.sos.mapper.CapabilityMapper;
import org.cug.geodt.weibo.sos.pojo.MetricsInfo;
import org.cug.geodt.weibo.sos.utils.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;


/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine
 * @Description
 * @date 2023/5/10 15:00
 */
@Component
public class EngineUtils {

    static Map<String,String> stringToFilterOps;

    static {
        stringToFilterOps = new HashMap<>();
    }

    @Resource
    CapabilityMapper capabilityMapper;

    private static EngineUtils engineUtils;

    @PostConstruct
    public void init(){
        engineUtils = this;
        engineUtils.capabilityMapper = this.capabilityMapper;
    }

    public static String conbineListString(String... strings){
        return StringUtils.join(strings,"%^");
    }

    public static List<MetricsInfo> handleMetricsJson(String metrics){
        List<MetricsInfo> metricsInfos = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(metrics);
        for (Object j : jsonArray) {
            JSONObject jsonObject = (JSONObject) j;
            MetricsInfo metricsInfo = new MetricsInfo();
            metricsInfo.setMetricName(jsonObject.getString("metricName"));
            metricsInfo.setMetricType(jsonObject.getString("metricType"));
            metricsInfo.setLocation(jsonObject.getString("location"));
            metricsInfos.add(metricsInfo);
        }
        return metricsInfos;
    }


    public static Query describeSensorToQuery(DescribeSensor describeSensor) {
        Query query = new Query();
        query.setBaseDataIdentifier("sensorInfo");
        DeriveDataSpec deriveDataSpec = new DeriveDataSpec(null,null,null,null);
        query.setDeriveDataSpec(deriveDataSpec);
        return query;
    }

    public static List<SensorQuery> observationToQuery(SOSEntity sosEntity){
        List<SensorQuery> sensorQueries = new ArrayList<>();
        List<QueryInfo> queryInfos = generateQueryInfo(sosEntity);
        if (queryInfos.size()>0) {
            for (QueryInfo queryInfo : queryInfos) {
                SensorQuery sensorQuery = new SensorQuery();
                String offering = queryInfo.getOffering();
                String procedure = queryInfo.getProcedure();
                String featureOfInterest = queryInfo.getFeatureOfInterest();
                List<String> sensorIds = engineUtils.capabilityMapper.getSensorIdByOfferingIdentifierAndProcedureIdentifier(offering, procedure, featureOfInterest);
                sensorQuery.setSensorIds(sensorIds);
                Query query = new Query();
                query.setBaseDataIdentifier("sensorData");
                DeriveDataSpec deriveDataSpec = new DeriveDataSpec();
                List<Filter> filters = new ArrayList<>();
                List<Filter> metricFilters = handleMetricFilters(queryInfo,sosEntity);
                filters.addAll(metricFilters);
                List<Filter> temporalFilter = handleTemporalFilter(sosEntity);
                filters.addAll(temporalFilter);
                //空间处理

                deriveDataSpec.setFilters(filters);
                query.setDeriveDataSpec(deriveDataSpec);
                sensorQuery.setQuery(query);
                sensorQueries.add(sensorQuery);
            }
        }else {
            List<Filter> filters = new ArrayList<>();
            SensorQuery sensorQuery = new SensorQuery();
            sensorQuery.setSensorIds(null);
            Query query = new Query();
            query.setBaseDataIdentifier("sensorData");
            DeriveDataSpec deriveDataSpec = new DeriveDataSpec();
            List<Filter> temporalFilter = handleTemporalFilter(sosEntity);
            filters.addAll(temporalFilter);
            deriveDataSpec.setFilters(filters);
            query.setDeriveDataSpec(deriveDataSpec);
            sensorQuery.setQuery(query);
            sensorQueries.add(sensorQuery);
        }
        return sensorQueries;
    }

    private static List<Filter> handleMetricFilters(QueryInfo queryInfo,SOSEntity sosEntity) {
        List<String> metricNames = queryInfo.getObservedProperty();
        List<Filter> filters = null;
        if (metricNames != null && metricNames.size() > 0) {
            filters = new ArrayList<>();
            Filter filter = new Filter();
            filter.setNotLogic(false);
            filter.setFilterOp("equal");
            List<ReferValue> referValues = new ArrayList<>();
            for (String metricName : metricNames) {
                ReferValue referValue = new ReferValue();
                referValue.setType("string");
                referValue.setValue(metricName);
                referValues.add(referValue);
            }
            filter.setTargetFieldName("metricName");
            filter.setReferValues(referValues);
            filters.add(filter);
        }
        return filters;
    }

    private static List<Filter> handleTemporalFilter(SOSEntity sosEntity) {
        List<Filter> filters = null;
        if (sosEntity.getBody().getTemporalFilter() != null) {
            filters = new ArrayList<>();
            TemporalFilter temporalFilter = sosEntity.getBody().getTemporalFilter().getTemporalFilters().get(0);
            if (temporalFilter.getTemporalOps().equals("TOverlaps")) {
                TimePeriod timePeriod = (TimePeriod) temporalFilter.getTemporal();
                TimeInstant end = timePeriod.getEnd();
                TimeInstant start = timePeriod.getBegin();
                Filter filter = new Filter();
                filter.setFilterOp("range");
                filter.setTargetFieldName("obsTimestamp");
                List<ReferValue> referValues = new ArrayList<>();
                ReferValue referValue = new ReferValue();
                referValue.setType("string");
                referValue.setValue(DateUtils.Date2String(start.getPosition()));
                referValues.add(referValue);
                referValue = new ReferValue();
                referValue.setType("string");
                referValue.setValue(DateUtils.Date2String(end.getPosition()));
                referValues.add(referValue);
                filter.setReferValues(referValues);
                filter.setNotLogic(false);
                filters.add(filter);
            }
        }
        return filters;
    }

    private static List<QueryInfo> generateQueryInfo(SOSEntity sosEntity) {
        List<QueryInfo> queryInfos = new ArrayList<>();
        Body body = sosEntity.getBody();
        int count = calculateSize(body);
        for (int i = 0;i<count;i++){
            QueryInfo queryInfo = new QueryInfo();
            String offering = null;
            String procedure = null;
            String featureOfInterest = null;
            List<String> observedProperty = null;
            if (body.getOffering()!=null&&body.getOffering().size()>0){
                offering = body.getOffering().get(i)!=null?body.getOffering().get(i):null;
            }
            if (body.getProcedure()!=null&&body.getProcedure().size()>0){
                procedure = sosEntity.getBody().getProcedure().get(i)!=null?sosEntity.getBody().getProcedure().get(i):null;
            }
            if (body.getFeatureOfInterest()!=null&&body.getFeatureOfInterest().size()>0){
                featureOfInterest = sosEntity.getBody().getFeatureOfInterest().get(i)!=null?sosEntity.getBody().getFeatureOfInterest().get(i):null;
            }
            if (body.getObservedProperty()!=null&&body.getObservedProperty().size()>0){
                observedProperty = sosEntity.getBody().getObservedProperty().get(i)!=null?Arrays.asList(sosEntity.getBody().getObservedProperty().get(i).split(",")):null;
            }
            queryInfo.setOffering(offering);
            queryInfo.setObservedProperty(observedProperty);
            queryInfo.setProcedure(procedure);
            queryInfo.setFeatureOfInterest(featureOfInterest);
            queryInfos.add(queryInfo);
        }
        return queryInfos;
    }

    private static int calculateSize(Body body) {
        List<Integer> count = new ArrayList<>();
        int numOfOffering = 0;
        int numOfProcedure = 0;
        int numOfFeature = 0;
        int numOfObservedProperty = 0;
        if (body.getObservedProperty()!=null&&body.getObservedProperty().size()>0){
            numOfObservedProperty = body.getObservedProperty().size();
        }
        count.add(numOfObservedProperty);
        if (body.getOffering()!=null&&body.getOffering().size()>0){
            numOfOffering = body.getOffering().size();
        }
        count.add(numOfOffering);
        if (body.getFeatureOfInterest()!=null&&body.getFeatureOfInterest().size()>0){
            numOfFeature = body.getFeatureOfInterest().size();
        }
        count.add(numOfFeature);
        if (body.getProcedure()!=null&&body.getProcedure().size()>0){
            numOfProcedure = body.getProcedure().size();
        }
        count.add(numOfProcedure);
        return Collections.max(count);
    }
}
