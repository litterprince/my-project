package com.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor {

    static class Executor {

        private ExecutorService executor;

        Executor() {
            executor = Executors.newFixedThreadPool(3);
        }

        public void start() {
            executor.execute(new MyRunnable());
        }
    }

    static class MyRunnable implements Runnable {

        private int num;

        @Override
        public void run() {
            while (true) {
                System.out.println("task " + num++ + ",thread name:" + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Executor executor = new Executor();
        executor.start();
    }
}
