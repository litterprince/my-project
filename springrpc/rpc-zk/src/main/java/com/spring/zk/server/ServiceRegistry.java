package com.spring.zk.server;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ServiceRegistry {
    private CountDownLatch latch = new CountDownLatch(1);
    private String registryAddress;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    // TODO: 实现注册
    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }

    private ZooKeeper connectServer() {
        return null;
    }

    private void createNode(ZooKeeper zk, String data) {

    }
}
