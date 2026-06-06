package org.cug.geodt.weibo.sos.service.Imp;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.sos.domain.describeSensor.TimePeriodEntity;
import org.cug.geodt.weibo.sos.domain.observation.*;
import org.cug.geodt.weibo.sos.engine.QueryEngine;
import org.cug.geodt.weibo.sos.engine.entity.SensorQueryLambda;
import org.cug.geodt.weibo.sos.engine.utils.QueryLambdaFilterUtils;
import org.cug.geodt.weibo.sos.factory.GetObservationFactory;
import org.cug.geodt.weibo.sos.factory.SensorObservationServiceFactory;
import org.cug.geodt.weibo.sos.factory.entity.SOSEntity;
import org.cug.geodt.weibo.sos.pojo.sensor.data.SensorData;
import org.cug.geodt.weibo.sos.service.GetObservationService;
import org.cug.geodt.weibo.sos.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.service.Imp
 * @Description
 * @date 2023/6/26 16:31
 */
@Slf4j
@Service
public class GetObservationServiceImpl implements GetObservationService{
    @Resource
    QueryEngine queryEngine;

    @Resource
    SensorObservationServiceFactory sensorObservationServiceFactory;
    @Override
    public String getObservationService(String str) {
        try {
            SOSEntity getObservation = GetObservationFactory.parse(str);
            List<SensorQueryLambda> sensorQueryLambdas = QueryLambdaFilterUtils.observationToLambdaQuery(getObservation);
            List<List<SensorData>> dataList = new ArrayList<>();
            for (SensorQueryLambda sensorQueryLambda:sensorQueryLambdas){
                List list = queryEngine.selectBySensorIdsAndQueryList(sensorQueryLambda);
                if (list!=null&&!list.isEmpty()) dataList.add(list);
            }
            if (!confirmDataNotEmpty(dataList)){
//                throw new BusinessException("查询为空", Code.BUSINESS_ERR);
                return "数据为空";
            }
            ObservationResponseEntity responseEntity = generateObservationResponseEntity(dataList,getObservation);
            return sensorObservationServiceFactory.getObservationDocument("2.0", responseEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean confirmDataNotEmpty(List<List<SensorData>> dataList){
        for (List<SensorData> sensorData : dataList) {
            if (sensorData != null && !sensorData.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private ObservationResponseEntity generateObservationResponseEntity(List<List<SensorData>> dataList,SOSEntity sosEntity) {
        ObservationResponseEntity responseEntity = new ObservationResponseEntity();
        List<ObservationDataEntity> observationDataEntityList = new ArrayList<>();
        for (int i = 0;i<dataList.size();i++){
//            SensorQuery sensorQuery = sensorQueries.get(i);
            List<SensorData> data = dataList.get(i);
            ObservationDataEntity observationDataEntity = new ObservationDataEntity();
            observationDataEntity.setNameEntity(generateObservationDataName(sosEntity));
            observationDataEntity.setPhenomenonTimeEntity(generatePhenomenonTimeEntity(data));
            observationDataEntity.setProcedure(generateObservationDataProcedure(sosEntity));
            observationDataEntity.setFeatureOfInterestEntity(generateObservationDataFeature(sosEntity));
            List<String> metricNames = data.stream().map(SensorData::getMetricName).distinct().collect(Collectors.toList());
            observationDataEntity.setObservedProperty(metricNames.stream().map(String::valueOf).collect(Collectors.joining(",")));
            observationDataEntity.setResultEntity(generateObservationResultEntityV1(data,metricNames));
            observationDataEntityList.add(observationDataEntity);
        }

        responseEntity.setObservationDataEntityList(observationDataEntityList);

        return responseEntity;
    }

    private ResultEntity generateObservationResultEntityV1(List<SensorData> data,List<String> metricNames) {
        ResultEntity resultEntity = new ResultEntity();
        Map<Long, List<SensorData>> sensorDataGroupByTime = data.stream().collect(Collectors.groupingBy(SensorData::getObsTimestamp));
        Map<Long, List<SensorData>> result = new LinkedHashMap<>();
        sensorDataGroupByTime.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        DataArrayEntity arrayEntity = new DataArrayEntity();
        arrayEntity.setElementCountEntity(new ElementCountEntity(result.size()));
        arrayEntity.setElementTypeEntity(generateElementTypeEntity(metricNames));
        EncodingEntity encodingEntity = generateEncodingEntity("sensorData");
        arrayEntity.setEncodingEntity(encodingEntity);
        arrayEntity.setValuesEntity(generateValuesEntity(result,metricNames,encodingEntity));
        resultEntity.setDataArrayEntity(arrayEntity);
        return resultEntity;

    }

    private ElementTypeEntity generateElementTypeEntity(List<String> metricNames) {
        ElementTypeEntity elementTypeEntity = new ElementTypeEntity();

        elementTypeEntity.setName("Components");
        elementTypeEntity.setGmlId("Components");
        List<FieldEntity> fieldEntityList = new ArrayList<>();

        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setTime("urn:ogc:property:time:iso8601");
        fieldEntity.setName("time");
        fieldEntityList.add(fieldEntity);

        fieldEntity = new FieldEntity();
        fieldEntity.setName("longitude");
        QuantityEntity quantityEntity = new QuantityEntity();
        quantityEntity.setDefinition("urn:ogc:property:location:EPSG:4326:longitude");
        quantityEntity.setUom("deg");
        fieldEntity.setQuantity(quantityEntity);
        fieldEntityList.add(fieldEntity);

        fieldEntity = new FieldEntity();
        fieldEntity.setName("latitude");
        quantityEntity = new QuantityEntity();
        quantityEntity.setDefinition("urn:ogc:property:location:EPSG:4326:latitude");
        quantityEntity.setUom("deg");
        fieldEntity.setQuantity(quantityEntity);
        fieldEntityList.add(fieldEntity);


        for (String s: metricNames){
            fieldEntity = new FieldEntity();
            fieldEntity.setName(s);
            QuantityEntity quantity = new QuantityEntity();
            quantity.setDefinition(s);
            fieldEntity.setQuantity(quantity);
            fieldEntityList.add(fieldEntity);
        }
        elementTypeEntity.setFieldEntityList(fieldEntityList);
        return elementTypeEntity;
    }

    private List<String> generateObservationDataName(SOSEntity sosEntity) {
        if (sosEntity!=null){
            if (sosEntity.getBody()!=null){
                if (sosEntity.getBody().getProcedure()!=null){
                    return sosEntity.getBody().getProcedure();
                }
            }
        }
        return null;
    }

    private String generateObservationDataProcedure(SOSEntity sosEntity) {
        String procedure = null;
        if (sosEntity!=null){
            if (sosEntity.getBody()!=null){
                if (sosEntity.getBody().getProcedure()!=null){
                    procedure = sosEntity.getBody().getProcedure().get(0);
                }
            }
        }
        return procedure;
    }

    private FeatureOfInterestEntity generateObservationDataFeature(SOSEntity sosEntity) {
        FeatureOfInterestEntity featureOfInterestEntity = null;
        if (sosEntity!=null){
            if (sosEntity.getBody()!=null){
                if (sosEntity.getBody().getFeatureOfInterest()!=null){
                    featureOfInterestEntity = new FeatureOfInterestEntity();
                    featureOfInterestEntity.setHref(sosEntity.getBody().getFeatureOfInterest().get(0));
                    return featureOfInterestEntity;
                }
            }
        }
        return featureOfInterestEntity;
    }

//    private ResultEntity generateObservationResultEntity(SensorInfo sensorInfo, List<SensorData> sensorDatas,String key) {
//        ResultEntity resultEntity = new ResultEntity();
//        DataArrayEntity arrayEntity = new DataArrayEntity();
//        Map<Integer, List<SensorData>> sensorDataGroupByTime = sensorDatas.stream().collect(Collectors.groupingBy(SensorData::getObsTimestamp));
//        ElementCountEntity elementCountEntity = new ElementCountEntity();
//        elementCountEntity.setCountValues(sensorDataGroupByTime.size());
//        arrayEntity.setElementCountEntity(elementCountEntity);
//        arrayEntity.setElementTypeEntity(generateElementTypeEntity("sensorData",sensorInfo));
//        EncodingEntity encodingEntity = generateEncodingEntity("sensorData");
//        arrayEntity.setEncodingEntity(encodingEntity);
//        arrayEntity.setValuesEntity(generateValuesEntity(sensorDataGroupByTime,encodingEntity));
//        resultEntity.setDataArrayEntity(arrayEntity);
//        return resultEntity;
//    }

    private ValuesEntity generateValuesEntity(Map<Long, List<SensorData>> sensorDataGroupByTime,List<String> metricNames,EncodingEntity encodingEntity) {
        ValuesEntity valuesEntity = new ValuesEntity();
        String result = "";
        for (Long key:sensorDataGroupByTime.keySet()){
            List<SensorData> sensorDatas = sensorDataGroupByTime.get(key);
            Map<String, List<SensorData>> collect = sensorDatas.stream().collect(Collectors.groupingBy(SensorData::getSensorId));


            for (String sensorId:collect.keySet()){
                result =  result + DateUtils.convertToISO8601(key * 1000L)+encodingEntity.getTokenSeparator();
                List<SensorData> sensorData = collect.get(sensorId);
                result = result + sensorData.get(0).getLongitude()+encodingEntity.getTokenSeparator();
                result = result + sensorData.get(0).getLatitude()+encodingEntity.getTokenSeparator();
                for (String metricName:metricNames){
                    List<SensorData> dataList = sensorData.stream().filter(sensorData1 -> sensorData1.getMetricName().equals(metricName)).collect(Collectors.toList());
                    if (dataList.size()>0){
                        SensorData data = dataList.get(0);
                        result = result + data.getMetricValue()+encodingEntity.getTokenSeparator();
                    }else {
                        result = result +encodingEntity.getTokenSeparator();
                    }
                }
                result = result.substring(0, result.length()-encodingEntity.getTokenSeparator().length()) + encodingEntity.getBlockSeparator()+"\n";
            }
        }
        result = result.substring(0, result.length()-encodingEntity.getBlockSeparator().length()-1);
        valuesEntity.setValue(result);
        return valuesEntity;
    }

    private EncodingEntity generateEncodingEntity(String target) {
        if (target.equals("sensorData")) {
            EncodingEntity entity = new EncodingEntity();
            entity.setBlockSeparator("@@");
            entity.setDecimalSeparator(".");
            entity.setTokenSeparator(",");
            return entity;
        }else {
            throw new RuntimeException();
        }
    }

    private PhenomenonTimeEntity generatePhenomenonTimeEntity(List<SensorData> sensorData) {
        PhenomenonTimeEntity phenomenonTimeEntity = new PhenomenonTimeEntity();
        Long min = sensorData.stream().mapToLong(SensorData::getObsTimestamp).min().getAsLong();
        Long max = sensorData.stream().mapToLong(SensorData::getObsTimestamp).max().getAsLong();
        TimePeriodEntity timePeriodEntity = new TimePeriodEntity();
        timePeriodEntity.setBeginTime(DateUtils.convertToISO8601(min*1000L));
        timePeriodEntity.setEndTime(DateUtils.convertToISO8601(max*1000L));
        phenomenonTimeEntity.setTimePeriodEntity(timePeriodEntity);
        return phenomenonTimeEntity;
    }

//    private ElementTypeEntity generateElementTypeEntity(String target,SensorInfo sensorInfo){
//        if (target.equals("sensorData")){
//            List<String> metricName = LocationHandle.metricNameHandleOne(sensorInfo);
//            ElementTypeEntity elementTypeEntity = new ElementTypeEntity();
//            elementTypeEntity.setName("Components");
//            List<FieldEntity> fieldEntityList = new ArrayList<>();
//            FieldEntity fieldEntity = new FieldEntity();
//            fieldEntity.setTime("urn:ogc:property:time:iso8601");
//            fieldEntity.setName("time");
//            fieldEntityList.add(fieldEntity);
//            for (String s: metricName){
//                fieldEntity = new FieldEntity();
//                fieldEntity.setName(s);
//                QuantityEntity quantity = new QuantityEntity();
//                quantity.setDefinition(s);
//                fieldEntity.setQuantity(quantity);
//                fieldEntityList.add(fieldEntity);
//            }
//            elementTypeEntity.setFieldEntityList(fieldEntityList);
//            return elementTypeEntity;
//        }else {
//            throw new RuntimeException();
//        }
//
//    }

    public static String fetchGroupKey(SensorData sensorData){
        return sensorData.getSensorId() + "#" + sensorData.getObsTimestamp();
    }
}
