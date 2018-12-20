package com.thread.threadpool.simple;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {
    public static void main(String[] args) {
        MyExecutor myExecutor = new MyExecutor();
        myExecutor.init();
    }

    static class MyExecutor {
        private boolean running = false;
        private int num = 0 ;
        private ExecutorService executor = Executors.newFixedThreadPool(3);

        public MyExecutor(){
            //add hook
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    destroy();
                }
            }));
        }

        public void init(){
            System.out.println("begin!!!");
            running = true;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.interrupted() && running) {
                        System.out.println("task"+ num +"开始...");
                        System.out.println("task"+ num++ +"结束...");

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        public void destroy(){
            System.out.println("shutdown!!!");
            running = false;
            executor.shutdown();
        }
    }
}
