package org.cug.geodt.weibo.sos.expression.aggregator.node;

import org.apache.commons.lang.StringUtils;
import org.cug.geodt.weibo.sos.expression.aggregator.AggNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.expression.aggregator.node
 * @Description
 * @date 2023/2/9 15:47
 */
public class GroupByNode implements AggNode {

    private List<ValueAccessNode> keyNodes;

    public GroupByNode(List<ValueAccessNode> keyNodes) {
        this.keyNodes = keyNodes;
    }

    @Override
    public Object apply(Object obj) {
        List<String> keys = new ArrayList<>();
        for (ValueAccessNode v:keyNodes){
            keys.add(v.apply(obj).toString());
        }
        return StringUtils.join(keys,"%^");
    }

    public List<String> getLabel() {
        List<String> keys = new ArrayList<>();
        for (ValueAccessNode v:keyNodes){
            keys.add(v.getLabel());
        }
        return keys;
    }
}
