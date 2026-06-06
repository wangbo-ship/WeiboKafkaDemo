package org.cug.geodt.weibo.sos.expression.aggregator.node;

import java.util.List;

/**
 * @author mengquanqi
 * @version V1.0
 * @Package org.cug.geodt.weibo.sos.expression.aggregator.node
 * @Description
 * @date 2023/5/26 10:11
 */
public class AggregatorResult {
    private List<String> groupAttr;
    private List<String> groupName;
    private String targetFieldName;
    private Object value;
    private String op;
    private String groupNameStr;
    public AggregatorResult() {
    }

    public List<String> getGroupAttr() {
        return groupAttr;
    }

    public void setGroupAttr(List<String> groupAttr) {
        this.groupAttr = groupAttr;
    }

    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }

    public String getTargetFieldName() {
        return targetFieldName;
    }

    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getGroupNameStr() {
        return groupNameStr;
    }

    public void setGroupNameStr(String groupNameStr) {
        this.groupNameStr = groupNameStr;
    }

    @Override
    public String toString() {
        return "AggregatorResult{" +
                "groupAttr=" + groupAttr +
                ", groupName=" + groupName +
                ", targetFieldName='" + targetFieldName + '\'' +
                ", value=" + value +
                ", op='" + op + '\'' +
                ", groupNameStr='" + groupNameStr + '\'' +
                '}';
    }
}
