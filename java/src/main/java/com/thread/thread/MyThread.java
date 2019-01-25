package main.java.com.thread.thread;

public class MyThread {

    public static void main(String[] args) {
        // example one
        MyRunnable runnable = new MyRunnable();
        for (int i = 0; i < 3; i++) {
            new Thread(runnable).start();
        }

        //example two
        for (int i = 0; i < 3; i++) {
            new Thread(new MyRunnable()).start();
        }

        //example three
        new Thread(new Runnable() {
            private int num;
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println("task "+ num++ +",thread is:"+Thread.currentThread().getName());
                }
            }
        }).start();
    }

    static class MyRunnable implements Runnable{
        private int num;
        @Override
        public void run() {
            System.out.println("task "+ num++ +",thread is:"+Thread.currentThread().getName());
        }
    }
}
