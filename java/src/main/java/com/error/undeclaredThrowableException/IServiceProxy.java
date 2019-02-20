package com.error.undeclaredThrowableException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IServiceProxy implements InvocationHandler {
    private Object target;

    IServiceProxy(Object target){
        this.target = target;
    }

    /*@Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException e){
            throw e.getCause();
        }
    }*/

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}