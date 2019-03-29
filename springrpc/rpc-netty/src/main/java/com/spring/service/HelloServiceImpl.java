package com.spring.service;

public class HelloServiceImpl implements HelloService{

    @Override
    public String sayHello(String name) {
        return "Hi, " + name;
    }
}
