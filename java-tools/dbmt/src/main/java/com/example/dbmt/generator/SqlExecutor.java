package com.example.dbmt.generator;

import com.example.dbmt.entity.DbLink;
import com.example.dbmt.tool.DbUrlGenerator;
import com.example.dbmt.tool.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SqlExecutor.class);

    public static List<String> execute(List<String> sqlList, DbLink dbLink) throws SQLException {
        List errorList = new ArrayList();
        String sqlUrl = DbUrlGenerator.generateDbUrl(dbLink);
        Connection connection = DbUtil.makeConnection(sqlUrl, dbLink.getUserName(), dbLink.getPassword());

        for (String sql : sqlList) {
            logger.debug("执行Sql:" + sql);
            try {
                Statement statement = connection.createStatement();
                statement.execute(sql);
                statement.close();
            } catch (Exception ex) {
                errorList.add("执行SQL语句出现错误，[" + sql + "]" + ex.getMessage());
            }
        }
        return errorList;
    }
}
