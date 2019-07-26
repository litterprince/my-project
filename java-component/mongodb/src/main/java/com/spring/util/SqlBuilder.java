package com.spring.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SqlBuilder {
    /**
     * 根据属性名获取属性值
     * */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * 获取属性名数组
     * */
    private static String[] getFiledName(Object o){
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    public static Map<String, String> getFiledValues(Object o){
        HashMap<String, String> map = new HashMap();
        String[] fieldNames = getFiledName(o);
        for(int i=0;i<fieldNames.length;i++){
            map.put(fieldNames[i], (String) getFieldValueByName(fieldNames[i], o));
        }
        return map;
    }
}
