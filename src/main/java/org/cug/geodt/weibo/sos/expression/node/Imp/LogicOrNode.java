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
 * @date 2023/1/6 17:27
 */
public class LogicOrNode implements LogicNode {

    ExprNode leftNode;

    ExprNode rightNode;

    public LogicOrNode(ExprNode leftNode, ExprNode rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }


    @Override
    public BooleanNode eval(Object obj) {
//        if (leftNode == null ){
//            this.leftNode = BooleanTrueNode.INSTANCE;
//        }
//        if (rightNode == null ){
//            this.rightNode = BooleanTrueNode.INSTANCE;
//        }
        return ExprUtils.or(leftNode.eval(obj),rightNode.eval(obj));
    }
}
