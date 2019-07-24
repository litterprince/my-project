package com.service.impl;


import com.service.HelloService;
import other.hessian.service2.bean.User;

public class HelloServiceImpl implements HelloService {

    public User sayHello(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }
}
