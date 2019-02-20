package com.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        //1.加载Class对象
        Class clazz = Class.forName("main.java.com.reflect.Student");
        Constructor con = clazz.getConstructor();
        //调用构造方法
        Object obj = con.newInstance();
        Student student = (Student)obj;
        Method method = clazz.getMethod("product", String.class);
        method.invoke(student, "开始");
    }
}
