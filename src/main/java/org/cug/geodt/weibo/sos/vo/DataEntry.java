package org.cug.geodt.weibo.sos.vo;

/**
 * @FileName DataEntry
 * @Author WJW
 * @Date 2023/9/6 11:17
 * @Description 用于返回前端的数据数据记录条数
 */
public class DataEntry {

    private String type;

    private int number;

    public DataEntry() {
    }

    public DataEntry(String type, int number) {
        this.type = type;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
