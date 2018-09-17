package com.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor {
    static class Executor{
        private ExecutorService executor;

        public Executor(){
            executor = Executors.newFixedThreadPool(3);
        }

        public void start(){
            executor.execute(new MyRunnable());
        }
    }

    static class MyRunnable implements java.lang.Runnable{
        private int num;

        @Override
        public void run() {
            while (true){
                System.out.println("task "+ num++);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
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
