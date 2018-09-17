package com.thread.thread;

public class MyThread {

    public static void main(String[] args) {
         MyRunnable runnable = new MyRunnable();
         for(int i=0;i< 3;i++){
             new Thread(runnable).start();
             //new Thread(new MyRunnable()).start();
         }
    }

    static class MyRunnable implements Runnable{
        private int num;
        @Override
        public void run() {
            System.out.println("task "+ num++);
        }
    }
}
