package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;
import org.cug.geodt.weibo.sos.expression.node.LogicNode;
import org.cug.geodt.weibo.sos.expression.utils.ExprUtils;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.node.Imp
 * @Description
 * @date 2023/1/8 17:32
 */
public class LogicNotNode implements LogicNode {

    ExprNode node;


    public LogicNotNode(ExprNode node) {
        this.node = node;
    }

    @Override
    public BooleanNode eval(Object obj) {
        return ExprUtils.not(node.eval(obj));
    }
}
