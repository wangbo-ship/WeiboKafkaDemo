package org.cug.geodt.weibo.sos.expression.node;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:38
 */
public interface ValueNode extends ExprNode {
    void setValue(Object obj);
    Object getValue();
}
