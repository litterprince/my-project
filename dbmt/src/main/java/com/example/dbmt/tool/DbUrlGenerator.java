package com.example.dbmt.tool;

import com.example.dbmt.entity.DbLink;

import java.util.HashMap;

public class DbUrlGenerator {
    private static HashMap<String, String> dbUrlTemplates = new HashMap();

    public static String generateDbUrl(String type, String address, String dbName)
    {
        type = type.toUpperCase();
        String template = (String)dbUrlTemplates.get(type);
        if (template != null) {
            return String.format(template, new Object[] { address, dbName });
        }
        return "";
    }

    public static String generateDbUrl(DbLink dbLink) {
        String type = dbLink.getDbType().toUpperCase();
        String template = (String)dbUrlTemplates.get(type);
        if (template != null) {
            return String.format(template, new Object[] { dbLink.getAddress(), dbLink.getDbName() });
        }
        return "";
    }

    private static String generateDriver(String type)
    {
        type = type.toUpperCase();
        if (type.equals("MYSQL"))
            return "com.mysql.jdbc.Driver";
        if (type.equals("ORACLE"))
            return "oracle.jdbc.driver.OracleDriver";
        if (type.equals("ORACLE")) {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
        return "";
    }

    static
    {
        dbUrlTemplates.put("MYSQL", "jdbc:mysql://%s/%s?useFastDateParsing=false&useUnicode=true&characterEncoding=UTF-8");
        dbUrlTemplates.put("ORACLE", "jdbc:oracle:thin:@%s/%s");
        dbUrlTemplates.put("SQLSERVER", "jdbc:sqlserver://%s;DatabaseName=%s");
    }
}
