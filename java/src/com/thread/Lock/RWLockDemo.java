package com.thread.Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockDemo {
    private Map<String, ReadWriteLock> clientLock = new HashMap<String, ReadWriteLock>();
    private Map<String, Object> results = new ConcurrentHashMap<String, Object>();

    public void setResult(String qualifiedName, Object result){
        if(!clientLock.containsKey(qualifiedName)){
            clientLock.put(qualifiedName, new ReentrantReadWriteLock());
        }
        clientLock.get(qualifiedName).writeLock().lock();
        try{
            results.put(qualifiedName, result);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            clientLock.get(qualifiedName).writeLock().unlock();
        }
    }

    public Object getResult(String qualifiedName) throws Exception {
        Object result = null;
        clientLock.get(qualifiedName).readLock().lock();
        try {
            if(results.containsKey(qualifiedName)){
                return results.get(qualifiedName);
            }
            throw new Exception("this qualifiedName is not exist!");
        } catch (Exception e){
            throw e;
        } finally {
            clientLock.get(qualifiedName).readLock().unlock();
        }
    }

    public void removeResult(String qualifiedName){
        clientLock.get(qualifiedName).writeLock().lock();
        try {
            if(results.containsKey(qualifiedName)){
                results.remove(qualifiedName);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            clientLock.get(qualifiedName).writeLock().unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        String name = "jeff";
        RWLockDemo lockDemo = new RWLockDemo();
        lockDemo.setResult(name, "hello");
        System.out.println(lockDemo.getResult(name));
        lockDemo.removeResult(name);
    }
}