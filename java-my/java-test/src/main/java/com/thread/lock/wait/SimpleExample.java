package com.thread.lock.wait;

/**
 * 现象：出现IllegalMonitorStateException错误
 * 原因：对象锁持有线程只有把锁释放后，另一个线程才能调用notify, wait等方法，否则会报以上错误
 * 解决：增加synchronized同步控制
 * 注意：基础类型作为锁时值（对象变化了）改变也会引起以上报错；解决：使用对象（修改时只变化属性对象不变）作为锁
 */
public class SimpleExample implements Runnable {
    private Object lock;

    public static void main(String[] args){
        Object lock = "lock";
        new Thread(new SimpleExample(lock), "1").start();
        new Thread(new SimpleExample(lock), "2").start();
    }

    public SimpleExample(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            lock.notifyAll();
            System.out.println(Thread.currentThread().getName() + " get lock");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notifyAll();
            System.out.println(Thread.currentThread().getName() + " finish");
        }
    }
}
