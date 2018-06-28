package com.example.dbmt.generator;

import com.example.dbmt.DbComparer;
import com.example.dbmt.entity.ColumnInfo;
import com.example.dbmt.entity.DbMetadata;
import com.example.dbmt.entity.Operate;
import com.example.dbmt.entity.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SqlGenerator {
    public List<String> GeneratorSql(DbMetadata sourceMetadata, DbMetadata targetMetadata) {
        List sqlList = new ArrayList();
        List<TableInfo> difference = DbComparer.getDifference(sourceMetadata);
        for (TableInfo tableInfo : difference)
        {
            if (tableInfo.getOperate() == Operate.ADD) {
                generatorAddTableSql(tableInfo, sqlList);
            }
            else if (tableInfo.getOperate() == Operate.ALERT) {
                generatorAlertTableSql(tableInfo, targetMetadata.getTable(tableInfo.getTableName()), sqlList);
            }
        }

        /*List<TableInfo> difference1 = DbComparer.getDifference(targetMetadata);
        for (TableInfo tableInfo : difference1)
        {
            if (tableInfo.getOperate() == Operate.REMOVE) {
                generatorRemoveTableSql(tableInfo, sqlList);
            }
            else if (tableInfo.getOperate() == Operate.ALERT) {
                generatorRemoveColumnSql(tableInfo, sqlList);
            }
        }*/
        return sqlList;
    }

    public void generatorAlertTableSql(TableInfo tableInfo, TableInfo targetTable, List<String> sqlList) {
        Collection<ColumnInfo> tableInfoColumns = tableInfo.getColumns();
        for (ColumnInfo column : tableInfoColumns)
            if (column.getOperate() != Operate.NONE) {
                StringBuilder resultSql = new StringBuilder();
                generatorAlertTableColumnSql(column, targetTable.getColumn(column.getColumnName()), resultSql);
                sqlList.add(resultSql.toString());
            }
    }

    public void generatorAddTableSql(TableInfo tableInfo, List<String> sqlList) {
        StringBuilder resultSql = new StringBuilder();
        generatorAddTableHeadSql(tableInfo, resultSql);
        resultSql.append(System.lineSeparator());
        boolean isFirstChangeHappen = false;
        Collection<ColumnInfo> tableInfoColumns = tableInfo.getColumns();
        for (ColumnInfo column : tableInfoColumns) {
            if (isFirstChangeHappen) {
                resultSql.append(",").append(System.lineSeparator());
            }
            generatorAddTableColumnSql(column, resultSql);
            isFirstChangeHappen = true;
        }
        String primaryKey = tableInfo.getPrimaryKey();

        if (StringUtils.isNotBlank(primaryKey)) {
            resultSql.append(",").append(System.lineSeparator());
            generatorAddTablePrimaryKeySql(tableInfo, resultSql);
        }
        generatorAddTableEndSql(tableInfo, resultSql);
        sqlList.add(resultSql.toString());
    }

    public void generatorRemoveTableSql(TableInfo tableInfo, List<String> sqlList){
        StringBuilder resultSql = new StringBuilder();
        resultSql.append("DROP TABLE ");
        resultSql.append(tableInfo.getTableName());
        sqlList.add(resultSql.toString());
    }

    public void generatorRemoveColumnSql(TableInfo tableInfo, List<String> sqlList){
        StringBuilder resultSql = new StringBuilder();
        Collection<ColumnInfo> tableInfoColumns = tableInfo.getColumns();
        for (ColumnInfo column : tableInfoColumns) {
            if (column.getOperate() == Operate.REMOVE) {
                resultSql.setLength(0);
                resultSql.append("ALTER TABLE ");
                resultSql.append(tableInfo.getTableName());
                resultSql.append(" DROP COLUMN ");
                resultSql.append(column.getColumnName());
                sqlList.add(resultSql.toString());
            }
        }
    }

    protected abstract void generatorAddTableHeadSql(TableInfo paramTableInfo, StringBuilder paramStringBuilder);

    protected abstract void generatorAddTablePrimaryKeySql(TableInfo paramTableInfo, StringBuilder paramStringBuilder);

    protected abstract void generatorAddTableEndSql(TableInfo paramTableInfo, StringBuilder paramStringBuilder);

    protected abstract void generatorAddTableColumnSql(ColumnInfo paramColumnInfo, StringBuilder paramStringBuilder);

    protected abstract void generatorAlertTableColumnSql(ColumnInfo paramColumnInfo1, ColumnInfo paramColumnInfo2, StringBuilder paramStringBuilder);

    protected abstract void generatorAlertTablePrimaryKeySql(TableInfo paramTableInfo, StringBuilder paramStringBuilder);
}
