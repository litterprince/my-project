package com.example.dbmt.entity;

import java.io.Serializable;

public class ProjectInfo implements Serializable {
    private String projectName = "";
    private boolean isDeleteColumns = false;
    private boolean isDeleteTables = false;
    private boolean isDealPrimaryKey = false;
    private DbLink sourceDbLink;
    private DbLink targetDbLink;

    public String getProjectName()
    {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isDeleteColumns() {
        return this.isDeleteColumns;
    }

    public void setIsDeleteColumns(boolean isDeleteColumns) {
        this.isDeleteColumns = isDeleteColumns;
    }

    public boolean isDeleteTables() {
        return this.isDeleteTables;
    }

    public void setIsDeleteTables(boolean isDeleteTables) {
        this.isDeleteTables = isDeleteTables;
    }

    public boolean isDealPrimaryKey() {
        return this.isDealPrimaryKey;
    }

    public void setIsDealPrimaryKey(boolean isDealPrimaryKey) {
        this.isDealPrimaryKey = isDealPrimaryKey;
    }

    public DbLink getSourceDbLink() {
        return this.sourceDbLink;
    }

    public void setSourceDbLink(DbLink sourceDbLink) {
        this.sourceDbLink = sourceDbLink;
    }

    public DbLink getTargetDbLink() {
        return this.targetDbLink;
    }

    public void setTargetDbLink(DbLink targetDbLink) {
        this.targetDbLink = targetDbLink;
    }
}
