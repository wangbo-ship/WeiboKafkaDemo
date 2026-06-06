package org.cug.geodt.weibo.sos.expression.aggregator.node;

import org.cug.geodt.weibo.sos.enums.AggregatorEnum;
import org.cug.geodt.weibo.sos.expression.aggregator.AggNode;
import org.cug.geodt.weibo.sos.expression.aggregator.AggOp;
import org.cug.geodt.weibo.sos.expression.aggregator.Imp.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.expression.aggregator.node
 * @Description
 * @date 2023/2/9 15:32
 */
public class GroupByValue implements AggNode {

    private List<String> groupByAttrNameList;
    private String calculatedOp;
    private String targetFieldName;
    private Class clazz;


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

    public GroupByValue(List<String> groupByAttrNameList, String calculatedOp, String targetFieldName,Class clazz) {
        this.groupByAttrNameList = groupByAttrNameList;
        this.calculatedOp = calculatedOp;
        this.targetFieldName = targetFieldName;
        this.clazz = clazz;
    }

    @Override
    public Object apply(Object obj) {
        List<ValueAccessNode> valueAccessNodes = new ArrayList<>();
        for(String s:groupByAttrNameList){
            valueAccessNodes.add(new ValueAccessNode(s,clazz));
        }
        //分组
        GroupByNode groupByNode = new GroupByNode(valueAccessNodes);
        Map<String,Object> parametersMap = new HashMap<>();
        parametersMap.put("targetFieldName",targetFieldName);
        parametersMap.put("class",clazz);
        AggOp aggOp = createAggregatorOperation(calculatedOp,parametersMap);
        GroupByAndAggNode groupByAndAggNode = new GroupByAndAggNode(groupByNode,aggOp);
        //聚合
        return groupByAndAggNode.apply(obj);
    }

}
