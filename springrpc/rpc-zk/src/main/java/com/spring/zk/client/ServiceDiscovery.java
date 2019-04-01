package com.spring.zk.client;

import com.spring.zk.common.Constant;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class ServiceDiscovery {
    private CountDownLatch latch = new CountDownLatch(1);

    // TODO: 问题，dataList用于存放服务的Ip，服务粒度以服务ip划分而不是服务方法，不够灵活
    private volatile List<String> dataList = new ArrayList<>();

    private String registryAddress;

    // TODO: 学习，实现服务发现功能
    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;

        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }

    public String discover() {
        String data = null;
        try {
            int size = dataList.size();
            if (size > 0) {
                if (size == 1) {
                    data = dataList.get(0);
                } else {
                    data = dataList.get(ThreadLocalRandom.current().nextInt(size));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    watchNode(zk);
                }
            });

            List<String> dataList = new ArrayList<>();
            for (String node : nodeList) {
                byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(bytes));
            }

            System.out.println("node data: " + dataList);
            this.dataList = dataList;
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
