package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThread {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executor.execute(new MyRunnable());
        }
    }

    static class MyRunnable implements Runnable {

        private int num;

        @Override
        public void run() {
            System.out.println("task " + num++ + ",thread is:" + Thread.currentThread().getName());
        }
    }
}
