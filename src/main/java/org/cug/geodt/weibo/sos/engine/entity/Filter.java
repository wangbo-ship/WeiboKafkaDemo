package org.cug.geodt.weibo.sos.engine.entity;

import java.util.List;

public class Filter {
    private String filterOp;
    private String targetFieldName;
    private List<ReferValue> ReferValues;
    private Boolean notLogic;

//    private String rightLogic;


    public Filter(String filterOp, String targetFieldName, List<ReferValue> referValues, Boolean notLogic) {
        this.filterOp = filterOp;
        this.targetFieldName = targetFieldName;
        ReferValues = referValues;
        this.notLogic = notLogic;
    }

    public Filter() {
    }

    public String getFilterOp() {
        return filterOp;
    }

    public void setFilterOp(String filterOp) {
        this.filterOp = filterOp;
    }

    public String getTargetFieldName() {
        return targetFieldName;
    }

    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public List<ReferValue> getReferValues() {
        return ReferValues;
    }

    public void setReferValues(List<ReferValue> referValues) {
        ReferValues = referValues;
    }

    public Boolean getNotLogic() {
        return notLogic;
    }

    public void setNotLogic(Boolean notLogic) {
        this.notLogic = notLogic;
    }

//    public String getRightLogic() {
//        return rightLogic;
//    }
//
//    public void setRightLogic(String rightLogic) {
//        this.rightLogic = rightLogic;
//    }

    @Override
    public String toString() {
        return "Filter{" +
                "filterOp='" + filterOp + '\'' +
                ", targetFieldName='" + targetFieldName + '\'' +
                ", ReferValues=" + ReferValues +
                ", notLogic=" + notLogic +
//                ", rightLogic='" + rightLogic + '\'' +
                '}';
    }
}
