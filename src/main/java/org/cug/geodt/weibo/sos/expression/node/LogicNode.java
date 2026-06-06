package org.cug.geodt.weibo.sos.expression.node;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.node
 * @Description
 * @date 2023/1/6 11:01
 */
public interface LogicNode extends ExprNode{
    BooleanNode eval(Object obj);
}
