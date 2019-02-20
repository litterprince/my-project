package com.proxy.dynamic;

public class RealSubject implements Subject {
    @Override
    public void hello(String str) {
        System.out.println("hello: "+str);
    }
}
