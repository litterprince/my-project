package com.spring.zk.lock;

public class Main {
    private static int n = 500;

    private static void secSkill() {
        System.out.println(--n);
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                DistributedLock lock = null;
                try {
                    lock = new DistributedLock("127.0.0.1:2181", "test1");
                    lock.lock();
                    secSkill();
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }
}
