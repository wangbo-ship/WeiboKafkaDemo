package org.cug.geodt.weibo.sos.expression.utils;

import net.hydromatic.linq4j.function.Function1;
import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.utils
 * @Description
 * @date 2023/1/7 9:52
 */
public class ExprWrappedFunction <T> implements Function1<T,Boolean> {
    ExprNode wrappedNode;
    public ExprWrappedFunction(ExprNode root) {
        this.wrappedNode = root;
    }

    @Override
    public Boolean apply(T t) {
        ExprNode result = this.wrappedNode.eval(t);
        if (result instanceof BooleanNode){
            BooleanNode booleanResult = (BooleanNode) result;
            return booleanResult.getValue();
        }
        throw new RuntimeException();
    }
}
