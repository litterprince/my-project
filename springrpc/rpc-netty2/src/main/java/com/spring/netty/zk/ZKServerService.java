package com.spring.netty.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class ZKServerService {
    private ZooKeeper zooKeeper;

    public ZKServerService(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void createServerService() throws KeeperException, InterruptedException {

    }

    public List<String> getAllServiceIP(String serviceName) throws KeeperException, InterruptedException {
        return null;
    }

    public void initZNode() throws KeeperException, InterruptedException {

    }
}
