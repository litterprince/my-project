package com.network.rpc.netty.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class ClientConfig implements ApplicationContextAware {
    private int port;
    private String host;
    private Map<String,String> serverImplMap;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

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

    public void setServerImplMap(Map<String, String> serverImplMap) {
        this.serverImplMap = serverImplMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
