package org.cug.geodt.weibo.sos.pojo.json;


import java.io.Serializable;

public class InnerData implements Serializable {
    private Integer idx;
    private String valueidx;

    public InnerData() {
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getValueidx() {
        return valueidx;
    }

    public void setValueidx(String valueidx) {
        this.valueidx = valueidx;
    }

    public InnerData(Integer idx, String valueidx) {
        this.idx = idx;
        this.valueidx = valueidx;
    }
}
