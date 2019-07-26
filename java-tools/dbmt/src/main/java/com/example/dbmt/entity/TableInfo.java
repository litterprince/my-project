package com.example.dbmt.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TableInfo {
    private String tableName;
    private String type;
    private String primaryKey;
    private String remark;
    private Operate operate;
    private List<ColumnInfo> columns = new ArrayList();

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ColumnInfo getColumn(String columnName) {
        for (ColumnInfo column : this.columns) {
            if (column.getColumnName().equals(columnName)) {
                return column;
            }
        }
        return null;
    }

    public Operate getOperate() {
        return this.operate;
    }

    public void setOperate(Operate operate) {
        this.operate = operate;
    }

    public void addColumn(ColumnInfo columnInfo) {
        this.columns.add(columnInfo);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Collection<ColumnInfo> getColumns() {
        return Collections.unmodifiableCollection(this.columns);
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }
}
