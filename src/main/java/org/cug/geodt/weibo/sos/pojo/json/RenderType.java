package org.cug.geodt.weibo.sos.pojo.json;


import java.io.Serializable;


public class RenderType implements Serializable {
    private String type;
    private String xAxis;
    private String yAxis;

    public RenderType(String type, String xAxis, String yAxis) {
        this.type = type;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public RenderType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }
}
