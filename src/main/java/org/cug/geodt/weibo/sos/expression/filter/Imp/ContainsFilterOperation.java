package org.cug.geodt.weibo.sos.expression.filter.Imp;

import org.cug.geodt.weibo.sos.engine.entity.ReferValue;
import org.cug.geodt.weibo.sos.expression.filter.FilterOperation;
import org.cug.geodt.weibo.sos.expression.filter.FilterUtils;
import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.GenericValueNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.GeometryNode;
import org.cug.geodt.weibo.sos.expression.node.Imp.PropertyAccessorNode;
import org.cug.geodt.weibo.sos.expression.utils.ExprUtils;
import org.locationtech.jts.geom.Geometry;

import java.util.List;
import java.util.Map;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.expression.filter.Imp
 * @Description
 * @date 2023/7/29 19:45
 */
public class ContainsFilterOperation implements FilterOperation {
    ExprNode leftNode;

    ExprNode rightNode;

    private Map<String,Object> parameters;

    public void setLeftNode(ExprNode leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(ExprNode rightNode) {
        this.rightNode = rightNode;
    }

    public ContainsFilterOperation() {
    }

    public ContainsFilterOperation(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
    @Override
    public BooleanNode eval(Object obj) {
        List<ReferValue> referValues = (List<ReferValue>) parameters.get("referValues");
        ReferValue referValue = referValues.get(0);
        String targetFieldName = (String) parameters.get("targetFieldName");
        Class clazz = (Class) parameters.get("class");
        //想要查询的指定值
        this.leftNode = FilterUtils.handleGeometryString(referValue);
        //数据库中的值
        PropertyAccessorNode propertyAccessorNode =new PropertyAccessorNode(clazz,targetFieldName);
        Geometry value = (Geometry)((GenericValueNode) propertyAccessorNode.eval(obj)).getValue();
        this.rightNode = new GeometryNode(value);
        return ExprUtils.contains(this.leftNode.eval(obj),rightNode.eval(obj),targetFieldName);
    }
}
