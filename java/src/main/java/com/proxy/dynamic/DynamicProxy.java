package main.java.com.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler {
    private Subject subject;

    public DynamicProxy(Subject subject){
        this.subject = subject;
    }


    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        System.out.println("before "+method.getName());
        Object object = method.invoke(subject, args);
        System.out.println("after "+method.getName());
        return object;
    }
}
