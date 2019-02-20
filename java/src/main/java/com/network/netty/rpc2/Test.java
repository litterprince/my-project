package com.network.netty.rpc2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        RpcClient client = RpcClient.getInstance("127.0.0.1", 9999);
                        System.out.println(""+ client.getResponse("hello".getBytes()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
