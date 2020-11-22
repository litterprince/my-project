package com.thread.lock.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockDemo {

    private final static String NAME = "jeff";

    /**
     * 锁
     * key：资源唯一key，value：锁
     */
    private Map<String, ReadWriteLock> clientLock = new HashMap<>();

    /**
     * 结果
     * key：资源唯一key，value：值
     */
    private Map<String, Object> results = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        RWLockDemo lockDemo = new RWLockDemo();
        lockDemo.setResult();
        System.out.println(lockDemo.getResult());
        lockDemo.removeResult();
    }

    private void setResult() {
        // 判断该资源是否有锁
        if (!clientLock.containsKey(RWLockDemo.NAME)) {
            clientLock.put(RWLockDemo.NAME, new ReentrantReadWriteLock());
        }
        // 上写锁
        clientLock.get(RWLockDemo.NAME).writeLock().lock();
        try {
            // 执行逻辑
            results.put(RWLockDemo.NAME, "hello");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // 释放写锁
            clientLock.get(RWLockDemo.NAME).writeLock().unlock();
        }
    }

    private Object getResult() {
        clientLock.get(RWLockDemo.NAME).readLock().lock();
        try {
            if (results.containsKey(RWLockDemo.NAME)) {
                return results.get(RWLockDemo.NAME);
            }
            // 获取不到的逻辑
            return null;
        }
        finally {
            clientLock.get(RWLockDemo.NAME).readLock().unlock();
        }
    }

    private void removeResult() {
        clientLock.get(RWLockDemo.NAME).writeLock().lock();
        try {
            results.remove(RWLockDemo.NAME);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            clientLock.get(RWLockDemo.NAME).writeLock().unlock();
        }
    }
}
