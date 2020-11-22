package com.thread.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/10.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class DaemonThreadTest {

    public static void main(String[] args) {
        //
        /*Thread mainThread = new Thread(new Runnable(){
            @Override
            public void run()
            {
                Thread childThread = new Thread(new ChildRunnable());
                childThread.setDaemon(true);//childThread.setDaemon(false);
                childThread.start();
                System.out.println("I'm main thread...");
            }
        });
        mainThread.start();*/

        // 线程池会将守护线程转换为用户线程
        ExecutorService service = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());
        service.execute(new ChildThread());
    }

    static class ChildRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println("I'm child thread..");
                System.out.println("" + Thread.currentThread().isDaemon());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ChildThread extends Thread {

        ChildThread() {
            super.setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("I'm child thread..");
                System.out.println("" + Thread.currentThread().isDaemon());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
