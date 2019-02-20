package com.thread.threadpool.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/14.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class ScheduleTest {
    private static final int POOL_SIZE = 3;
    private static final int SCHEDULE_MINUTES = 1;

    public static void main(String[] args) {
        testFixedRate();
        testFixedDelay();
    }


    private static void testFixedRate() {
        ScheduledExecutorService alarmExecutor = Executors.newScheduledThreadPool(POOL_SIZE);
        ScheduledFuture<?> alarmFuture = alarmExecutor.scheduleAtFixedRate(new MyRunnable("fixedRate"), 0, SCHEDULE_MINUTES, TimeUnit.MINUTES);
    }

    private static void testFixedDelay() {
        ScheduledExecutorService alarmUserFlushExecutor = Executors.newScheduledThreadPool(POOL_SIZE);
        ScheduledFuture<?> alarmUserFuture = alarmUserFlushExecutor.scheduleWithFixedDelay(new MyRunnable("fixedDelay"), 0, SCHEDULE_MINUTES, TimeUnit.MINUTES);
    }


    static class MyRunnable implements Runnable{
        private String name;

        public MyRunnable(String name){
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + ": " + new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
