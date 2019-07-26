package com.example.dbmt.generator;

import com.example.dbmt.entity.ColumnInfo;
import com.example.dbmt.entity.Operate;
import com.example.dbmt.entity.TableInfo;
import org.apache.commons.lang3.StringUtils;

public class OracleGenerator extends SqlGenerator {
    protected void generatorAddTableHeadSql(TableInfo tableInfo, StringBuilder resultSql)
    {
        resultSql.append(" CREATE TABLE \"").append(tableInfo.getTableName()).append("\" (");
    }

    protected void generatorAddTablePrimaryKeySql(TableInfo tableInfo, StringBuilder resultSql)
    {
        String keys = tableInfo.getPrimaryKey().replace(",", "\",\"");
        resultSql.append(" PRIMARY KEY(\"").append(keys).append("\")");
    }

    protected void generatorAddTableEndSql(TableInfo tableInfo, StringBuilder resultSql)
    {
        resultSql.append(")");
    }

    protected void generatorAddTableColumnSql(ColumnInfo column, StringBuilder resultSql)
    {
        setColumnInfo(column, null, resultSql, '"', '"');
    }

    protected void generatorAlertTableColumnSql(ColumnInfo column, ColumnInfo targetColumn, StringBuilder resultSql)
    {
        resultSql.append(" ALTER TABLE \"").append(column.getTableName()).append("\" ");
        if (column.getOperate() == Operate.ADD) {
            resultSql.append(" ADD ");
            setColumnInfo(column, null, resultSql, '"', '"');
        } else if (column.getOperate() == Operate.REMOVE) {
            resultSql.append(" DROP (\"").append(column.getColumnName()).append("\") ");
        } else if (column.getOperate() == Operate.ALERT) {
            resultSql.append(" MODIFY ");
            setColumnInfo(column, targetColumn, resultSql, '"', '"');
        }
    }

    protected void generatorAlertTablePrimaryKeySql(TableInfo tableInfo, StringBuilder resultSql)
    {
    }

    protected void setColumnInfo(ColumnInfo column, ColumnInfo targetColumn, StringBuilder resultSql, char prefix, char suffix)
    {
        resultSql.append(" ").append(prefix).append(column.getColumnName()).append(suffix).append(" ").append(column.getType());
        if (((column.getSqlType() == 6) || (column.getSqlType() == 8) || (column.getSqlType() == 2) || (column.getSqlType() == 3) || (column.getSqlType() == 1) || (column.getSqlType() == 12) || (column.getSqlType() == -15) || (column.getSqlType() == -9) || (column.getType().equals("NVARCHAR2"))) && (column.getSize() > 0))
        {
            resultSql.append("(").append(column.getSize());
            if (column.getDigits() > 0) {
                resultSql.append(",").append(column.getDigits());
            }
            resultSql.append(")");
        }
        if (StringUtils.isNotBlank(column.getDefaultValue())) {
            resultSql.append(" DEFAULT ").append(column.getDefaultValue());
        }

        if ((targetColumn != null) && (column.isNullable() == targetColumn.isNullable())) {
            return;
        }

        if (!column.isNullable())
            resultSql.append(" NOT NULL");
        else
            resultSql.append(" NULL");
    }
}
