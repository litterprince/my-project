package com.util.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiterDemo {
    private RateLimiter limiter = RateLimiter.create(0.1);

    public void exec() {
        System.out.println(Thread.currentThread().getName()+
                " start and 等待了：" + (int)limiter.acquire());
        try {
            // 处理核心逻辑
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName()+ " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exec1() {
        //判断能否在1秒内得到令牌，如果不能则立即返回false，不会阻塞程序
        if (!limiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            System.out.println("短期无法获取令牌，真不幸，排队也瞎排");
            return ;
        }
        try {
            // 处理核心逻辑
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName()+ " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        final RateLimiterDemo l = new RateLimiterDemo();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    l.exec();
                }
            });
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}