package org.cug.geodt.weibo.sos.expression.filter.Imp;

import org.cug.geodt.weibo.sos.engine.entity.ReferValue;
import org.cug.geodt.weibo.sos.expression.filter.FilterOperation;
import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.GenericValueNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.PropertyAccessorNode;
import org.cug.geodt.weibo.sos.expression.utils.ExprUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.operationB.Imp
 * @Description
 * @date 2023/1/9 15:09
 */
public class RangeFilterOperation implements FilterOperation {
    ExprNode leftNode;

    ExprNode rightNode;

    private Map<String,Object> parameters;

    public void setLeftNode(ExprNode leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(ExprNode rightNode) {
        this.rightNode = rightNode;
    }

    public RangeFilterOperation() {
    }

    public RangeFilterOperation(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public BooleanNode eval(Object obj) {
        List<ReferValue> referValues = (List<ReferValue>) parameters.get("referValues");
        referValues.sort(Comparator.comparing(ReferValue::getValue));
        ReferValue referValue1 = referValues.get(0);
        ReferValue referValue2 = referValues.get(1);
        Class clazz = (Class) parameters.get("class");
        String targetFieldName = (String) parameters.get("targetFieldName");
        PropertyAccessorNode propertyAccessorNode =new PropertyAccessorNode(clazz,targetFieldName);
        Object value = ((GenericValueNode) propertyAccessorNode.eval(obj)).getValue();
        this.rightNode = new GenericValueNode(value);
        ExprNode leftNode1 = new GenericValueNode(referValue1.getValue());
        ExprNode leftNode2 = new GenericValueNode(referValue2.getValue());
        //大于等于节点1
        BooleanNode node1 = ExprUtils.gte(leftNode1.eval(obj), rightNode,targetFieldName);
        //小于等于节点2
        BooleanNode node2 = ExprUtils.lte(leftNode2.eval(obj),rightNode,targetFieldName);
        return ExprUtils.and(node1,node2);
    }
}
