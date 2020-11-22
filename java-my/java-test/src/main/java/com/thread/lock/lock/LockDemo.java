package com.thread.lock.lock;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class LockDemo {

    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    private ExecutorService threadPool = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        testLock();
        // testTryLock();
    }

    private static void testLock() {
        final LockDemo test = new LockDemo();
        for (int i = 0; i < 2; i++) {
            test.threadPool.execute(() -> {
                test.insert(Thread.currentThread());
            });
        }
    }

    private void insert(Thread thread) {
        lock.lock();
        try {
            System.out.println(thread.getName() + "得到了锁");
            for (int i = 0; i < 5; i++) {
                arrayList.add(i);
            }
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        finally {
            lock.unlock();
            System.out.println(thread.getName() + "释放了锁");
        }
    }

    public static void testTryLock() {
        final LockDemo test = new LockDemo();
        for (int i = 0; i < 2; i++) {
            test.threadPool.execute(() -> {
                test.insert2(Thread.currentThread());
            });
        }
    }

    private void insert2(Thread thread) {
        if (lock.tryLock()) {
            try {
                System.out.println(thread.getName() + "得到了锁");
                for (int i = 0; i < 5; i++) {
                    arrayList.add(i);
                }
                Thread.sleep(3000);
            }
            catch (Exception e) {
                // TODO: handle exception
            }
            finally {
                lock.unlock();
                System.out.println(thread.getName() + "释放了锁");
            }
        } else {
            System.out.println(thread.getName() + "获取锁失败");
        }
    }
}
