package com.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor {
    static class Executor{
        private ExecutorService executor;
        private int num;

        public Executor(){
            executor = Executors.newFixedThreadPool(3);
        }

        public void start(){
            executor.execute(() ->{
                while (true){
                    System.out.println("task "+ num++);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        Executor executor = new Executor();
        executor.start();
    }
}
