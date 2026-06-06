package org.cug.geodt.weibo.sos.vo;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.dto
 * @Description
 * @date 2022/12/27 11:51
 */
public class DataSet<T> {
    private List<Schema<T>> schema;
    private List<Data<T>> data;

    public List<Schema<T>> getSchema() {
        return schema;
    }

    public void setSchema(List<Schema<T>> schema) {
        this.schema = schema;
    }

    public List<Data<T>> getData() {
        return data;
    }

    public void setData(List<Data<T>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "schema=" + schema +
                ", data=" + data +
                '}';
    }
}
