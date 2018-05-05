package com.example.dbmt.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static Logger logger = LoggerFactory.getLogger(DbUtil.class);

    public static boolean testConnection(String urlpath, String uid, String pwd)
            throws SQLException
    {
        try
        {
            Connection conn = makeConnection(urlpath, uid, pwd);
            if (conn != null) {
                close(conn);
                return true;
            }
        }
        catch (SQLException e) {
            throw e;
        }
        return false;
    }

    public static Connection makeConnection(String urlpath, String uid, String pwd)
            throws SQLException
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlpath, uid, pwd);
        } catch (SQLException e) {
            throw e;
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                logger.info(e.getMessage());
            }
    }

    static
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            logger.error("没有合适的MS-SQL server驱动供使用", e);
        }
    }
}
