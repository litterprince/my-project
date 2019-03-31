package com.spring.zk.client;

import com.spring.zk.message.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcProxy {
    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    // TODO: create proxy method
    public <T> T create(Class<?> interfaceClass) {
        Object obj = Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(interfaceClass.getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameters(args);
                        rpcRequest.setParameterTypes(method.getParameterTypes());

                        if(serviceDiscovery != null ) {
                            serverAddress = serviceDiscovery.discover();
                        }


                        return null;
                    }
                }
        );
        return (T) obj;
    }
}
