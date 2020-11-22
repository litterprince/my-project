package com.thread.lock.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CasTest {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static int value = 0;

    public static void main(String[] args) throws InterruptedException {
        atomicIntegerTest();
        System.out.println("atomicInteger最终结果是" + atomicInteger.get());

        IntValueIncrementTest();
        System.out.println("value最终结果是" + value);
    }

    private static void atomicIntegerTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    for (int j = 0; j < 4; j++) {
                        atomicInteger.getAndIncrement();
                        // System.out.println(atomicInteger.getAndIncrement());
                    }
                }
            });
        }
        executorService.shutdown();
    }

    private static void IntValueIncrementTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    for (int j = 0; j < 4; j++) {
                        value++;
                        // System.out.println(value++);
                    }
                }
            });
        }
        executorService.shutdown();
    }
}
