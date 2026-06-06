package org.cug.geodt.weibo.sos.vo;

import java.math.BigDecimal;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.vo
 * @Description
 * @date 2022/12/27 12:51
 */
public class InnerData<T> {
    private BigDecimal idx;
    private BigDecimal valueidx;
    private T value;

    public InnerData(BigDecimal idx, BigDecimal valueidx, T value) {
        this.idx = idx;
        this.valueidx = valueidx;
        this.value = value;
    }


    public BigDecimal getIdx() {
        return idx;
    }

    public void setIdx(BigDecimal idx) {
        this.idx = idx;
    }

    public BigDecimal getValueidx() {
        return valueidx;
    }

    public void setValueidx(BigDecimal valueidx) {
        this.valueidx = valueidx;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "InnerData{" +
                "idx=" + idx +
                ", valueidx=" + valueidx +
                ", value=" + value +
                '}';
    }
}
