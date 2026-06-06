package org.cug.geodt.weibo.sos.pojo.json;


import java.io.Serializable;
import java.util.List;


public class Schema<T> implements Serializable {
    private String name;
    private String type;
    private String statics;
    private List<T> codebook;

    public Schema() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatics() {
        return statics;
    }

    public void setStatics(String statics) {
        this.statics = statics;
    }

    public List<T> getCodebook() {
        return codebook;
    }

    public void setCodebook(List<T> codebook) {
        this.codebook = codebook;
    }

    public Schema(String name, String type, String statics, List<T> codebook) {
        this.name = name;
        this.type = type;
        this.statics = statics;
        this.codebook = codebook;
    }
}
