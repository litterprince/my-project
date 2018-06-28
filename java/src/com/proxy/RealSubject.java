package com.proxy;

public class RealSubject implements Subject {
    @Override
    public void rent() {
        System.out.println("i want to rent my house");
    }

    @Override
    public void hello(String str) {
        System.out.println("hello: "+str);
    }
}
