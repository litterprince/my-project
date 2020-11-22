package com.thread.lock.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ConditionDemo1 {

    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo1 condition = new ConditionDemo1();
        Producer producer = condition.new Producer();
        Consumer consumer = condition.new Consumer();

        int consumerCount = 10;
        ThreadPoolExecutor consumerPool = new ThreadPoolExecutor(consumerCount, consumerCount * 2, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("consumer-poool-%d").build());
        for (int i = 0; i < consumerCount; i++) {
            consumerPool.execute(consumer);
        }

        int produceCount = 2;
        ThreadPoolExecutor producePool = new ThreadPoolExecutor(produceCount, produceCount, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("consumer-poool-%d").build());
        for (int i = 0; i < produceCount; i++) {
            producePool.execute(producer);
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "consume我在等一个新信号");
                condition.await();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "consume拿到一个信号");
            }

        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "produce我拿到锁");
                condition.signalAll();
                System.out.println(Thread.currentThread().getName() + "produce我发出了一个信号：");
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "produce我释放锁");
            }
        }
    }
}
