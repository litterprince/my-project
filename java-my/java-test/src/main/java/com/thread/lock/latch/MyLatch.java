package com.thread.lock.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class MyLatch {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());
        threadPoolExecutor.execute(new MyRunnable(latch));
        threadPoolExecutor.execute(new MyRunnable(latch));

        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕,继续执行主线程");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyRunnable implements Runnable {

        MyRunnable(CountDownLatch latch) {
            this.latch = latch;
        }

        private CountDownLatch latch;

        @Override
        public void run() {
            try {
                System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
                Thread.sleep(3000);
                System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
                latch.countDown();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
