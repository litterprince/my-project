package com.spring.zk.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BaseZookeeper implements Watcher {
    private ZooKeeper zookeeper;
    private static final int SESSION_TIME_OUT = 2000;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        BaseZookeeper zookeeper = new BaseZookeeper();
        zookeeper.connectZookeeper("127.0.0.1:2181");

        List<String> children = zookeeper.getChildren("/");
        System.out.println(children);
    }

    // connect
    public void connectZookeeper(String host) throws Exception{
        zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
        countDownLatch.await();
        System.out.println("zookeeper connection success");
    }

    // watch
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("Watch received event");
            countDownLatch.countDown();
        }
    }

    // 创建节点
    public String createNode(String path,String data) throws Exception{
        return this.zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    // 获取节点上面的数据
    public String getData(String path) throws KeeperException, InterruptedException{
        byte[] data = zookeeper.getData(path, false, null);
        if (data == null) {
            return "";
        }
        return new String(data);
    }

    // 设置节点信息
    public Stat setData(String path,String data) throws KeeperException, InterruptedException{
        Stat stat = zookeeper.setData(path, data.getBytes(), -1);
        return stat;
    }

    // 删除节点
    public void deleteNode(String path) throws InterruptedException, KeeperException{
        zookeeper.delete(path, -1);
    }

    // 获取创建时间
    public String getCTime(String path) throws KeeperException, InterruptedException{
        Stat stat = zookeeper.exists(path, false);
        return String.valueOf(stat.getCtime());
    }

    // 获取路径下所有子节点
    public List<String> getChildren(String path) throws KeeperException, InterruptedException{
        List<String> children = zookeeper.getChildren(path, false);
        return children;
    }

    // 获取某个路径下孩子的数量
    public Integer getChildrenNum(String path) throws KeeperException, InterruptedException{
        int childenNum = zookeeper.getChildren(path, false).size();
        return childenNum;
    }

    // close
    public void closeConnection() throws InterruptedException{
        if (zookeeper != null) {
            zookeeper.close();
        }
    }
}
