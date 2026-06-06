package org.cug.geodt.weibo.sos.pojo.json;


import java.io.Serializable;
import java.util.List;

public class DataSet<T> implements Serializable {
    private List<Schema> schema;
    private List<Data> data;

    public DataSet(List<Schema> schema, List<Data> data) {
        this.schema = schema;
        this.data = data;
    }

    public List<Schema> getSchema() {
        return schema;
    }

    public void setSchema(List<Schema> schema) {
        this.schema = schema;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public DataSet() {
    }
}
