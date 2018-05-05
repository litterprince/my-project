package com.example.dbmt.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DbMetadata{
    private String dbType;
    private List<TableInfo> tables = new ArrayList();

    public String getDbType() {
        return this.dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public void addTable(TableInfo tableInfo) {
        this.tables.add(tableInfo);
    }

    public TableInfo getTable(String tableName) {
        for (TableInfo table : this.tables) {
            if (table.getTableName().equals(tableName)) {
                return table;
            }
        }
        return null;
    }

    public Collection<TableInfo> getTables() {
        return Collections.unmodifiableCollection(this.tables);
    }

    public void setTables(List<TableInfo> tables) {
        this.tables = tables;
    }
}
