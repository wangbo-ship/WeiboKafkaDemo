package org.cug.geodt.weibo.sos.vo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @FileName EntryMonth
 * @Author WJW
 * @Date 2023/9/6 20:22
 * @Description
 */
public class EntryByMonth implements Comparable<EntryByMonth>{
    private String month;
    private Long record;

    public EntryByMonth() {
    }

    public EntryByMonth(String month, Long record) {
        this.month = month;
        this.record = record;
    }

    public String getMonth() {
        return month;
    }

    public void setYear(String month) {
        this.month = month;
    }

    public Long getRecord() {
        return record;
    }

    public void setRecord(Long record) {
        this.record = record;
    }

    @Override
    public int compareTo(EntryByMonth entryByMonth) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.month, entryByMonth.month);
    }

    public static void main(String [] args) {
        ArrayList<EntryByMonth> entryByMonths = new ArrayList<>();
        EntryByMonth entryByMonth = new EntryByMonth("2023-08", 15L);
        EntryByMonth entryByMonth2 = new EntryByMonth("2022-09", 15L);
        EntryByMonth entryByMonth1 = new EntryByMonth("2023-09", 15L);
        entryByMonths.add(entryByMonth);
        entryByMonths.add(entryByMonth2);
        entryByMonths.add(entryByMonth1);
        Collections.sort(entryByMonths);
        System.out.println(entryByMonths.toString());
    }
}
