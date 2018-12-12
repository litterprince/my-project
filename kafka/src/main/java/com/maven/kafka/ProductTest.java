package com.maven.kafka;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/12.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class ProductTest {
    private static Producer<String, String> producer;
    private static final int NUM_THREADS = 5;
    private static ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
    private static Random random = new Random();

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", Constant.KAFKA_SERVER_LIST);
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "0");
        properties.put("producer.type", "async");
        producer = new Producer<>(new ProducerConfig(properties));

        for(;;) {
            for (int i = 0; i < 1; i++) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        KeyedMessage<String, String> keyedMessage;
                        keyedMessage = new KeyedMessage<>(Constant.KAFKA_TOPIC, getMsgData(random.nextInt(9)));
                        producer.send(keyedMessage);
                        System.out.println("发送成功！");
                    }
                });
            }

            try {
                Thread.sleep(2000);
                System.in.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getMsgData(int sleep){
        try {
            Thread.sleep(sleep*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] ipList = new String[]{"61.139.2.21", "218.88.212.31", "61.139.2.69", "172.20.4.125", "171.104.0.55", "59.49.129.35", "119.57.71.6", "219.78.113.243", "121.56.0.23"};
        //String[] ipList = new String[]{"6.139.2.69"};
        Date time = new Date();
        Long timeStamp = time.getTime();
        String pageId = UUID.randomUUID().toString().replace("-", "");

        JSONArray resDataList = new JSONArray();
        JSONObject resData = new JSONObject();
        resData.put("resourceUrl", getRandomUrl());
        resData.put("ip", ipList[random.nextInt(ipList.length)]);
        resData.put("isLoadSuccess", random.nextBoolean());
        resData.put("isHijacked", random.nextBoolean());
        resData.put("errorMsg", getRandomString(1000));
        resData.put("fileName", getRandomString(10));
        resData.put("deleteMD5", getRandomString(10));
        resData.put("H5Content", getRandomString(1000));
        resData.put("H5InIframe", getRandomString(9));
        resDataList.add(resData);

        JSONObject resData1 = new JSONObject();
        resData1.put("resourceUrl", getRandomUrl());
        resData1.put("ip", ipList[random.nextInt(ipList.length)]);
        resData1.put("isLoadSuccess", random.nextBoolean());
        resData1.put("isHijacked", random.nextBoolean());
        resData1.put("errorMsg", getRandomString(1000));
        resData1.put("fileName", getRandomString(9));
        resData1.put("deleteMD5", getRandomString(10));
        resData1.put("H5Content", getRandomString(1000));
        resData1.put("H5InIframe", getRandomString(10));
        resDataList.add(resData1);

        JSONObject pageData = new JSONObject();
        pageData.put("id", pageId);
        pageData.put("uid", getRandomString(10));
        pageData.put("url", getRandomUrl());
        pageData.put("timeStamp", timeStamp);
        pageData.put("ua", getRandomString(10));
        pageData.put("device", random.nextInt(2));
        pageData.put("zipName", getRandomString(10) + ".zip");
        pageData.put("resourcePages", resDataList);

        JSONObject data = new JSONObject();
        data.put("device", "");
        data.put("deviceId", "");
        data.put("appVersion", "");
        data.put("uid", "");
        data.put("userIP", ipList[random.nextInt(ipList.length)]);
        StringBuilder zipPath = new StringBuilder();
        data.put("zipPath", zipPath.append(getRandomString(random.nextInt(5))).append("/").append(getRandomString(random.nextInt(5))).append("/").append(getRandomString(random.nextInt(5))).append("/").toString());
        data.put("data", new BASE64Encoder().encode(pageData.toJSONString().getBytes()));

        return data.toJSONString();
    }

    private static String getRandomString(int length){
        if(length <= 3) length = 3;
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    private static String getRandomUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(getRandomString(random.nextInt(5))).append("/")
                .append(getRandomString(random.nextInt(5))).append("/")
                .append(getRandomString(random.nextInt(5))).append("/")
                .append(getRandomString(random.nextInt(5))).append("/");
        return sb.toString();
    }
}
