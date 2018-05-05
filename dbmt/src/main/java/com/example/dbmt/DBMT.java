package com.example.dbmt;

import com.example.dbmt.entity.DbLink;
import com.example.dbmt.entity.DbMetadata;
import com.example.dbmt.entity.ProjectInfo;
import com.example.dbmt.generator.SqlExecutor;
import com.example.dbmt.generator.SqlGenerator;
import com.example.dbmt.generator.SqlGeneratorFactory;
import com.example.dbmt.manager.MetadataManager;
import com.example.dbmt.manager.ProjectManager;
import com.example.dbmt.tool.DbUrlGenerator;
import com.example.dbmt.tool.DbUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DBMT {
    public static ProjectInfo currentProjectInfo = new ProjectInfo();
    public static DbMetadata currentTargetDbMetadata;
    public static DbMetadata currentSourceDbMetadata;

    public static ProjectInfo loadProject(File projectFile)
            throws IOException {
        return (ProjectInfo) ProjectManager.loadProject(projectFile, ProjectInfo.class);
    }

    public static void saveProject(File projectFile, ProjectInfo projectInfo)
            throws IOException {
        ProjectManager.saveProject(projectFile, projectInfo);
    }

    public static DbMetadata loadMetadata(File metadataFile)
            throws IOException {
        return MetadataManager.loadMetadata(metadataFile);
    }

    public static DbMetadata loadMetadata(DbLink dbLink)
            throws SQLException {
        return MetadataManager.loadMetadata(dbLink);
    }

    public static void saveMetadata(File metadataFile, DbLink dbLink)
            throws IOException, SQLException {
        if (!testConnection(dbLink)) {
            throw new SQLException("数据库连接失败");
        }
        DbMetadata dbMetadata = MetadataManager.loadMetadata(dbLink);
        MetadataManager.saveMetadata(metadataFile, dbMetadata);
    }

    public static void saveMetadata(File metadataFile, DbMetadata dbMetadata)
            throws IOException, SQLException {
        MetadataManager.saveMetadata(metadataFile, dbMetadata);
    }

    public static List<String> generaterSql(DbMetadata sourceDbMetadata, DbMetadata targetDbMetadata)
            throws Exception {
        DbComparer.compare(sourceDbMetadata, targetDbMetadata);
        String dbType = sourceDbMetadata.getDbType().toUpperCase();
        SqlGenerator sqlgenerator = SqlGeneratorFactory.create(dbType);
        return sqlgenerator.GeneratorSql(sourceDbMetadata, targetDbMetadata);
    }

    public static List<String> getNeddRemoveColumns(DbMetadata dbMetadata) {
        return DbComparer.getNeedRemoveColumns(dbMetadata);
    }

    public static List<String> getNeedRemoveTables(DbMetadata dbMetadata) {
        return DbComparer.getNeedRemoveTables(dbMetadata);
    }

    public static List<String> excuteSql(List<String> sqlList, DbLink targetLink)
            throws SQLException {
        return SqlExecutor.execute(sqlList, targetLink);
    }

    public static boolean testConnection(DbLink dbLink)
            throws SQLException {
        String dbUrl = DbUrlGenerator.generateDbUrl(dbLink);

        return DbUtil.testConnection(dbUrl, dbLink.getUserName(), dbLink.getPassword());
    }
}
