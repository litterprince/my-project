package com.error.undeclaredThrowableException;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        IService service = new ServiceImpl();
        IService serviceProxy = (IService) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                service.getClass().getInterfaces(), new IServiceProxy(service));
        try {
            serviceProxy.foo();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
