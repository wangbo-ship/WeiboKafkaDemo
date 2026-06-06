package org.cug.geodt.weibo.sos.expression.node;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:40
 */
public interface BooleanNode extends ValueNode {
    ExprNode eval(Object obj);

    Boolean getValue();

    boolean equals(Object obj);

}
