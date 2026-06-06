package org.cug.geodt.weibo.sos.vo;

/**
 * @FileName EntryByYear
 * @Author WJW
 * @Date 2023/9/6 20:22
 * @Description
 */
public class EntryByYear implements Comparable<EntryByYear>{
    private String year;
    private Long record;

    public EntryByYear() {
    }

    public EntryByYear(String year, Long record) {
        this.year = year;
        this.record = record;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getRecord() {
        return record;
    }

    public void setRecord(Long record) {
        this.record = record;
    }

    @Override
    public int compareTo(EntryByYear o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.year, o.year);
    }
}
