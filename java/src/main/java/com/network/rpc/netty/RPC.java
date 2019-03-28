package com.network.rpc.netty;

import com.network.rpc.netty.config.ClientConfig;
import com.network.rpc.netty.config.ServerConfig;
import com.network.rpc.netty.util.Request;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPC {
    public static ApplicationContext serverContext;
    public static ApplicationContext clientContext;

    public static Object call(Class cls) {
        Object proxyObj = Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Request request = new Request();
                request.setRequestId(RPCClient.buildRequestId(method.getName()));
                request.setClassName(method.getDeclaringClass().getName());//返回表示声明由此 Method 对象表示的方法的类或接口的Class对象
                request.setMethodName(method.getName());
                request.setParameters(args);//输入的实参
                RPCClient.requestLockMap.put(request.getRequestId(),request);
                RPCClient.connect().send(request);

                RPCClient.requestLockMap.remove(request.getRequestId());
                return request.getResult();//目标方法的返回结果
            }
        });
        return proxyObj;
    }

    public static ServerConfig getServerConfig(){
        return serverContext.getBean(ServerConfig.class);
    }

    public static ClientConfig getClientConfig(){
        return clientContext.getBean(ClientConfig.class);
    }
}
