package com.util.ratelimiter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CountRateLimiterDemo1 {
    private AtomicInteger count = new AtomicInteger(0);

    public void exec() {
        if (count.get() >= 5) {
            System.out.println("请求用户过多，请稍后在试！"+System.currentTimeMillis()/1000);
        } else {
            count.incrementAndGet();
            try {
                //处理核心逻辑
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+ " end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                count.decrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        final CountRateLimiterDemo1 l = new CountRateLimiterDemo1();
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    l.exec();
                }
            });
        }

        System.in.read();
    }
}
