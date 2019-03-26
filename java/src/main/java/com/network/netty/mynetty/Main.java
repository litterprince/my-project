package com.network.netty.mynetty;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        ThreadHandle handle = new ThreadHandle(boss, worker);
        handle.bind(new InetSocketAddress(9000));
        System.out.println("start...");
    }
}
