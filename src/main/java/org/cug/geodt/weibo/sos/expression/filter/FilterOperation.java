package org.cug.geodt.weibo.sos.expression.filter;

import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.operationB
 * @Description
 * @date 2023/1/6 17:32
 */
public interface FilterOperation extends ExprNode {
    BooleanNode eval(Object obj);
}
