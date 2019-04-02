package com.spring.netty.client;

import com.spring.netty.message.RPCRequest;
import com.spring.netty.util.LoadBalance;
import com.spring.netty.util.impl.RandomBalance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RPCRequestNet {
    // 请求集合
    public Map<String, RPCRequest> requestMap = new ConcurrentHashMap<>();
    // <IP地址,锁>集合，每个IP对应一个锁，防止重复连接一个IP多次
    public Map<String, Lock> connectLock = new ConcurrentHashMap<>();
    // <服务名称,服务信息类>集合
    public Map<String, ServiceInfo> serviceInfoMap = new ConcurrentHashMap<>();
    // <IP地址,管道>集合
    public Map<String, IPChannelInfo> IPChannelInfoMap = new ConcurrentHashMap<>();
    // <IP地址,读写锁>集合，更新IP使用写锁，读取IP使用读锁
    public Map<String, ReentrantReadWriteLock> serviceLockMap = new ConcurrentHashMap<>();

    private LoadBalance loadBalance = new RandomBalance();

    private static RPCRequestNet instance = null;


}
