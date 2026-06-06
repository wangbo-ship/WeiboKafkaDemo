package org.cug.geodt.weibo.sos.utils.csw;

import java.util.List;

/**
 * @Classname TransactionResponseInfo
 * @Description TODO
 * @Date 2023/8/25 15:32
 * @Created by mjh
 */
public class TransactionResponseInfo implements ResponseInfo{
    private Long totalInserted;
    private Long totalUpdated;
    private Long totalDeleted;

    private List<BriefRecordInfo> insertResults;

    public Long getTotalInserted() {
        return totalInserted;
    }

    public void setTotalInserted(Long totalInserted) {
        this.totalInserted = totalInserted;
    }

    public Long getTotalUpdated() {
        return totalUpdated;
    }

    public void setTotalUpdated(Long totalUpdated) {
        this.totalUpdated = totalUpdated;
    }

    public Long getTotalDeleted() {
        return totalDeleted;
    }

    public void setTotalDeleted(Long totalDeleted) {
        this.totalDeleted = totalDeleted;
    }

    public List<BriefRecordInfo> getInsertResults() {
        return insertResults;
    }

    public void setInsertResults(List<BriefRecordInfo> insertResults) {
        this.insertResults = insertResults;
    }

    @Override
    public String getResponseType() {
        return "Transaction";
    }

    @Override
    public String toString() {
        return "TransactionResponseInfo{" +
                "totalInserted=" + totalInserted +
                ", totalUpdated=" + totalUpdated +
                ", totalDeleted=" + totalDeleted +
                ", insertResults=" + insertResults +
                '}';
    }
}
