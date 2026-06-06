package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.ValueNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:46
 */
public class DoubleValueNode implements ValueNode {
    Double value;

    public DoubleValueNode(Double value) {
        this.value = value;
    }

    @Override
    public ValueNode eval(Object obj) {
        return this;
    }


    @Override
    public void setValue(Object obj) {
        if(obj instanceof Double||obj instanceof Float||obj instanceof Integer){
            this.value = Double.valueOf(obj.toString());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public Double getValue() {
        return value;
    }
}
