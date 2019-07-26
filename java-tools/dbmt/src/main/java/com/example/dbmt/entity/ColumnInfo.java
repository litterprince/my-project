package com.example.dbmt.entity;

public class ColumnInfo {
    private String tableName;
    private String columnName;
    private String type;
    private int size;
    private int digits;
    private boolean nullable;
    private String remark;
    private String defaultValue;
    private String autoIncrement;
    private Operate operate;
    private int sqlType;

    public int getSqlType()
    {
        return this.sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public Operate getOperate() {
        return this.operate;
    }

    public void setOperate(Operate operate) {
        this.operate = operate;
    }

    public String getAutoIncrement() {
        return this.autoIncrement;
    }

    public void setAutoIncrement(String autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDigits() {
        return this.digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
