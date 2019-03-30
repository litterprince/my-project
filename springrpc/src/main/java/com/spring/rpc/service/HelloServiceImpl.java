package com.spring.rpc.service;

import com.spring.rpc.declare.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService{

    @Override
    public String sayHello(String name) {
        return "Hi, " + name;
    }
}
