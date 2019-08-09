package com.util.ratelimiter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CountRateLimiterDemo2 {
    private static Semaphore semaphore = new Semaphore(2);

    public static void exec() {
        if(semaphore.getQueueLength() > 5){
            System.out.println("当前等待排队的任务数大于5，请稍候再试...");
        }
        try {
            semaphore.acquire();
            // 处理核心逻辑
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName()+" end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    exec();
                }
            });
        }

        System.in.read();
    }
}
