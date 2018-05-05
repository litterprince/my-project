package com.example.dbmt.generator;

import java.sql.SQLException;

public class SqlGeneratorFactory {
    public static SqlGenerator create(String dbType) throws SQLException {
        SqlGenerator generator = null;
        if (dbType.equals("MYSQL")) {
            generator = new MySqlGenerator();
        }else if(dbType.equals("SQLSERVER")) {
            generator = new SqlServerGenerator();
        }else if(dbType.equals("ORACLE")){
            generator = new OracleGenerator();
        }else{
            throw new SQLException("不支持的数据库类型：" + dbType);
        }
        return generator;
    }
}
