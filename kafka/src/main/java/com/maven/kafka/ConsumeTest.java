package com.maven.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class ConsumeTest
{
    private static final Integer NUM_THREADS = 3;
    private static Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap;
    private static ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    public static void main(String[] args ) {
        connect();
    }

    public static void connect(){
        Properties props = new Properties();
        props.put("zookeeper.connect", Constant.ZK_SERVER_LIST);
        props.put("zookeeper.connection.timeout.ms", "1000000");
        props.put("auto.commit.interval.ms", "10000");
        props.put("num.consumer.fetchers", "4");
        props.put("rebalance.max.retries", "5");
        props.put("rebalance.backoff.ms", "6000");
        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("group.id", Constant.KAFKA_GROUP_ID);

        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(Constant.KAFKA_TOPIC, NUM_THREADS);
        consumerMap = consumer.createMessageStreams(topicCountMap);

        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(Constant.KAFKA_TOPIC);
        for (final KafkaStream<byte[], byte[]> stream : streams) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        ConsumerIterator<byte[], byte[]> streamIterator = stream.iterator();
                        while (streamIterator.hasNext()) {
                            MessageAndMetadata<byte[], byte[]> message = streamIterator.next();
                            String row = new String(message.message());
                            try {
                                JSONObject km = JSON.parseObject(row);
                                System.out.println(km);
                            } catch (Throwable e) {
                                System.out.println(e);
                            }
                        }
                    } catch (Throwable e) {
                        System.out.println(e);
                    }
                }
            });
        }
    }
}
