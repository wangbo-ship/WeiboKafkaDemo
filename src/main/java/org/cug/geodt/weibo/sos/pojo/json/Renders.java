package org.cug.geodt.weibo.sos.pojo.json;


import java.io.Serializable;


public class Renders<T> implements Serializable {
    private T renderType;
    private boolean defaultType;

    public Renders() {
    }

    public Renders(T renderType, boolean defaultType) {
        this.renderType = renderType;
        this.defaultType = defaultType;
    }

    public T getRenderType() {
        return renderType;
    }

    public void setRenderType(T renderType) {
        this.renderType = renderType;
    }

    public boolean isDefaultType() {
        return defaultType;
    }

    public void setDefaultType(boolean defaultType) {
        this.defaultType = defaultType;
    }
}
