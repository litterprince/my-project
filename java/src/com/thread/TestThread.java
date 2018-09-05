package com.thread;

public class TestThread {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        new Thread(t1, "线程1").start();
        new Thread(t1, "线程2").start();

        MyRunnable r1 = new MyRunnable();
        new Thread(r1, "线程1").start();
        new Thread(r1,"线程2").start();
    }

    static class MyThread extends Thread {
        private int ticket = 10;

        @Override
        public void run(){
            for(int i=0;i<10;i++){
                synchronized(this){
                    if(this.ticket > 0){
                        System.out.println(Thread.currentThread().getName()+"买票-->"+this.ticket--);
                        try{
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class MyRunnable implements Runnable {
        private int ticked = 10;

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                synchronized(this){
                    if(this.ticked > 0){
                        System.out.println(Thread.currentThread().getName()+"买票-->"+this.ticked--);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}