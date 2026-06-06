package org.cug.geodt.weibo.sos.pojo.json;


import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    private List<InnerData> innerData;

    public Data() {
    }

    public Data(List<InnerData> innerData) {
        this.innerData = innerData;
    }

    public List<InnerData> getInnerData() {
        return innerData;
    }

    public void setInnerData(List<InnerData> innerData) {
        this.innerData = innerData;
    }
}
