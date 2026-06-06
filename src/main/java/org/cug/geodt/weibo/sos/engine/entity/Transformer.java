package org.cug.geodt.weibo.sos.engine.entity;

public class Transformer {
    private String expr;
    private String outputFieldName;

    @Override
    public String toString() {
        return "transformer{" +
                "expr='" + expr + '\'' +
                ", outputFieldName='" + outputFieldName + '\'' +
                '}';
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public String getOutputFieldName() {
        return outputFieldName;
    }

    public void setOutputFieldName(String outputFieldName) {
        this.outputFieldName = outputFieldName;
    }
}
