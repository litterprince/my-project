package com.thread;

import java.util.concurrent.*;

public class TestThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyExecutor myTask2 = new MyExecutor();
        myTask2.init();

        /*MyExecutorFuture myExecutorFuture = new MyExecutorFuture();
        Object object = myExecutorFuture.getResult();
        System.out.println("object=" + object);
        myExecutorFuture.destroy();*/
    }

    static class MyExecutor {
        private boolean running = false;
        private int num = 0 ;
        private ExecutorService executor = Executors.newFixedThreadPool(3);

        public MyExecutor(){
            //add hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> destroy()));
        }

        public void init(){
            System.out.println("begin!!!");
            running = true;
            executor.execute(() -> {
                while (!Thread.interrupted() && running) {
                    System.out.println("task"+ num +"开始...");
                    System.out.println("task"+ num++ +"结束...");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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

    static class MyExecutorFuture{
        private ExecutorService executor = Executors.newFixedThreadPool(1);

        public MyExecutorFuture(){

        }

        public Object getResult() throws ExecutionException, InterruptedException {
            return this.submit().get();
        }

        public Future<Object> submit(){
            return executor.submit(() -> "Halo");
        }

        public void destroy(){
            executor.shutdown();
        }
    }
}
