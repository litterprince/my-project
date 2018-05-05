package com.example.springtemplate.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlHolder {
    private String sql;
    private List<Object> arrayList = new ArrayList();

    public SqlHolder() {
    }

    public void addParam(Object var1) {
        this.arrayList.add(var1);
    }

    public Object[] getParams() {
        return this.arrayList.toArray();
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String var1) {
        this.sql = var1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("sql语句:");
        sb.append(this.sql).append("\r\n").append("             参数值:");
        Iterator var2 = this.arrayList.iterator();

        while(var2.hasNext()) {
            Object var3 = var2.next();
            sb.append(var3).append(",");
        }

        if (this.arrayList.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
