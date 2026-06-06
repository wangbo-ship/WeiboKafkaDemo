package org.cug.geodt.weibo.sos.expression.aggregator.node;

import net.hydromatic.linq4j.Enumerable;
import net.hydromatic.linq4j.Grouping;
import org.cug.geodt.weibo.sos.expression.aggregator.AggNode;
import org.cug.geodt.weibo.sos.expression.aggregator.AggOp;

import java.util.*;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.expression.aggregator.node
 * @Description
 * @date 2023/5/24 20:41
 */
public class GroupByAndAggNode implements AggNode {

    private GroupByNode groupByNode;
    private AggOp aggOp;

    public GroupByAndAggNode(GroupByNode groupByNode, AggOp aggOp) {
        this.groupByNode = groupByNode;
        this.aggOp = aggOp;
    }

    @Override
    public Object apply(Object obj) {
        Map<String,Object> map = new HashMap<>();
        Enumerable enumerable = (Enumerable)obj;
        enumerable = enumerable.groupBy(o -> groupByNode.apply(o));
        enumerable.forEach(o -> {
            Grouping grouping = (Grouping) o;
            map.put(grouping.getKey().toString(),aggOp.eval(grouping));
        });
        System.out.println(map);
        List<AggregatorResult> aggregatorResults = new ArrayList<>();
        List<Object> labels = this.getLabel();
        for (String key:map.keySet()){
            List<String> keyList = Collections.unmodifiableList(Arrays.asList(key.split("%\\^")));
            AggregatorResult aggregatorResult = new AggregatorResult();
            aggregatorResult.setGroupName(keyList);
            aggregatorResult.setGroupAttr((List) labels.get(0));
            aggregatorResult.setTargetFieldName(labels.get(1).toString());
            aggregatorResult.setValue(map.get(key));
            aggregatorResult.setOp(aggOp.getOp());
            aggregatorResult.setGroupNameStr(key);
            aggregatorResults.add(aggregatorResult);
        }
        return aggregatorResults;
    }

    public List<Object> getLabel(){
        List<Object> labels = new ArrayList<>();
        labels.add(this.groupByNode.getLabel());
        labels.add(this.aggOp.getLabel());
        return labels;
    }
}
