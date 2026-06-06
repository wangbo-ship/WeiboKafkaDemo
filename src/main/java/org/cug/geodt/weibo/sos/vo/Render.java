package org.cug.geodt.weibo.sos.vo;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.dto
 * @Description
 * @date 2022/12/27 11:59
 */
public class Render {
    private String renderType;
    private Boolean defaultType;

    public String getRenderType() {
        return renderType;
    }

    public void setRenderType(String renderType) {
        this.renderType = renderType;
    }

    public Boolean getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(Boolean defaultType) {
        this.defaultType = defaultType;
    }

    @Override
    public String toString() {
        return "Render{" +
                "renderType='" + renderType + '\'' +
                ", defaultType=" + defaultType +
                '}';
    }
}
