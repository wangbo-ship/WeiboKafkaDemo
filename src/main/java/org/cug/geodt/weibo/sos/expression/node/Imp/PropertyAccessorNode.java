package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.utils.PropertyAccessor;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 16:05
 */
public class PropertyAccessorNode implements ExprNode {

    String targetFieldName;
    Class<?> clazz;


    public PropertyAccessorNode( Class<?> clazz,String targetFieldName) {
        this.targetFieldName = targetFieldName;
        this.clazz = clazz;
    }



    @Override
    public ExprNode eval(Object obj) {
        PropertyAccessor propertyAccessor = new PropertyAccessor(clazz,targetFieldName);
        Object value = propertyAccessor.apply(obj);
        return new GenericValueNode(value);
    }
}
