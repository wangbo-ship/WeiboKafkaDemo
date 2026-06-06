package org.cug.geodt.weibo.sos.engine.entity;

public class Agg {

    private String aggOp;
    private String targetFieldName;
    private String outputFieldName;


    public Agg() {
    }

    public Agg(String aggOp, String targetFieldName, String outputFieldName) {
        this.aggOp = aggOp;
        this.targetFieldName = targetFieldName;
        this.outputFieldName = outputFieldName;
    }

    @Override
    public String toString() {
        return "aggregator{" +
                "aggOp='" + aggOp + '\'' +
                ", targetFieldName='" + targetFieldName + '\'' +
                ", outputFieldName='" + outputFieldName + '\'' +
                '}';
    }

    public String getAggOp() {
        return aggOp;
    }

    public void setAggOp(String aggOp) {
        this.aggOp = aggOp;
    }

    public String getTargetFieldName() {
        return targetFieldName;
    }

    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public String getOutputFieldName() {
        return outputFieldName;
    }

    public void setOutputFieldName(String outputFieldName) {
        this.outputFieldName = outputFieldName;
    }
}
