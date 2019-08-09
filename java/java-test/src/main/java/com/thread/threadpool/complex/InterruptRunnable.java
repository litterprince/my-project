package com.thread.threadpool.complex;

import java.util.concurrent.CountDownLatch;

public class InterruptRunnable implements Runnable {
    private CountDownLatch beginLatch;
    private ConcurrentTaskExecutor concurrentTaskExecutor;

    public InterruptRunnable(ConcurrentTaskExecutor currConcurrentTaskExecutor, CountDownLatch beginLatch) {
        this.beginLatch = beginLatch;
        this.concurrentTaskExecutor = currConcurrentTaskExecutor;
    }

    @Override
    public void run() {
        try {
            beginLatch.await();
            long millis = (long) (Math.random() * 1 * 1000);
            System.out.println(String.format("System need sleep %s millis", millis));
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        concurrentTaskExecutor.setCanceled(true);
    }
}