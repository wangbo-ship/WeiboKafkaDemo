package org.cug.geodt.weibo.sos.vo;

/**
 * @FileName EntryByDay
 * @Author WJW
 * @Date 2023/9/6 22:11
 * @Description
 */
public class EntryByDay implements Comparable<EntryByDay>{
    private String day;
    private Long record;

    public EntryByDay() {
    }

    public EntryByDay(String day, Long record) {
        this.day = day;
        this.record = record;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getRecord() {
        return record;
    }

    public void setRecord(Long record) {
        this.record = record;
    }

    @Override
    public int compareTo(EntryByDay o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.day, o.day);
    }
}
