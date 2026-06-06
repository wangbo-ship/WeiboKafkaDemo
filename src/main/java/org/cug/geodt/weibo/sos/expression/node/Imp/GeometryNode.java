package org.cug.geodt.weibo.sos.expression.node.Imp;

import org.cug.geodt.weibo.sos.expression.node.ValueNode;
import org.locationtech.jts.geom.Geometry;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.expression.node.Imp
 * @Description
 * @date 2023/7/3 11:01
 */
public class GeometryNode implements ValueNode {
    Geometry value;

    public GeometryNode(Geometry geometry) {
        this.value = geometry;
    }

    @Override
    public ValueNode eval(Object obj) {
        return this;
    }

    @Override
    public void setValue(Object obj) {
        if (obj instanceof Geometry){
            this.value = (Geometry) obj;
        }else {
            throw  new RuntimeException();
        }
    }

    @Override
    public Geometry getValue() {
        return value;
    }
}
