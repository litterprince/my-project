package com.spring.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Consumer {
    public void getMessage(Map<String, Object> map) {
        System.out.println("消息消费者 = " + map.toString());
    }
}