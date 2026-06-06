package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.BooleanNode;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.filter
 * @Description
 * @date 2023/1/4 15:41
 */
public class BooleanTrueNode implements BooleanNode {

    public static BooleanTrueNode INSTANCE = new BooleanTrueNode();

    private BooleanTrueNode(){
    }

    @Override
    public BooleanNode eval(Object obj) {
        return this;
    }

    @Override
    public void setValue(Object obj) {

    }

    @Override
    public Boolean getValue() {
        return true;
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
