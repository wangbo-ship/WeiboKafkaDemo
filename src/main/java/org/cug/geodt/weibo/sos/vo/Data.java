package org.cug.geodt.weibo.sos.vo;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.vo
 * @Description
 * @date 2022/12/27 13:26
 */
public class Data<T> {
    private List<InnerData<T>> innerData;

    public List<InnerData<T>> getInnerData() {
        return innerData;
    }

    public void setInnerData(List<InnerData<T>> innerData) {
        this.innerData = innerData;
    }

    @Override
    public String toString() {
        return "Data{" +
                "innerData=" + innerData +
                '}';
    }
}
