package com.thread.lock.synch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 现象：出现IllegalMonitorStateException错误
 * 原因：对象锁持有线程只有把锁释放后，另一个线程才能调用notify, wait等方法，否则会报以上错误
 * 解决：增加synchronized同步控制
 * 注意：基础类型作为锁时值（对象变化了）改变也会引起以上报错；解决：使用对象（修改时只变化属性对象不变）作为锁
 */
public class SimpleExample implements Runnable {

    private final Object lock;

    public static void main(String[] args) {
        Object lock = "lock";
        ExecutorService pool = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());
        pool.execute(new SimpleExample(lock));
        pool.execute(new SimpleExample(lock));
    }

    private SimpleExample(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            lock.notifyAll();
            System.out.println(Thread.currentThread().getName() + " get lock");
            try {
                lock.wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notifyAll();
            System.out.println(Thread.currentThread().getName() + " finish");
        }
    }
}
