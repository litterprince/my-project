package com.thread.lock.wait;

import java.io.IOException;

/**
 * 关键：
 */
public class NumberPrint implements Runnable {
    private int number;
    public byte[] res;
    public static int count = 5;

    public NumberPrint(int number, byte[] res) {
        this.number = number;
        this.res = res;
    }

    @Override
    public void run() {
        synchronized (res){
            while (count-- > 0){
                res.notifyAll();

                System.out.println(number);
                System.out.println("线程 " +Thread.currentThread().getName() + " 获得锁，执行wait()方法阻塞并释放锁");
                try {
                    res.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        byte[] res = new byte[1];
        new Thread(new NumberPrint(1, res), "1").start();
        new Thread(new NumberPrint(2, res), "2").start();
    }
}
