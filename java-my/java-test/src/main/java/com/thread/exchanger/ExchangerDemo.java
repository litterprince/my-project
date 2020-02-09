package com.thread.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
