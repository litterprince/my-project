package com.thread;

import java.util.concurrent.*;

public class TestThreadPool {
    public static void main(String[] args) {
        Service1 myTask = new Service1(0);
        myTask.init();

        Service2 myTask2 = new Service2();
        myTask2.init();
    }

    static class Service1 {
        private int taskNum;
        private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5));

        public Service1(int num) {
            this.taskNum = num;
        }

        public void init(){
            executor.execute(() -> {
                while(true){
                    System.out.println("正在执行task " + taskNum);
                    try {
                        Thread.currentThread().sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(taskNum == 10) break;
                    System.out.println("task " + taskNum++ +"执行完毕");
                }
            });
        }
    }

    static class Service2 {
        private boolean running = false;
        private int num = 0 ;
        private ExecutorService executor = Executors.newFixedThreadPool(3);

        public Service2(){
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
}
