package org.cug.geodt.weibo.sos.engine.entity;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DeriveDataSpec {

    private List<String> keepFieldNames;
    private List<Filter> filters;
    private List<Transformer> transformers;
    private List<Aggregator> aggregators;

    public DeriveDataSpec(List<String> keepFieldNames, List<Filter> filters, List<Transformer> transformers, List<Aggregator> aggregators) {
        this.keepFieldNames = keepFieldNames;
        this.filters = filters;
        this.transformers = transformers;
        this.aggregators = aggregators;
    }

    public List<String> getKeepFieldNames() {
        return keepFieldNames;
    }

    public void setKeepFieldNames(List<String> keepFieldNames) {
        this.keepFieldNames = keepFieldNames;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Transformer> getTransformers() {
        return transformers;
    }

    public void setTransformers(List<Transformer> transformers) {
        this.transformers = transformers;
    }

    public List<Aggregator> getAggregators() {
        return aggregators;
    }

    public void setAggregators(List<Aggregator> aggregators) {
        this.aggregators = aggregators;
    }

    @Override
    public String toString() {
        return "DeriveDataSpec{" +
                "keepFieldNames=" + keepFieldNames +
                ", filters=" + filters +
                ", transformers=" + transformers +
                ", aggregators=" + aggregators +
                '}';
    }
}
