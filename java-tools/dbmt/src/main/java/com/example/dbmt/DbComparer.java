package com.example.dbmt;

import com.example.dbmt.entity.ColumnInfo;
import com.example.dbmt.entity.DbMetadata;
import com.example.dbmt.entity.Operate;
import com.example.dbmt.entity.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.*;

public class DbComparer {
    public static void compare(DbMetadata sourceDbMetadata, DbMetadata targetDbMetadata) throws SQLException {
        compareTables(sourceDbMetadata, targetDbMetadata);
        compareColumns(sourceDbMetadata, targetDbMetadata);
    }

    public static List<TableInfo> getDifference(DbMetadata dbMetadata) {
        List diffTable = new LinkedList();
        Collection<TableInfo> dbMetadataTables = dbMetadata.getTables();
        for (TableInfo tableInfo : dbMetadataTables) {
            if (tableInfo.getOperate() != Operate.NONE) {
                diffTable.add(tableInfo);
            }
        }
        return diffTable;
    }

    public static List<String> getNeedRemoveColumns(DbMetadata dbMetadata)
    {
        List rtnList = new ArrayList();
        Collection dbMetadataTables = dbMetadata.getTables();
        for (Iterator i$ = dbMetadataTables.iterator(); i$.hasNext(); ) {
            TableInfo dbTable = (TableInfo)i$.next();
            if (dbTable.getOperate() == Operate.ALERT) {
                Collection<ColumnInfo> dbTableColumns = dbTable.getColumns();
                for (ColumnInfo column : dbTableColumns)
                    if (column.getOperate() == Operate.REMOVE)
                        rtnList.add(dbTable.getTableName() + "." + column.getColumnName());
            }
        }
        return rtnList;
    }

    public static List<String> getNeedRemoveTables(DbMetadata dbMetadata)
    {
        List rtnList = new ArrayList();
        Collection<TableInfo> dbMetadataTables = dbMetadata.getTables();
        for (TableInfo dbTable : dbMetadataTables) {
            if (dbTable.getOperate() == Operate.REMOVE) {
                rtnList.add(dbTable.getTableName());
            }
        }
        return rtnList;
    }

    private static void compareTables(DbMetadata sourceDbMetadata, DbMetadata targetDbMetadata)
    {
        Collection<TableInfo> sourceDbMetadataTables = sourceDbMetadata.getTables();
        for (TableInfo sourceTable : sourceDbMetadataTables) {
            String tableName = sourceTable.getTableName();
            if (targetDbMetadata.getTable(tableName) == null) {
                sourceTable.setOperate(Operate.ADD);
            }
        }

        Collection<TableInfo> targetDbMetadataTables = targetDbMetadata.getTables();
        for (TableInfo targetTable : targetDbMetadataTables) {
            String tableName = targetTable.getTableName();
            if (sourceDbMetadata.getTable(tableName) == null)
                targetTable.setOperate(Operate.REMOVE);
        }
    }

    private static void compareColumns(DbMetadata sourceDbMetadata, DbMetadata targetDbMetadata) {
        Collection sourceDbMetadataTables = sourceDbMetadata.getTables();
        for (Iterator i$ = sourceDbMetadataTables.iterator(); i$.hasNext(); ) {
            TableInfo sourceTable = (TableInfo)i$.next();
            if (sourceTable.getOperate() != Operate.ADD) {
                TableInfo targetTable = targetDbMetadata.getTable(sourceTable.getTableName());
                Collection<ColumnInfo> sourceTableColumns = sourceTable.getColumns();
                for (ColumnInfo sourceTableColumn : sourceTableColumns) {
                    ColumnInfo targetTableColumn = targetTable.getColumn(sourceTableColumn.getColumnName());
                    if (targetTableColumn == null) {
                        sourceTable.setOperate(Operate.ALERT);
                        sourceTableColumn.setOperate(Operate.ADD);
                    }else if ((!targetTableColumn.getType().equalsIgnoreCase(sourceTableColumn.getType())) ||
                            (targetTableColumn.getSize() < sourceTableColumn.getSize()) ||
                            (targetTableColumn.getDigits() != sourceTableColumn.getDigits()) ||
                            (targetTableColumn.isNullable() != sourceTableColumn.isNullable()) ||
                            (!targetTableColumn.getAutoIncrement().equalsIgnoreCase(sourceTableColumn.getAutoIncrement())) ||
                            ((StringUtils.isNotBlank(sourceTableColumn.getDefaultValue())) && (!sourceTableColumn.getDefaultValue().equalsIgnoreCase(targetTableColumn.getDefaultValue())))) {
                        sourceTableColumn.setOperate(Operate.ALERT);
                        sourceTable.setOperate(Operate.ALERT);
                    }
                }
            }
        }
        TableInfo sourceTable;
        TableInfo targetTable;
        Collection targetDbMetadataTables = targetDbMetadata.getTables();
        for (Iterator i$ = targetDbMetadataTables.iterator(); i$.hasNext(); ) { targetTable = (TableInfo)i$.next();
            if (targetTable.getOperate() != Operate.REMOVE){
                sourceTable = sourceDbMetadata.getTable(targetTable.getTableName());
                Collection<ColumnInfo> targetTableColumns = targetTable.getColumns();
                for (ColumnInfo targetColumn : targetTableColumns) {
                    ColumnInfo sourceColumn = sourceTable.getColumn(targetColumn.getColumnName());
                    if (sourceColumn == null) {
                        targetTable.setOperate(Operate.ALERT);
                        targetColumn.setOperate(Operate.REMOVE);
                    }
                }
            }
        }
    }
}
