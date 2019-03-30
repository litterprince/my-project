package com.spring.client;

import com.spring.netty.RPC;
import com.spring.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/spring-context.xml", "/spring/spring-client.xml"})
public class ClientStart {
    @Test
    public void start() throws IOException, InterruptedException {
        int i = 3;
        while (i -- > 0) {
            HelloService service = (HelloService) RPC.call(HelloService.class);
            System.out.println(service.sayHello("jeff"));

            Thread.sleep(100);
        }
        System.in.read();
    }
}
