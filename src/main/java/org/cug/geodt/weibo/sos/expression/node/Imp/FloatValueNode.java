package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.ValueNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:50
 */
public class FloatValueNode implements ValueNode {
    Float value;

    public FloatValueNode(Float value) {
        this.value = value;
    }

    @Override
    public ValueNode eval(Object obj) {
        return this;
    }

    @Override
    public void setValue(Object obj) {
        if (obj instanceof Float||obj instanceof Integer){
            this.value = Float.valueOf(obj.toString());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public Float getValue() {
        return value;
    }
}
