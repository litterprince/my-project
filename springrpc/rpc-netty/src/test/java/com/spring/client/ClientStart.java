package com.spring.client;

import com.spring.netty.RPC;
import com.spring.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {""})
public class ClientStart {
    @Test
    public void start() {
        HelloService service = (HelloService) RPC.call(HelloService.class);
        System.out.println(service.sayHello("jeff"));
    }
}
