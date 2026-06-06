package org.cug.geodt.weibo.sos.engine.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.cug.geodt.weibo.sos.engine.entity.QueryInfo;
import org.cug.geodt.weibo.sos.engine.entity.ReferValue;
import org.cug.geodt.weibo.sos.engine.entity.SensorQueryLambda;
import org.cug.geodt.weibo.sos.enums.FilterEnum;
import org.cug.geodt.weibo.sos.expression.filter.FilterOperation;
import org.cug.geodt.weibo.sos.expression.filter.Imp.*;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.LogicAndNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.LogicNotNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.LogicOrNode;
import org.cug.geodt.weibo.sos.expression.utils.ExprWrappedFunction;
import org.cug.geodt.weibo.sos.factory.entity.*;
import org.cug.geodt.weibo.sos.mapper.CapabilityMapper;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorData;
import org.cug.geodt.weibo.sos.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.engine
 * @Description
 * @date 2023/7/26 11:10
 */
@Component
public class QueryLambdaFilterUtils {

    @Resource
    CapabilityMapper capabilityMapper;

    @Value("#{'${fieldName.procedure}'}")
    String procedureFormat;

    private static QueryLambdaFilterUtils QueryLambdaFilterUtils;

    @PostConstruct
    public void init(){
        QueryLambdaFilterUtils = this;
        QueryLambdaFilterUtils.capabilityMapper = this.capabilityMapper;
        QueryLambdaFilterUtils.procedureFormat = this.procedureFormat;
    }

    static Map<String,Class<? extends FilterOperation>> stringToFilterOps;
    static {
        stringToFilterOps = new HashMap<>();
        stringToFilterOps.put(FilterEnum.EQUAL.getValue(), EqualFilterOperation.class);
        stringToFilterOps.put(FilterEnum.GTE.getValue(), GteFilterOperation.class);
        stringToFilterOps.put(FilterEnum.GT.getValue(), GtFilterOperation.class);
        stringToFilterOps.put(FilterEnum.LTE.getValue(), LteFilterOperation.class);
        stringToFilterOps.put(FilterEnum.LT.getValue(), LtFilterOperation.class);
        stringToFilterOps.put(FilterEnum.REGEXLIKE.getValue(), RegexLikeFilterOperation.class);
        stringToFilterOps.put(FilterEnum.IN.getValue(), InFilterOperation.class);
        stringToFilterOps.put(FilterEnum.RANGE.getValue(), RangeFilterOperation.class);
        stringToFilterOps.put(FilterEnum.WITHIN.getValue(), WithinFilterOperation.class);
        stringToFilterOps.put(FilterEnum.CONTAINS.getValue(), ContainsFilterOperation.class);
    }
    static public FilterOperation createFilterOperation(String operationName, Map<String,Object> parameters){
        try{
            return stringToFilterOps.get(operationName).getDeclaredConstructor(Map.class).newInstance(parameters);
        }catch (Exception e){
            return null;
        }
    }


    public static List<SensorQueryLambda> observationToLambdaQuery(SOSEntity sosEntity) {
        List<SensorQueryLambda> sensorQueryLambdas = new ArrayList<>();
        List<QueryInfo> queryInfos = generateQueryInfo(sosEntity);
        if (!queryInfos.isEmpty()) {
            for (QueryInfo queryInfo : queryInfos) {
                SensorQueryLambda sensorQueryLambda = new SensorQueryLambda();
                ExprNode observedPropertyNode;
                ExprNode temporalFilterNode = null;
                ExprNode geometryFilterNode = null;
                ExprNode allNode;
                String offering = queryInfo.getOffering();
                String procedure = queryInfo.getProcedure();
                String featureOfInterest = queryInfo.getFeatureOfInterest();
                List<String> sensorIds = QueryLambdaFilterUtils.capabilityMapper.
                        getSensorIdByOfferingIdentifierAndProcedureIdentifier(offering, procedure, featureOfInterest);
                if (sensorIds.isEmpty()){
                    List<String> sensorId = new ArrayList<>();
                    sensorId.add(procedure.replace(QueryLambdaFilterUtils.procedureFormat,""));
                    sensorQueryLambda.setSensorIds(sensorId);
                }else {
                    sensorQueryLambda.setSensorIds(sensorIds);
                }
                observedPropertyNode = handleObservedProperty(queryInfo.getObservedProperty());
                if (sosEntity.getBody().getTemporalFilter()!=null){
                    temporalFilterNode = handleTemporalFilterV2(sosEntity.getBody().getTemporalFilter());
                }
                if (sosEntity.getBody().getSpatialFilter()!=null){
                    geometryFilterNode = handleGeometryFilter(sosEntity.getBody().getSpatialFilter());
                }
                allNode = handleAllFilter(observedPropertyNode,temporalFilterNode,geometryFilterNode);
                ExprWrappedFunction<Object> all = new ExprWrappedFunction<>(allNode);
                sensorQueryLambda.setFunction(all);
                sensorQueryLambda.setTargetInfo("sensorData");
                sensorQueryLambdas.add(sensorQueryLambda);
            }
        }
        return sensorQueryLambdas;
    }

    private static ExprNode handleAllFilter(ExprNode observedPropertyNode, ExprNode temporalFilterNode, ExprNode geometryFilterNode) {
        ExprNode all;
        if (observedPropertyNode != null && temporalFilterNode != null && geometryFilterNode!=null){
            ExprNode propertyAndTime =  new LogicAndNode(observedPropertyNode,temporalFilterNode);
            all = new LogicAndNode(propertyAndTime,geometryFilterNode);
        }else if (observedPropertyNode!=null&&temporalFilterNode!=null){
            all = new LogicAndNode(observedPropertyNode,temporalFilterNode);
        }else if (observedPropertyNode!=null&&geometryFilterNode!=null){
            all = new LogicAndNode(observedPropertyNode,geometryFilterNode);
        }else if (temporalFilterNode!=null&&geometryFilterNode!=null){
            all  = new LogicAndNode(temporalFilterNode,geometryFilterNode);
        }else if (observedPropertyNode != null){
            all = observedPropertyNode;
        }else if (temporalFilterNode!=null){
            all = temporalFilterNode;
        }else {
            all = geometryFilterNode;
        }
        return all;
    }

    private static ExprNode handleGeometryFilter(SpatialFilter<?> spatialFilter) {
        String srid = "4326";
        String geometry = "SRID:"+srid+";"+spatialFilter.getGeometry().toString();
        List<ReferValue> referValues = new ArrayList<>();
        ReferValue referValue = new ReferValue();
        referValue.setType("geometry");
        referValue.setValue(geometry);
        referValues.add(referValue);
        Map<String,Object> map = putFilterParameters(referValues, "coordinate", SensorData.class);
        String op = StringUtils.firstToLowerCase(spatialFilter.getSpatialOps());
        return createFilterOperation(op,map);
    }


    private static ExprNode handleTemporalFilter(LogicTemporalFilter temporalFilter,ExprNode root,boolean first) {

        if (temporalFilter.getLogicTemporalFilter()==null&&temporalFilter.getTemporalFilters()==null){
            return root;
        }
        if (temporalFilter.getLogicOps()==null){
            TemporalFilter<?> filter = temporalFilter.getTemporalFilters().get(0);
            root = handleTemporal(filter);
        }else if (temporalFilter.getLogicOps().equals("And")){
            ExprNode exprNode;
            TemporalFilter<?> filter = temporalFilter.getTemporalFilters().get(0);
            exprNode = handleTemporal(filter);
            if (first){
                root = exprNode;
            }else {
                root = new LogicAndNode(root, exprNode);
            }
        }else if (temporalFilter.getLogicOps().equals("Not")){
            TemporalFilter<?> filter = temporalFilter.getTemporalFilters().get(0);
            ExprNode exprNode = handleTemporal(filter);
            if (first){
                root = new LogicNotNode(exprNode);
            }else {
                root = new LogicAndNode(new LogicNotNode(exprNode), root);
            }
        }else if (temporalFilter.getLogicOps().equals("Or")){
            TemporalFilter<?> filter = temporalFilter.getTemporalFilters().get(0);
            ExprNode exprNode = handleTemporal(filter);
            if (first){
                root = exprNode;
            }else {
                root = new LogicOrNode(root, exprNode);
            }
        }
        if (temporalFilter.getLogicTemporalFilter()!=null && temporalFilter.getLogicTemporalFilter().size()>0) {
            root = handleTemporalFilter(temporalFilter.getLogicTemporalFilter().get(0), root, false);
        }
        return root;
    }

    private static ExprNode handleTemporal(TemporalFilter<?> temporalFilter) {
        ExprNode root = null;
        String ops = temporalFilter.getTemporalOps();
        switch (ops) {
            case "TOverlaps":
            case "During":
                root = handleTOverlaps(temporalFilter);
                break;
            case "TEquals":
                root = handleTEquals(temporalFilter);
                break;
            case "After":
                root = handleAfter(temporalFilter);
                break;
            case "Before":
                root = handleBefore(temporalFilter);
                break;
            case "Begins":
            case "BegunBy":
                root = handleBegins(temporalFilter);
                break;
            case "Ends":
            case "EndedBy":
                root = handleEnds(temporalFilter);
                break;
        }
        return root;
    }

    private static ExprNode handleEnds(TemporalFilter<?> temporalFilter) {
        List<ReferValue> referValues = new ArrayList<>();
        TimeInstant timePeriod = (TimeInstant) temporalFilter.getTemporal();
        ReferValue referValue = new ReferValue();
        referValue.setType("string");
        referValue.setValue(DateUtils.Date2String(timePeriod.getPosition()));
        referValues.add(referValue);
        Map<String,Object> map = putFilterParameters(referValues, "obsTimestamp", SensorData.class);
        return createFilterOperation("lte",map);
    }

    private static ExprNode handleBegins(TemporalFilter<?> temporalFilter) {
        List<ReferValue> referValues = new ArrayList<>();
        TimeInstant timePeriod = (TimeInstant) temporalFilter.getTemporal();
        ReferValue referValue = new ReferValue();
        referValue.setType("string");
        referValue.setValue(DateUtils.Date2String(timePeriod.getPosition()));
        referValues.add(referValue);
        Map<String,Object> map = putFilterParameters(referValues, "obsTimestamp", SensorData.class);
        return createFilterOperation("gte",map);
    }


    private static ExprNode handleAfter(TemporalFilter<?> temporalFilter) {
        List<ReferValue> referValues = new ArrayList<>();
        TimeInstant timePeriod = (TimeInstant) temporalFilter.getTemporal();
        ReferValue referValue = new ReferValue();
        referValue.setType("string");
        referValue.setValue(DateUtils.Date2String(timePeriod.getPosition()));
        referValues.add(referValue);
        Map<String,Object> map = putFilterParameters(referValues, "obsTimestamp", SensorData.class);
        return createFilterOperation("gt",map);
    }
    private static ExprNode handleBefore(TemporalFilter<?> temporalFilter) {
        List<ReferValue> referValues = new ArrayList<>();
        TimeInstant timePeriod = (TimeInstant) temporalFilter.getTemporal();
        ReferValue referValue = new ReferValue();
        referValue.setType("string");
        referValue.setValue(DateUtils.Date2String(timePeriod.getPosition()));
        referValues.add(referValue);
        Map<String,Object> map = putFilterParameters(referValues, "obsTimestamp", SensorData.class);
        return createFilterOperation("lt",map);
    }

    private static ExprNode handleTEquals(TemporalFilter<?> temporalFilter) {
        List<ReferValue> referValues = new ArrayList<>();
        TimeInstant timePeriod = (TimeInstant) temporalFilter.getTemporal();
        ReferValue referValue = new ReferValue();
        referValue.setType("string");
        referValue.setValue(DateUtils.Date2String(timePeriod.getPosition()));
        referValues.add(referValue);
        Map<String,Object> map = putFilterParameters(referValues, "obsTimestamp", SensorData.class);
        return createFilterOperation("equal",map);
    }

    private static ExprNode handleTOverlaps(TemporalFilter<?> temporalFilter){
        List<ReferValue> referValues = new ArrayList<>();
        TimePeriod timePeriod = (TimePeriod) temporalFilter.getTemporal();
        TimeInstant end = timePeriod.getEnd();
        TimeInstant start = timePeriod.getBegin();
        ReferValue referValue = new ReferValue();
        referValue.setType("string");
        referValue.setValue(DateUtils.Date2String(start.getPosition()));
        referValues.add(referValue);
        referValue = new ReferValue();
        referValue.setType("string");
        referValue.setValue(DateUtils.Date2String(end.getPosition()));
        referValues.add(referValue);
        Map<String,Object> map = putFilterParameters(referValues, "obsTimestamp", SensorData.class);
        return  createFilterOperation("range",map);
    }
    private static ExprNode handleObservedProperty(List<String> observedProperty) {
        ExprNode root = null;
        if (observedProperty!=null && !observedProperty.isEmpty()){
            List<ReferValue> referValues = handleObservedPropertyReferValue(observedProperty.get(0));
            Map<String,Object> map = putFilterParameters(referValues, "metricName", SensorData.class);
            root = createFilterOperation("equal",map);
            if (observedProperty.size()>1){
                for (int i = 1;i<observedProperty.size();i++){
                    referValues = handleObservedPropertyReferValue(observedProperty.get(i));
                    map = putFilterParameters(referValues, "metricName", SensorData.class);
                    FilterOperation filterOperation = createFilterOperation("equal",map);
                    root = new LogicOrNode(root,filterOperation);
                }
            }
        }
        return root;
    }

    private static List<ReferValue> handleObservedPropertyReferValue(String s) {
        if (s!=null) {
            List<ReferValue> referValues = new ArrayList<>();
            ReferValue referValue = new ReferValue();
            referValue.setType("string");
            referValue.setValue(s);
            referValues.add(referValue);
            return referValues;
        }else {
            return null;
        }
    }


    public static Map<String,Object> putFilterParameters(List<ReferValue> referValues, String targetFieldName, Class clazz){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("referValues", referValues);
        parameters.put("targetFieldName", targetFieldName);
        parameters.put("class", clazz);
        return parameters;
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
                observedProperty = sosEntity.getBody().getObservedProperty().get(i)!=null? Arrays.asList(sosEntity.getBody().getObservedProperty().get(i).split(",")):null;
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

    private static ExprNode handleTemporalFilterV2(LogicTemporalFilter temporalFilter){
        ExprNode root;
        ExprNode temp = handleTemporalV2(temporalFilter.getTemporalFilters(),temporalFilter.getLogicOps());
        ExprNode logic = handleTemporalFilterV2(temporalFilter.getLogicTemporalFilter(),temporalFilter.getLogicOps(),temp);
        if (temp!=null&&logic!=null){
            if (temporalFilter.getLogicOps().equals("And")){
                root =  new LogicAndNode(temp,logic);
            }else{
                root =  new LogicOrNode(temp,logic);
            }
        }else if (temp!=null){
            root = temp;
        }else root = logic;
        return root;
    }

    private static ExprNode handleTemporalFilterV2(ArrayList<LogicTemporalFilter> logicTemporalFilter,String logicOps,ExprNode node) {
        if (logicTemporalFilter==null || logicTemporalFilter.size() == 0) {
            return null;
        }
        if (node!=null){
            LogicTemporalFilter logicFilter = logicTemporalFilter.get(0);
            TemporalFilter<?> temporalFilter = logicFilter.getTemporalFilters().get(0);
            String logicOp = logicFilter.getLogicOps();
            ExprNode exprNode = handleTemporal(temporalFilter);
            if (logicOp.equals("Not")) {
                exprNode = new LogicNotNode(exprNode);
            }
            if (logicOps.equals("And")) {
                return new LogicAndNode(node, exprNode);
            } else {
                return new LogicOrNode(node, exprNode);
            }
        }else {
            LogicTemporalFilter logicTemporalFilter1 = logicTemporalFilter.get(0);
            TemporalFilter<?> temporalFilter1 = logicTemporalFilter1.getTemporalFilters().get(0);
            String logicOp = logicTemporalFilter1.getLogicOps();
            ExprNode exprNode1 = handleTemporal(temporalFilter1);
            if (logicOp.equals("Not")){
                exprNode1 = new LogicNotNode(exprNode1);
            }
            if (logicOps.equals("And")){
                LogicTemporalFilter logicTemporalFilter2 = logicTemporalFilter.get(1);
                TemporalFilter<?> temporalFilter2 = logicTemporalFilter2.getTemporalFilters().get(0);
                String logic2 = logicTemporalFilter2.getLogicOps();
                ExprNode exprNode2 = handleTemporal(temporalFilter2);
                if (logic2.equals("Not")){
                    exprNode2 = new LogicNotNode(exprNode2);
                }
                return new LogicAndNode(exprNode1,exprNode2);
            }else if (logicOps.equals("Or")){
                LogicTemporalFilter logicTemporalFilter2 = logicTemporalFilter.get(1);
                TemporalFilter<?> temporalFilter2 = logicTemporalFilter2.getTemporalFilters().get(0);
                String logic2 = logicTemporalFilter2.getLogicOps();
                ExprNode exprNode2 = handleTemporal(temporalFilter2);
                if (logic2.equals("Not")){
                    exprNode2 = new LogicNotNode(exprNode2);
                }
                return new LogicOrNode(exprNode1,exprNode2);
            }else{
                return new LogicNotNode(exprNode1);
            }
        }
    }

    private static ExprNode handleTemporalV2(ArrayList<TemporalFilter<?>> temporalFilters,String logicOps) {
        if (temporalFilters!=null&&temporalFilters.size()>0){
            if (temporalFilters.size()==1){
                ExprNode exprNode = handleTemporal(temporalFilters.get(0));
                if (logicOps!=null&&logicOps.equals("Not")){
                    return new LogicNotNode(exprNode);
                }else {
                    return exprNode;
                }
            }else if (temporalFilters.size()==2){
                ExprNode exprNode1 = handleTemporal(temporalFilters.get(0));
                ExprNode exprNode2 = handleTemporal(temporalFilters.get(1));
                if (logicOps.equals("And")){
                    return new LogicAndNode(exprNode1,exprNode2);
                }else if (logicOps.equals("Or")){
                    return new LogicOrNode(exprNode1,exprNode2);
                }
            }
        }else {
            return null;
        }
        throw new RuntimeException();
    }
}
