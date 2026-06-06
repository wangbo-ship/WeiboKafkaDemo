package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.BooleanNode;
import org.cug.geodt.weibo.sos.expression.node.ValueNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:42
 */
public class BooleanFalseNode implements BooleanNode {

    public static BooleanFalseNode INSTANCE = new BooleanFalseNode();
    private BooleanFalseNode(){
    }


    @Override
    public void setValue(Object obj) {

    }

    @Override
    public Boolean getValue() {
        return false;
    }

    @Override
    public ValueNode eval(Object obj) {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BooleanNode){
            BooleanNode object = (BooleanNode) obj;
            return getValue().equals(object.getValue());
        }else {
            throw  new RuntimeException();
        }
    }
}
