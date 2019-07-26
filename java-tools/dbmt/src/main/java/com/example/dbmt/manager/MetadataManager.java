package com.example.dbmt.manager;

import com.alibaba.fastjson.JSONObject;
import com.example.dbmt.entity.*;
import com.example.dbmt.tool.DbUrlGenerator;
import com.example.dbmt.tool.DbUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class MetadataManager {
    public static void saveMetadata(File metadataFile, DbMetadata dbMetadata) throws IOException {
        FileUtils.writeStringToFile(metadataFile, JSONObject.toJSONString(JSONObject.toJSON(dbMetadata), false));
    }

    public static DbMetadata loadMetadata(File metadataFile) throws IOException {
        String metadataJson = FileUtils.readFileToString(metadataFile);
        return (DbMetadata)JSONObject.toJavaObject(JSONObject.parseObject(metadataJson), DbMetadata.class);
    }

    public static DbMetadata loadMetadata(DbLink dbLink) throws SQLException {
        Connection connection = null;
        DbMetadata dbMetadata = new DbMetadata();
        try {
            String dblink = DbUrlGenerator.generateDbUrl(dbLink);
            connection = DbUtil.makeConnection(dblink, dbLink.getUserName(), dbLink.getPassword());

            DatabaseMetaData metaData = connection.getMetaData();

            transMetaDataToMap(metaData, dbLink, dbMetadata);
        } finally {
            if (connection != null) {
                DbUtil.close(connection);
            }
        }
        return dbMetadata;
    }

    private static void transMetaDataToMap(DatabaseMetaData metaData, DbLink dblink, DbMetadata dbInfo) throws SQLException {
        dbInfo.setDbType(dblink.getDbType());

        String schema = dblink.getUserName();
        if (dblink.getDbType().equalsIgnoreCase("SQLSERVER"))
            schema = "dbo";
        else if (dblink.getDbType().equalsIgnoreCase("MYSQL"))
            schema = null;
        else if (dblink.getDbType().equalsIgnoreCase("ORACLE")) {
            schema = schema.toUpperCase();
        }
        transTable(metaData, dbInfo, schema);
        transColumn(metaData, dbInfo, schema);
    }

    private static void transColumn(DatabaseMetaData metadata, DbMetadata dbMetadata, String schema) throws SQLException {
        Collection<TableInfo> tableInfos = dbMetadata.getTables();
        for (TableInfo tableInfo : tableInfos) {
            ResultSet rs = metadata.getColumns(null, schema, tableInfo.getTableName(), null);

            while (rs.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setTableName(rs.getString("TABLE_NAME"));
                columnInfo.setColumnName(rs.getString("COLUMN_NAME"));
                columnInfo.setType(rs.getString("TYPE_NAME"));
                columnInfo.setSize(rs.getInt("COLUMN_SIZE"));
                columnInfo.setDigits(rs.getInt("DECIMAL_DIGITS"));
                columnInfo.setNullable(rs.getBoolean("NULLABLE"));
                columnInfo.setRemark(rs.getString("REMARKS"));
                columnInfo.setDefaultValue(rs.getString("COLUMN_DEF"));

                if (dbMetadata.getDbType().equalsIgnoreCase("ORACLE")) {
                    columnInfo.setAutoIncrement("NO");
                    if (StringUtils.isNotEmpty(columnInfo.getDefaultValue()))
                        columnInfo.setDefaultValue(columnInfo.getDefaultValue().trim());
                }
                else {
                    columnInfo.setAutoIncrement(rs.getString("IS_AUTOINCREMENT"));
                }

                columnInfo.setSqlType(rs.getInt("DATA_TYPE"));
                columnInfo.setOperate(Operate.NONE);
                tableInfo.addColumn(columnInfo);
            }
            rs.close();
        }
    }

    private static void transTable(DatabaseMetaData metadata, DbMetadata dbMetadata, String schema)
            throws SQLException
    {
        ResultSet rs = metadata.getTables(null, schema, null, new String[] { "TABLE" });
        while (rs.next()) {
            ResultSet primaryKeys = metadata.getPrimaryKeys(null, schema, rs.getString("TABLE_NAME"));
            StringBuilder primaryKey = new StringBuilder();
            while (primaryKeys.next()) {
                if (primaryKey.length() > 0)
                    primaryKey.append(',');
                primaryKey.append(primaryKeys.getString("COLUMN_NAME"));
            }

            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(rs.getString("TABLE_NAME"));
            tableInfo.setType(rs.getString("TABLE_TYPE"));
            tableInfo.setOperate(Operate.NONE);
            tableInfo.setPrimaryKey(primaryKey.toString());
            tableInfo.setRemark(rs.getString("REMARKS"));
            dbMetadata.addTable(tableInfo);
        }
        rs.close();
    }
}
