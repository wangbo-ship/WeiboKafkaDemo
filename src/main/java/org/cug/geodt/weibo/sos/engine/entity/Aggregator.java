package org.cug.geodt.weibo.sos.engine.entity;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package com.cug.geodt.dto
 * @Description
 * @date 2023/2/7 9:53
 */
public class Aggregator {
    private List<String> groupBy;
    private List<Agg> agg;

    public Aggregator(List<String> groupBy, List<Agg> agg) {
        this.groupBy = groupBy;
        this.agg = agg;
    }

    public Aggregator() {
    }

    public List<String> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<String> groupBy) {
        this.groupBy = groupBy;
    }

    public List<Agg> getAgg() {
        return agg;
    }

    public void setAgg(List<Agg> agg) {
        this.agg = agg;
    }

    @Override
    public String toString() {
        return "Aggregator{" +
                "groupBy=" + groupBy +
                ", agg=" + agg +
                '}';
    }
}
