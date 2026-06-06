package org.cug.geodt.weibo.sos.expression.node;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:37
 */
//表达式节点
public interface ExprNode {
    ExprNode eval(Object obj);
}
