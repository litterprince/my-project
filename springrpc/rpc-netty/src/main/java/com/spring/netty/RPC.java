package com.spring.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.netty.config.ClientConfig;
import com.spring.netty.config.ServerConfig;
import com.spring.netty.util.Request;
import com.spring.netty.util.Response;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RPC {
    public static ApplicationContext serverContext;
    public static ApplicationContext clientContext;

    //TODO: 思考，这里的编解使用静态变量的效率
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Object call(Class cls) {
        Object proxyObj = Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, new InvocationHandler() {
            //TODO: 思考，这个记录调用次数的字段生效吗
            private AtomicInteger requestTimes = new AtomicInteger(0);

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Request request = new Request();
                request.setRequestId(buildRequestId(method.getName()));
                //返回表示声明由此 Method 对象表示的方法的类或接口的Class对象
                request.setClassName(method.getDeclaringClass().getName());
                request.setMethodName(method.getName());
                request.setParameters(args);//输入的实参
                RPCClient.requestLockMap.put(request.getRequestId(),request);
                RPCClient.connect().send(request);

                RPCClient.requestLockMap.remove(request.getRequestId());
                return request.getResult();//目标方法的返回结果
            }

            //TODO: 思考如何保证同一客户端的requestId不会重复
            private String buildRequestId(String methodName) {
                StringBuilder sb = new StringBuilder();
                sb.append(requestTimes.incrementAndGet());
                sb.append(System.currentTimeMillis());
                sb.append(methodName);
                Random random = new Random();
                sb.append(random.nextInt(1000));
                return sb.toString();
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

    //TODO: 思考，这里使用的分隔符
    public static String requestEncode(Request request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request)+System.getProperty("line.separator");
    }

    public static Request requestDecode(String json) throws IOException {
        return objectMapper.readValue(json, Request.class);
    }

    public static String responseEncode(Response response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response)+System.getProperty("line.separator");
    }

    public static Object responseDecode(String json) throws IOException {
        return objectMapper.readValue(json, Response.class);
    }
}
