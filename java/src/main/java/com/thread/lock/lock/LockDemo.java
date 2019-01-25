package main.java.com.thread.lock.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Lock lock = new ReentrantLock();    //注意这个地方

    public static void main(String[] args)  {
        testLock();
        //testTryLock();
    }

    public static void testLock(){
        final LockDemo test = new LockDemo();
        new Thread(){
            public void run() {
                test.insert(Thread.currentThread());

            };
        }.start();
        new Thread(){
            public void run() {
                test.insert(Thread.currentThread());
            };
        }.start();
    }

    private void insert(Thread thread) {
        lock.lock();
        try {
            System.out.println(thread.getName()+"得到了锁");
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally {
            System.out.println(thread.getName()+"释放了锁");
            lock.unlock();
        }
    }

    public static void testTryLock(){
        final LockDemo test = new LockDemo();
        new Thread(){
            public void run() {
                test.insert2(Thread.currentThread());
            };
        }.start();
        new Thread() {
            public void run() {
                test.insert2(Thread.currentThread());
            };
        }.start();
    }

    private void insert2(Thread thread){
        if(lock.tryLock()) {
            try {
                System.out.println(thread.getName()+"得到了锁");
                for(int i=0;i<5;i++) {
                    arrayList.add(i);
                }
                Thread.sleep(3000);
            } catch (Exception e) {
                // TODO: handle exception
            }finally {
                System.out.println(thread.getName()+"释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName()+"获取锁失败");
        }
    }
}
