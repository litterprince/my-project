package com.thread.threadpool.simple;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {
    public static void main(String[] args) {
        MyExecutor myExecutor = new MyExecutor();
        myExecutor.init();
    }

    static class MyExecutor {
        private int num = 0;
        private volatile boolean running = false;
        private ExecutorService executor = Executors.newFixedThreadPool(3);

        MyExecutor() {
            //add hook
            Runtime.getRuntime().addShutdownHook(new Thread(this::destroy));
        }

        public void init() {
            System.out.println("begin!!!");
            running = true;
            executor.execute(() -> {
                while (!Thread.interrupted() && running) {
                    System.out.println("task" + num + "开始...");
                    System.out.println("task" + num++ + "结束...");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        void destroy() {
            System.out.println("shutdown!!!");
            running = false;
            executor.shutdown();
        }
    }
}
