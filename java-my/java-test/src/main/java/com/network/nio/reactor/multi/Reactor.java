package com.network.nio.reactor.multi;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Reactor {
    public static void main(String[] args) {
        new Reactor();
        System.out.println("start...");
    }

    public Reactor() {
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        Group group = new Group(boss, worker);
        group.bind(new InetSocketAddress(9000));
        System.out.println("start...");
    }
}
