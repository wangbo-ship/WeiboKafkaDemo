package org.cug.geodt.weibo.sos.expression.filter.Imp;

import org.cug.geodt.weibo.sos.engine.entity.ReferValue;
import org.cug.geodt.weibo.sos.expression.filter.FilterOperation;
import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.BooleanFalseNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.GenericValueNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.PropertyAccessorNode;
import org.cug.geodt.weibo.sos.expression.utils.ExprUtils;

import java.util.List;
import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.operationB.Imp
 * @Description
 * @date 2023/1/9 12:51
 */
public class InFilterOperation implements FilterOperation {
    ExprNode leftNode;

    ExprNode rightNode;

    private Map<String,Object> parameters;

    public void setLeftNode(ExprNode leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(ExprNode rightNode) {
        this.rightNode = rightNode;
    }

    public InFilterOperation() {
    }

    public InFilterOperation(Map<String, Object> parameters) {
        this.parameters = parameters;
    }


    @Override
    public BooleanNode eval(Object obj) {
        Class clazz = (Class) parameters.get("class");
        List<ReferValue> referValues = (List<ReferValue>) parameters.get("referValues");
        String targetFieldName = (String) parameters.get("targetFieldName");
        BooleanNode combineNode = BooleanFalseNode.INSTANCE;
        for(ReferValue referValue:referValues){
            this.leftNode = new GenericValueNode(referValue.getValue());
            PropertyAccessorNode propertyAccessorNode =new PropertyAccessorNode(clazz,targetFieldName);
            Object value = ((GenericValueNode) propertyAccessorNode.eval(obj)).getValue();
            //数据库中的值
            this.rightNode = new GenericValueNode(value);
            BooleanNode booleanNode = ExprUtils.in(leftNode.eval(obj),rightNode.eval(obj),targetFieldName);
            combineNode = ExprUtils.or(combineNode,booleanNode);
        }
        return combineNode;
    }
}
