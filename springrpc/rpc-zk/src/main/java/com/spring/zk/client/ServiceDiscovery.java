package com.spring.zk.client;

import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServiceDiscovery {
    private CountDownLatch latch = new CountDownLatch(1);

    // TODO: 思考
    private volatile List<String> dataList = new ArrayList<>();

    private String registryAddress;

    // TODO: 实现服务发现功能
    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;

        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }

    public String discover() {
        return null;
    }

    private ZooKeeper connectServer() {
        return null;
    }

    private void watchNode(final ZooKeeper zk) {

    }
}
