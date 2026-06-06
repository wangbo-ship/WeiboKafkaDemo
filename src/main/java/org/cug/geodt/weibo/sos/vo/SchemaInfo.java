package org.cug.geodt.weibo.sos.vo;

/**
 * @FileName SchemaInfo
 * @Author WJW
 * @Date 2023/9/25 20:11
 * @Description
 */
public class SchemaInfo {
    private String fieldName;
    private String fieldType;
    private int num;
    private int length;
    private boolean notNull;

    public SchemaInfo() {
    }

    public SchemaInfo(String fieldName, String fieldType, int num, int length, boolean notNull) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.num = num;
        this.length = length;
        this.notNull = notNull;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }
}
