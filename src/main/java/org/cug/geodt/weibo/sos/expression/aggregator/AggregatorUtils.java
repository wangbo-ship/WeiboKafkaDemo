package org.cug.geodt.weibo.sos.expression.aggregator;

import org.cug.geodt.weibo.sos.engine.entity.Aggregator;
import org.cug.geodt.weibo.sos.enums.AggregatorEnum;
import org.cug.geodt.weibo.sos.expression.aggregator.Imp.*;
import org.cug.geodt.weibo.sos.expression.aggregator.node.GroupByNode;
import org.cug.geodt.weibo.sos.expression.aggregator.node.ValueAccessNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.aggregator
 * @Description
 * @date 2022/12/31 14:29
 */
public class AggregatorUtils {

    static Map<String,Class<? extends AggOp>> stringToAggregatorOps;

    static {
        stringToAggregatorOps = new HashMap<>();
        stringToAggregatorOps.put(AggregatorEnum.MAX.getValue(), MaxAggOperation.class);
        stringToAggregatorOps.put(AggregatorEnum.MIN.getValue(), MinAggOperation.class);
        stringToAggregatorOps.put(AggregatorEnum.AVERAGE.getValue(), AverageOperation.class);
        stringToAggregatorOps.put(AggregatorEnum.SUM.getValue(), SumAggOperation.class);
        stringToAggregatorOps.put(AggregatorEnum.COUNT.getValue(), CountAggOperation.class);
    }


    static AggOp createAggregatorOperation(String operationName,Map<String,Object> parametersMap){
        try{
            return stringToAggregatorOps.get(operationName).getDeclaredConstructor(Map.class).newInstance(parametersMap);
        }catch (Exception e){
            return null;
        }
    }

    public static GroupByNode generateGroupByTree(Class clazz, List<Aggregator> aggregators) {
        List<ValueAccessNode> valueAccessNodes = new ArrayList<>();
        Aggregator aggregator = aggregators.get(0);
        for(String s:aggregator.getGroupBy()){
            valueAccessNodes.add(new ValueAccessNode(s,clazz));
        }
        return new GroupByNode(valueAccessNodes);
    }
//
//    static SimpleAggregatorOperation createSimpleAggregatorOperation(String operationName,Map<String,Object> parametersMap){
//        try{
//            return stringToSimpleAggregatorOps.get(operationName).getDeclaredConstructor(Map.class).newInstance(parametersMap);
//        }catch (Exception e){
//            return null;
//        }
//    }

//    public static <T> Enumerable executeAggregator(Class<T> clazz, Enumerable<T> data, Aggregator aggregator){
//        String operationName = aggregator.getAggOp();
//        Map<String,Object> parameters = new HashMap<>();
//        parameters.put("data",data);
//        parameters.put("class",clazz);
//        parameters.put("targetFieldName",aggregator.getTargetFieldName());
//        return createAggregatorOperation(operationName,parameters).aggregate();
//    }
//
//    public static <T> Object executeSimpleAggregator(Class<T> clazz, Enumerable<T> data, Aggregator aggregator){
//        String operationName = aggregator.getAggOp();
//        Map<String,Object> parameters = new HashMap<>();
//        parameters.put("data",data);
//        parameters.put("class",clazz);
//        parameters.put("targetFieldName",aggregator.getTargetFieldName());
//        return createSimpleAggregatorOperation(operationName,parameters).simpleAggregate();
//    }
//
//
//    public static List<String> listrem(List<String> listA,List<String> listB){
//        HashSet hs1 = new HashSet(listA);
//        HashSet hs2 = new HashSet(listB);
//        hs1.removeAll(hs2);
//        List<String> listC = new ArrayList<>();
//        listC.addAll(hs1);
//        return listC;
//    }

//    public static <T> ResDTO<T> generateAggRes(Class<T> clazz, Enumerable<T> aggResult, ReqDTO reqDTO) {
//        List<String> keepFieldNames = reqDTO.getDeriveDataSpec().getKeepFieldNames();
//        List<String> aggTargetFieldNames = reqDTO.getDeriveDataSpec().getAggregators().stream().
//                map(Aggregator::getTargetFieldName).collect(Collectors.toList());
//        List<String> outputFieldNames = reqDTO.getDeriveDataSpec().getAggregators().stream().map(Aggregator::getOutputFieldName).collect(Collectors.toList());
//        Integer dataSize = aggResult.count();
//        ResDTO resVO = new ResDTO();
//        DataSet dataSet = new DataSet<>();
//        List<Schema> schemas = new ArrayList<>();
//        for(String keepFieldName:keepFieldNames){
//            String finalFieldType ="";
//            String fieldType = "";
//            try{
//                fieldType = GeoDTUtilsOrigin.getFieldType(clazz, keepFieldName);
//            }catch (Exception e){
//                //如果是agg字符串
//                fieldType = GeoDTUtilsOrigin.getFieldType(clazz, aggTargetFieldNames.get(0));
//            }
//            if(fieldType.equals("class java.lang.Long")||fieldType.equals("class java.lang.String")){
//                        //时间字符串type
//                finalFieldType = "string";
//            }else if(fieldType.equals("class java.math.BigDecimal")){
//                        //dataV的Type
//                finalFieldType = "float";
//            }
//            //根据字段名获取值
//            String finalKeepFieldName = Pattern.compile("^.").matcher(keepFieldName).replaceFirst(m -> m.group().toUpperCase());
//            //根据字段名获取值
//            List finalData = aggResult.toList().stream().map(linq -> {
//                try {
//                    String fieldName = finalKeepFieldName;
//                    if (outputFieldNames.contains(keepFieldName)){
//                        fieldName = Pattern.compile("^.").matcher(aggTargetFieldNames.get(0)).replaceFirst(m -> m.group().toUpperCase());
//                    }
//                    Method method = clazz.getDeclaredMethod("get" + fieldName);
//                    Object value = method.(linq);
//                    return value.toString();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return null;
//            }).collect(Collectors.toList());
//            if(finalFieldType.equals("float")){
//                //处理float
//                finalData = (List) finalData.stream().map(m->Float.valueOf(m.toString())).collect(Collectors.toList());
//            }else if (keepFieldName.equals("time")){
//                //处理日期
//                finalData = (List) finalData.stream().map(m-> DateUtils.longToDateString(Long.parseLong(m.toString()))).collect(Collectors.toList());
//            }
//            Schema schema = new Schema<>(keepFieldName,finalFieldType,"null",finalData);
//            schemas.add(schema);
//        }
//        dataSet.setSchema(schemas);
//        dataSize = aggResult.count();
//        List<Data> dataList = new ArrayList<>();
//        for(int i= 0;i<dataSize;i++){//valueIdx
//            List<InnerData> innerDataList = new ArrayList<>();
//            Data data = new Data();
//            for(int j= 0 ;j<schemas.size();j++){//idx
//                InnerData innerData = new InnerData<>(new BigDecimal(j),new BigDecimal(i),schemas.get(j).getCodebook().get(i));
//                innerDataList.add(innerData);
//            }
//            data.setInnerData(innerDataList);
//            dataList.add(data);
//        }
//        dataSet.setData(dataList);
//        resVO.setDataSet(dataSet);
//        return resVO;
//    }
//
//    public static <T> ResDTO<T> generateSimpleAggRes(Class<T> clazz, Object simpleResult, ReqDTO reqDTO) {
//        String keepFieldName = reqDTO.getDeriveDataSpec().getKeepFieldNames().get(0);
//        String targetFieldName = reqDTO.getDeriveDataSpec().getAggregators().get(0).getTargetFieldName();
//        ResDTO resVO = new ResDTO();
//        DataSet dataSet = new DataSet<>();
//        List<Schema> schemas = new ArrayList<>();
//        String fieldType = GeoDTUtilsOrigin.getFieldType(clazz, targetFieldName);
//        String finalFieldType= "";
//        if(fieldType.equals("class java.lang.Long")||fieldType.equals("class java.lang.String")){
//            //时间字符串type
//            finalFieldType = "string";
//        }else if(fieldType.equals("class java.math.BigDecimal")){
//            //dataV的Type
//            finalFieldType = "float";
//        }
//        List finalData = new ArrayList<>();
//        finalData.add(simpleResult);
//        Schema schema = new Schema<>(keepFieldName,finalFieldType,"null",finalData);
//        schemas.add(schema);
//        dataSet.setSchema(schemas);
//        List<Data> dataList = new ArrayList<>();
//        for(int i= 0;i<finalData.size();i++){//valueIdx
//            List<InnerData> innerDataList = new ArrayList<>();
//            Data data = new Data();
//            for(int j= 0 ;j<schemas.size();j++){//idx
//                InnerData innerData = new InnerData<>(new BigDecimal(j),new BigDecimal(i),schemas.get(j).getCodebook().get(i));
//                innerDataList.add(innerData);
//            }
//            data.setInnerData(innerDataList);
//            dataList.add(data);
//        }
//        dataSet.setData(dataList);
//        resVO.setDataSet(dataSet);
//        return resVO;
//    }


    public static void main(String[] args) {

    }
}







