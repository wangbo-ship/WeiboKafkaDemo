package org.cug.geodt.weibo.sos.vo;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.dto
 * @Description
 * @date 2022/12/27 11:58
 */
public class ResVO<T> {
    private DataSet<T> dataSet;
    private List<Render> renders;

    public DataSet<T> getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet<T> dataSet) {
        this.dataSet = dataSet;
    }

    public List<Render> getRenders() {
        return renders;
    }

    public void setRenders(List<Render> renders) {
        this.renders = renders;
    }
}
