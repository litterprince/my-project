package com.example.springtemplate.db;

import com.example.springtemplate.db.mapping.Column;
import com.example.springtemplate.db.mapping.DbBeanProcessor;
import com.example.springtemplate.db.mapping.Table;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SqlBuilder<T>  {
    private static final Logger logger = LoggerFactory.getLogger(SqlBuilder.class);

    public SqlBuilder() {
    }

    public static SqlHolder buildInsert(Object paramObject){
        SqlHolder localSqlHolder = new SqlHolder();
        StringBuilder localStringBuilder1 = new StringBuilder();
        StringBuilder localStringBuilder2 = new StringBuilder();

        Field[] fields = paramObject.getClass().getDeclaredFields();
        for (Field localField : fields) {
            if (!isStaticOrFinal(localField)) {
                localSqlHolder.addParam(formatObject(getValue(paramObject, localField)));
                localStringBuilder1.append(getName(localField)).append(",");
                localStringBuilder2.append("?").append(",");
            }
        }
        subStr(localStringBuilder1);
        subStr(localStringBuilder2);

        StringBuilder sql = new StringBuilder();
        Map<String, String> localMap = beanToMap(paramObject);
        sql.append("INSERT INTO ").append((String)localMap.get("name")).append(" (");
        sql.append(localStringBuilder1).append(") ");
        sql.append(" VALUES(").append(localStringBuilder2).append(") ");
        localSqlHolder.setSql(sql.toString());
        return localSqlHolder;
    }

    public static SqlHolder buildUpdate(Object paramObject) {
        SqlHolder localSqlHolder = new SqlHolder();
        Field[] fields = paramObject.getClass().getDeclaredFields();
        Map<String, String> localMap = beanToMap(paramObject);

        StringBuilder localStringBuilder = new StringBuilder();
        Object localObject = null;
        localStringBuilder.append("UPDATE ").append((String)localMap.get("name")).append(" SET ");
        String strPk = (String)localMap.get("pk");
        for (Field localField : fields) {
            if ((!isStaticOrFinal(localField)) && (isColumn(localField))) {
                if (strPk.equalsIgnoreCase(getName(localField))) {
                    localObject = formatObject(getValue(paramObject, localField));
                } else {
                    localSqlHolder.addParam(formatObject(getValue(paramObject, localField)));
                    localStringBuilder.append(getName(localField)).append("=?").append(",");
                }
            }
        }
        subStr(localStringBuilder);
        localStringBuilder.append(new StringBuilder().append(" WHERE ").append(strPk).append("=?").toString());
        localSqlHolder.addParam(localObject);
        localSqlHolder.setSql(localStringBuilder.toString());
        return localSqlHolder;
    }

    public static SqlHolder buildGet(Object object, Object param){
        SqlHolder localSqlHolder = new SqlHolder();
        StringBuilder localStringBuilder = new StringBuilder();
        Map<String, String> localMap = beanToMap(object);
        localStringBuilder.append("select * from ").append((String)localMap.get("name"));
        String str = (String)localMap.get("pk");
        localStringBuilder.append(new StringBuilder().append(" WHERE ").append(str).append("=?").toString());
        localSqlHolder.addParam(param);
        localSqlHolder.setSql(localStringBuilder.toString());
        return localSqlHolder;
    }

    public static SqlHolder buildGet(Class paramClass, Object param){
        SqlHolder localSqlHolder = new SqlHolder();
        StringBuilder localStringBuilder = new StringBuilder();
        Map<String, String> localMap = beanToMap(paramClass);
        localStringBuilder.append("select * from ").append((String)localMap.get("name"));
        String str = (String)localMap.get("pk");
        localStringBuilder.append(new StringBuilder().append(" WHERE ").append(str).append("=?").toString());
        localSqlHolder.addParam(param);
        localSqlHolder.setSql(localStringBuilder.toString());
        return localSqlHolder;
    }

    private static String getName(Field field) {
        return DbBeanProcessor.prop2column(field.getName());
    }

    private static Object getValue(Object paramObject, Field field){
        Object localObject = null;
        try
        {
            if (field.isAccessible())
            {
                localObject = field.get(paramObject);
            }
            else
            {
                field.setAccessible(true);
                localObject = field.get(paramObject);
                field.setAccessible(false);
            }
        }
        catch (Exception localException)
        {
            logger.error("获取属性值失败！", localException);
            throw new RuntimeException("获取属性值失败！", localException);
        }
        return localObject;
    }

    private static void subStr(StringBuilder paramStringBuilder){
        if (paramStringBuilder.lastIndexOf(",") == paramStringBuilder.length() - 1)
            paramStringBuilder.deleteCharAt(paramStringBuilder.length() - 1);
    }

    private static boolean isStaticOrFinal(Field paramField){
        int i = paramField.getModifiers();
        if ((Modifier.isStatic(i)) || (Modifier.isFinal(i)))
            return true;
        Transient localTransient = (Transient)paramField.getAnnotation(Transient.class);
        return (localTransient != null) && (localTransient.value() == true);
    }

    private static Object formatObject(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Date) {
            Date date = (Date)obj;
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } else {
            return obj;
        }
    }

    private static boolean isColumn(Field field) {
        Column column = (Column)field.getAnnotation(Column.class);
        return column != null ? column.updatable() : true;
    }

    private static Map<String, String> beanToMap(Object var0) {
        HashMap<String, String> map = new HashMap();
        String tableName = var0.getClass().getSimpleName();
        Table table = (Table)var0.getClass().getAnnotation(Table.class);
        if (table != null && StringUtils.isNotEmpty(table.name())) {
            tableName = table.name();
            map.put("name", tableName);
            map.put("pk", table.pk());
        }
        return map;
    }

    private static Map<String, String> beanToMap(Class paramClass) {
        HashMap<String, String> map = new HashMap();
        String tableName = paramClass.getSimpleName();
        Table table = (Table)paramClass.getAnnotation(Table.class);
        if (table != null && StringUtils.isNotEmpty(table.name())) {
            tableName = table.name();
            map.put("name", tableName);
            map.put("pk", table.pk());
        }
        return map;
    }
}
