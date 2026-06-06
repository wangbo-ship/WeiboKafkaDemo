package org.cug.geodt.weibo.sos.expression.node.Imp;

import net.hydromatic.linq4j.function.Function1;
import org.cug.geodt.weibo.sos.expression.node.ExprNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter.node.Imp
 * @Description
 * @date 2023/1/6 11:57
 */
public class FunctionNode implements ExprNode {

    Function1 function;

    public FunctionNode(Function1 function) {
        this.function = function;
    }

    public Function1 getFunction() {
        return function;
    }

    public void setFunction(Object obj) {
        if(obj instanceof Function1){
            this.function = (Function1) obj;
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public FunctionNode eval(Object obj) {
        return this;
    }
}
