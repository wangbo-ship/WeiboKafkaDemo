package org.cug.geodt.weibo.sos.pojo.json;

import org.cug.geodt.weibo.sos.utils.CodebookEncoder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class ReturnVO implements Serializable {
    private CodebookEncoder.EncodeDataset dataSet;
    private List<Renders> renders;

    public ReturnVO() {
    }

    public ReturnVO(CodebookEncoder.EncodeDataset dataSet, List<Renders> renders) {
        this.dataSet = dataSet;
        this.renders = renders;
    }

    public CodebookEncoder.EncodeDataset getDataSet() {
        return dataSet;
    }

    public void setDataSet(CodebookEncoder.EncodeDataset dataSet) {
        this.dataSet = dataSet;
    }

    public List<Renders> getRenders() {
        return renders;
    }

    public void setRenders(List<Renders> renders) {
        this.renders = renders;
    }
}
