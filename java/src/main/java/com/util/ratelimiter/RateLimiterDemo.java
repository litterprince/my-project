package com.util.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RateLimiterDemo {
    private static RateLimiter limiter = RateLimiter.create(5);

    public static void exec() {
        limiter.acquire(1);
        try {
            // 处理核心逻辑
            TimeUnit.SECONDS.sleep(1);
            System.out.println("--" + System.currentTimeMillis() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    exec();
                    System.out.println(Thread.currentThread().getName()+" finish");
                }
            }).run();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}