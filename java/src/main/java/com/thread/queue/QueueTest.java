package main.java.com.thread.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/21.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class QueueTest {
    private static BlockingDeque<String> data = new LinkedBlockingDeque<>(10);
    private static final int THREAD_COUNT = 2;
    private static final int NUM = 4;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    private static AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(String[] args) {
        data.add("123");
        data.add("234");
        data.add("234");
        data.add("456");
        data.add("567");


        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(new Runnable() {
                int count = 0;
                List<String> list = new ArrayList<>();
                long start = System.currentTimeMillis();

                @Override
                public void run() {
                    while (!Thread.interrupted()) {
                        try {
                            System.out.println(Thread.currentThread().getName()+": start");
                            String str = data.take();
                            System.out.println(Thread.currentThread().getName()+": take");
                            list.add(str);
                            if (count >= NUM  || System.currentTimeMillis() - start > 5 * 1000) {
                                for(String s: list){
                                    System.out.println(Thread.currentThread().getName()+": "+s);
                                }
                                count = 0;
                                start = System.currentTimeMillis();
                            }
                            count++;
                            COUNTER.incrementAndGet();
                            System.out.println(Thread.currentThread().getName()+": COUNTER=" + COUNTER.get());
                            System.out.println(Thread.currentThread().getName()+": count=" + count);
                            if (COUNTER.get() == 4) {
                                Thread.sleep(1000);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
