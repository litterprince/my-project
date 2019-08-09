package com.thread.lock.cyclicbarrier;

import java.util.concurrent.*;

public class CyclicBarrierTest {

    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Worker("james", cyclicBarrier));
        executorService.execute(new Worker("jeff", cyclicBarrier));
        executorService.execute(new Worker("tom", cyclicBarrier));

        executorService.shutdown();
    }


    static class Worker implements Runnable{
        private String name;
        private CyclicBarrier cyclicBarrier;

        Worker(String name, CyclicBarrier cyclicBarrier){
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("worker "+name+" start work");
            try {
                Thread.sleep(1000);
                System.out.println("worker "+name+" finish work");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("all workers finish their works,so "+name+" gonna do another work");
        }
    }
}
