package com.thread.lock.lock;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.Setter;

public class ConditionDemo2 {

    @Setter
    private volatile boolean stopRunning;

    private int queueSize = 10;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);

    public static void main(String[] args) {
        ConditionDemo2 demo = new ConditionDemo2();

        int producerCount = 2;
        ThreadPoolExecutor producerThreadPoolExecutor = new ThreadPoolExecutor(producerCount, producerCount, 0,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("producer-threadpool-%d").build());
        for (int i = 0; i < producerCount; i++) {
            producerThreadPoolExecutor.execute(demo.new Producer());
        }
        producerThreadPoolExecutor.shutdown();

        int consumerCount = 10;
        ThreadPoolExecutor consumerThreadPoolExecutor = new ThreadPoolExecutor(consumerCount, consumerCount * 2, 1,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("consumer-threadpool-%d").build());
        for (int i = 0; i < consumerCount; i++) {
            consumerThreadPoolExecutor.execute(demo.new Consumer());
        }
        consumerThreadPoolExecutor.shutdown();

        System.out.println(Thread.currentThread().getName() + ": stop running!");
        demo.stopRunning = true;
    }

    class Consumer implements Runnable {

        Consumer() {

        }

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (!stopRunning) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "-consumer: 加锁");
                    while (queue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + "-consumer: queue is empty! wait...");
                        try {
                            notEmpty.await();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 执行消费逻辑
                    queue.poll();
                    notFull.signal();
                    System.out.println(
                            Thread.currentThread().getName() + "-consumer: consumer one item, please produce one!");
                }
                finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + "-consumer: 释放锁");
                }
            }
        }
    }

    class Producer implements Runnable {

        Producer() {
        }

        @Override
        public void run() {
            while (!stopRunning) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "-producer: 加锁");
                    while (queue.size() == queueSize) {
                        System.out.println(Thread.currentThread().getName() + "-producer: queue is full! wait...");
                        try {
                            notFull.await();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 生产逻辑
                    queue.offer(new Random(10).nextInt());
                    notEmpty.signal();
                    System.out.println(Thread.currentThread().getName() + "-producer: add one item into queue!");
                }
                finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + "-producer: 释放锁");
                }
            }
        }
    }

}
