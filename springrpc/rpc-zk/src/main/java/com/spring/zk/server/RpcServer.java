package com.spring.zk.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public class RpcServer implements ApplicationContextAware, InitializingBean {
    private String serverAddress;
    private ServiceRegistry serviceRegistry;
    // 存放接口名与服务对象之间的映射关系
    private Map<String, Object> handlerMap = new HashMap<>();

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    // TODO: 实现服务注册
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取所有带有 RpcService 注解的 Spring Bean

        // 放入map中存放
    }

    // TODO: 实现netty启动
    @Override
    public void afterPropertiesSet() throws Exception {
        // start server netty
    }
}
