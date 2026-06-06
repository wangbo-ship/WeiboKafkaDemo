package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.ValueNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 16:07
 */
public class GenericValueNode implements ValueNode {
    Object obj;

    public GenericValueNode(Object obj) {
        this.obj = obj;
    }

    @Override
    public ExprNode eval(Object obj) {
        return this;
    }

    @Override
    public void setValue(Object obj) {
        this.obj = obj;

    }

    @Override
    public Object getValue() {
        return obj;
    }
}
