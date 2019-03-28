package com.network.rpc.netty.config;

import com.network.rpc.netty.RPC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

//TODO: 思考，为什么要使用context方式
public class ServerConfig implements ApplicationContextAware {
    private int port;

    private Map<String,String> serverImplMap;

    public int getPort() {
        return port;
    }

    public Map<String, String> getServerImplMap() {
        return serverImplMap;
    }

    //为spring提供setter
    public void setPort(int port) {
        this.port = port;
    }

    //TODO: 这个方法需要调用一下
    public void setServerImplMap(Map<String, String> serverImplMap) {
        this.serverImplMap = serverImplMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RPC.serverContext = applicationContext;
    }
}
