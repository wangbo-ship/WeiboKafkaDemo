package org.cug.geodt.weibo.sos.expression.aggregator.node;

import org.cug.geodt.weibo.sos.utils.PropertyAccessor;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.expression.aggregator.node
 * @Description
 * @date 2023/2/9 15:16
 */
public class ValueAccessNode{

    private String targetFieldName;
    private Class clazz;

    public ValueAccessNode(String targetFieldName, Class clazz) {
        this.targetFieldName = targetFieldName;
        this.clazz = clazz;
    }

    public Object apply(Object obj) {
        PropertyAccessor propertyAccessor = new PropertyAccessor(clazz,targetFieldName);
        return propertyAccessor.apply(obj);
    }

    public String getLabel() {
        return targetFieldName;
    }
}
