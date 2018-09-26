package com.maven.simple;

import java.util.Properties;

import org.apache.kafka.clients.producer.*;


/**
 * Author  : RandySun
 * Date    : 2017-08-13  16:23
 * Comment :
 */
public class ProducerDemo {

    public static void main(String[] args){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", Constant.KAFKA_SERVERS);
        //“all”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
        properties.put("acks", "1");
        //如果请求失败，生产者也会自动重试，即使设置成０ the producer can automatically retry.
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        //默认立即发送，这里这是延时毫秒数
        //properties.put("linger.ms", 1);
        //生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
        //properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(properties);
            for (int i = 0; i < 100; i++) {
                String msg = "Message " + i;
                //producer.send(new ProducerRecord<>("HelloWorld", msg));
                producer.send(new ProducerRecord<>("HelloWorld", i % 4, String.valueOf(i), msg),
                    (recordMetadata, e) -> {
                        if(e != null){
                            e.printStackTrace();
                        }else {
                            System.out.println("message send to partition " + recordMetadata.partition() + ", offset: " + recordMetadata.offset());
                        }
                    }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }
}
