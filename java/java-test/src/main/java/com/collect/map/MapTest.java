package com.collect.map;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapTest {
    private int count;
    private ReadWriteHashMap<String, String> map = new ReadWriteHashMap<String, String>();
    Map<String, String> map1 = new ConcurrentHashMap<String, String>();

    public MapTest(int count) {
        this.count = count;
    }

    public static void main(String[] args) {
        MapTest test = new MapTest(1000000);
        test.testPut();
        test.testGet();

        test.testConMapPut();
        test.testConMapGet();

    }

    private void testPut() {
        Long oldTime = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            map.put(String.valueOf(i), UUID.randomUUID().toString());
        }

        long cost = System.currentTimeMillis() - oldTime;
        System.out.println("readWriteHashMap write cost:" + cost);
    }

    private void testGet() {
        long oldTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            map.get(String.valueOf(i));
        }
        long cost = System.currentTimeMillis() - oldTime;
        System.out.println("readWriteHashMap read cost:" + cost);
    }

    private void testConMapPut() {
        Long oldTime = System.currentTimeMillis();
        Map<String, String> map1 = new ConcurrentHashMap<String, String>();

        for (int i = 0; i < count; i++) {
            map.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        }

        long cost = System.currentTimeMillis() - oldTime;
        System.out.println("concurrentHashMap write cost:" + cost);
    }

    private void testConMapGet() {
        Long oldTime = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            map1.get(String.valueOf(i));
        }

        long cost = System.currentTimeMillis() - oldTime;
        System.out.println("concurrentHashMap read cost:" + cost);
    }

    public class ReadWriteHashMap<K, V> {
        private Map<K, V> mapContainer;

        private ReadWriteLock lock = new ReentrantReadWriteLock();

        private Lock readLock = lock.readLock();

        private Lock writeLock = lock.writeLock();

        public static final int count = 1000000;

        public ReadWriteHashMap() {
            mapContainer = new HashMap<K, V>();
        }

        public V put(K key, V value) {
            writeLock.lock();
            try {
                return mapContainer.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

        public V get(K key) {
            readLock.lock();
            try {
                return mapContainer.get(key);
            } finally {
                readLock.unlock();
            }
        }
    }
}
