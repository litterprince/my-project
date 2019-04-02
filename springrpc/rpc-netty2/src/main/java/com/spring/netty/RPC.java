package com.spring.netty;

import com.spring.netty.exception.ProvidersNoFoundException;
import com.spring.netty.message.RPCRequest;
import com.spring.netty.server.RPCResponseNet;
import com.spring.netty.zk.ZKConnect;
import com.spring.netty.zk.ZKServerService;
import com.sun.deploy.config.ClientConfig;
import io.netty.channel.Channel;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.server.ServerConfig;

import java.io.IOException;

public class RPC {
    private static ClientConfig clientConfig;
    private static ServerConfig serverConfig;

    public static void start() throws InterruptedException {
        ZooKeeper zooKeeper = new ZKConnect().serverConnect();
        ZKServerService zkServerService = new ZKServerService(zooKeeper);
        try {
            zkServerService.initZNode();
            //创建所有提供者服务的zNode
            zkServerService.createServerService();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        //阻塞服务端不会退出
        RPCResponseNet.connect();
    }

    public static void send(RPCRequest request) throws ProvidersNoFoundException {
        // TODO: 发送请求
        // 根据服务名，获取响应的channel

        // 发送请求

        // 阻塞等待
    }

    public static Channel connect(String ip) {
        return null;
    }
}
