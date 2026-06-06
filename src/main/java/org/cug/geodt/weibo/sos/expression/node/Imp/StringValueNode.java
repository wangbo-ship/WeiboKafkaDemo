package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.ValueNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:55
 */
public class StringValueNode implements ValueNode {
    String value;

    public StringValueNode(String value) {
        this.value = value;
    }

    @Override
    public ValueNode eval(Object obj) {
        return this;
    }

    @Override
    public void setValue(Object obj) {
        if (obj instanceof String){
            this.value = obj.toString();
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public Object getValue() {
        return value;
    }
}
