package com.thread.lock.synch;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 交替打印数字
 */
public class NumberPrint implements Runnable {

    private Object lock;

    private static int count = 5;

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());
        threadPoolExecutor.execute(new NumberPrint());
        threadPoolExecutor.execute(new NumberPrint());
    }

    private NumberPrint() {
        lock = "lock";
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (count-- > 0) {
                lock.notifyAll();

                System.out.println(count);
                System.out.println("线程 " + Thread.currentThread().getName() + " 获得锁，执行wait()方法阻塞并释放锁");
                try {
                    lock.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
