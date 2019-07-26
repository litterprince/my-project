package com.spring.zk.api;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TestZookeeper implements Watcher {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        TestZookeeper client = new TestZookeeper();
        ZooKeeper zk =  client.connect();
        client.createNode(zk);
    }

    private ZooKeeper connect() throws InterruptedException, IOException {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, this);
        System.out.println(zk.getState());// connecting
        Thread.sleep(2000);
        System.out.println(zk.getState());// connected
        return zk;
    }

    /**
     * CreateMode
     *    PERSISTENT : 持久节点
     *    PERSISTENT_SEQUENTIAL : 持久顺序节点
     *    EPHEMERAL : 临时节点
     *    EPHEMERAL_SEQUENTIAL : 临时顺序节点
     *
     * 无法在父节点不存在的情况下创建一个子节点
     * 一个节点已经存在了，那么创建同名节点的时候，会抛出NodeExistException异常（顺序节点永远不会抛出）
     * 临时节点不能有子节点,创建节点时如果给定的父节点是临时节点则会抛出NoChildrenForEphemeralsException
     * 创建节点同时，也可以在节点上设置Watcher 当删除节点或者setData时，将会触发Watcher
     */
    private void createNode(ZooKeeper zk) throws KeeperException, InterruptedException {
        /*
          同步创建一个持久节点,ACL为 world:anyone:cdrwa 等同于如下命令：
          create /node 123 world:anyone:cdrwa
         */
        zk.create("/node", "data1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        /*
          异步创建一个 临时的顺序节点，ACL为 ip:127.0.0.1:c 等同于如下命令：
          create /node2 123 ip:127.0.0.1:c
         */
        zk.create("/node2", "data2".getBytes(),
                Collections.singletonList(new ACL(ZooDefs.Perms.CREATE, new Id("ip", "127.0.0.1"))),
                CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("ctx:" + ctx);
                System.out.println("name:" + name);
            }
        }, "传给服务端的内容,会在异步回调时传回来");

        /*
          注意这里,线程睡眠20秒,因为是创建的临时节点,如果不睡眠,你不能使用命令在控制台看见创建的临时节点
         */
        Thread.sleep(20000);

        /*
          异步创建一个持久节点, ACL为 digest:wangsaichao:G2RdrM8e0u0f1vNCj/TI99ebRMw=:cdrwa,等同于如下命令：
          create /node3 123 digest:wangsaichao:G2RdrM8e0u0f1vNCj/TI99ebRMw=:cdrwa
         */
        zk.create("node3", "data3".getBytes(),
                Collections.singletonList(
                        new ACL(ZooDefs.Perms.ALL,
                        new Id("digest", "wangsaichao:G2RdrM8e0u0f1vNCj/TI99ebRMw="))),
                CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("ctx:" + ctx);
                System.out.println("name:" + name);
            }
        }, "传给服务端的内容,会在异步回调时传回来");

        /*
         * 注意这里,线程睡眠20秒,可以接收到watcher
         */
        Thread.sleep(20000);

        /*
          创建一个持久顺序定时节点，如果在10000毫秒内 未修改node，并且没有子节点,那么它将被删掉
         */
        zk.create("/node4", "123".getBytes(),
                Collections.singletonList(
                        new ACL(ZooDefs.Perms.ALL,
                        new Id("digest", "wangsaichao:G2RdrM8e0u0f1vNCj/TI99ebRMw="))),
                CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL, new AsyncCallback.Create2Callback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name, Stat stat) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("ctx:" + ctx);
                System.out.println("name:" + name);
                System.out.println("stat:" + stat);
            }
        }, "传给服务端的内容,会在异步回调时传回来", 10000);
        Thread.sleep(20000);
    }

    private void getNodeData(ZooKeeper zk) throws KeeperException, InterruptedException {
        /*
          每一步操作/node3都要先执行该语句
          因为上一步创建的node3 添加了 digest ACL 所以在获取该节点信息的时候,要先添加授权
         */
        zk.addAuthInfo("digest","wangsaichao:123456".getBytes());

        /*
          同步调用
          path 节点路径
          Watcher 监视
          Stat 节点统计信息
          添加授权之后同步获取节点信息，返回给定路径的节点的数据和统计信息
          如果调用成功,并且watcher参数不为空,则会在具有给定路径的节点上保留监视，当删除节点 或者 setData时候将会触发监视

         */
        byte[] data = zk.getData("/node3", new TestZookeeper(), new Stat());
        System.out.println(new String(data));

        /*
          注意这里,线程睡眠2秒,因为是创建的临时节点,如果不睡眠,你不能使用命令在控制台看见创建的临时节点
         */
        Thread.sleep(2000);

        /*
          异步调用
          path 节点路径
          watch true使用创建zookeeper时指定的默认watcher 如果为false则不设置监听
          DataCallback 异步通知
          ctx 回调上下文
         */
        zk.getData("/node3", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("ctx:" + ctx);
                System.out.println("data:" + new String(data));
                System.out.println("stat:" + stat);
            }
        }, "传给服务端的内容,会在异步回调时传回来");
        Thread.sleep(2000);

    }

    /**
     * setData节点有version这一参数，给定版本与节点的版本匹配，则设置给定路径的节点的数据，返回节点的统计信息
     * 如果给定版本是-1，则它匹配任何节点的版本
     * 如果不存在具有给定路径的节点，则将抛出NoNodeException
     * 如果给定版本与节点的版本不匹配，将抛出BadVersionException
     * 设置的数据最大允许大小为1 MB
     */
    private void updateNodeData(ZooKeeper zk) throws KeeperException, InterruptedException {
        /*
          每一步操作/node3都要先执行该语句
          因为上一步创建的node3 添加了 digest ACL 所以在获取该节点信息的时候,要先添加授权
         */
        zk.addAuthInfo("digest","wangsaichao:123456".getBytes());

        /*
          同步设置数据 -1匹配任何版本
         */
        Stat stat = zk.setData("/node3", "嗨喽".getBytes(), -1);
        System.out.println(stat);

        /*
          异步设置数据
         */
        zk.setData("/node3", "helloWord".getBytes(), -1, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("ctx:" + ctx);
                System.out.println("stat:" + stat);
            }
        }, "传给服务端的内容,会在异步回调时传回来");
        Thread.sleep(2000);

    }

    /**
     * 删除给定路径的节点。如果存在这样的节点，则调用将成功，并且给定版本与节点的版本匹配
     * 如果给定版本为-1，则它匹配任何节点的版本
     * 如果节点不存在，将抛出NoNodeException。
     * 如果给定版本与节点的版本不匹配，将抛出BadVersionException。
     * 如果节点有子节点，将抛出NotEmptyException。
     * 如果成功将触发现有API调用留下的给定路径节点上的所有监视，以及getChildren API调用留下的父节点上的监视。
     */
    private void deleteNode(ZooKeeper zk) throws KeeperException, InterruptedException {
        /*
          同步删除节点
          path 节点路径
          version 版本号 -1 代表匹配所有版本
         */
        zk.delete("/node1",-1);

        /*
          异步删除节点
         */
        zk.delete("/node2", -1, new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("ctx:" + ctx);
            }
        }, "传给服务端的内容,会在异步回调时传回来");
        Thread.sleep(2000);

    }

    /**
     * 返回给定路径的节点的stat。如果不存在这样的节点，则返回null。
     * 如果wathher非空并且调用成功（不会抛出异常），则会在具有给定路径的节点上保留监视。wather将由创建/删除节点或在节点上设置数据的成功时触发。
     */
    private void isNodeExist(ZooKeeper zk) throws KeeperException, InterruptedException {
        /*
          同步检查节点是否存在,并留下监听
         */
        Stat exists = zk.exists("/node2", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("留下监视");
            }
        });
        System.out.println("判断节点是否存在：" + exists);

        /*
          异步检查节点是否存在,并留下监听
         */
        zk.exists("/node2", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("留下监视");
            }
        }, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("ctx:" + ctx);
                System.out.println("判断节点是否存在：" + stat);
            }
        }, "传给服务端的内容,会在异步回调时传回来");

    }

    /**
     * 设置ACL
     */
    private void ACL(ZooKeeper zk) throws KeeperException, InterruptedException {
        //注册一个 helloworld 密码为123456的用户
        zk.addAuthInfo("digest","helloworld:123456".getBytes());

        /*
         注册一个 wangsaichao 密码为123456的用户
         因为是 /node3 节点 所以还需要 先添加 /node3的授权
         */
        zk.addAuthInfo("digest", "wangsaichao:123456".getBytes());
        Stat auth = zk.setACL("/node3",
                Collections.singletonList(
                        new ACL(ZooDefs.Perms.ALL, new Id("auth", "wangsaichao:123456"))), -1);
        System.out.println(auth);

        /*
          获取ACL
         */
        List<ACL> acl = zk.getACL("/node3", new Stat());
        System.out.println(acl);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event:" + watchedEvent);
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("zookeeper state is " + Event.KeeperState.SyncConnected);
        }
    }
}
