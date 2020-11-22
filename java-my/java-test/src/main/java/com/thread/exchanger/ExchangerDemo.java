package com.thread.exchanger;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ExchangerDemo {

    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());

        final Exchanger exchanger = new Exchanger();
        executor.execute(new Runnable() {

            String data = "克拉克森，小拉里南斯";

            @Override
            public void run() {
                nbaTrade(data, exchanger);
            }
        });

        executor.execute(new Runnable() {

            String data = "格里芬";

            @Override
            public void run() {
                nbaTrade(data, exchanger);
            }
        });

        executor.shutdown();
    }

    private static void nbaTrade(String data1, Exchanger exchanger) {
        try {
            System.out.println(Thread.currentThread().getName() + "在交易截止之前把 " + data1 + " 交易出去");
            Thread.sleep((long) (Math.random() * 1000));

            String data2 = (String) exchanger.exchange(data1);
            System.out.println(Thread.currentThread().getName() + "交易得到" + data2);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
