package com.spring.service;

import com.spring.annotation.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hi, " + name;
    }
}
