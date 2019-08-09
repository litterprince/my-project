package com.network.rpc.nio;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 *  它所做的工作是利用代理封装方法
 */
public class RPCClient {
    private SocketChannel channel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private static RPCClient instance = null;
    private Selector selector;

    private RPCClient() {
    }

    public static RPCClient getInstance(){
        if(instance == null){
            synchronized (RPCClient.class){
                if(instance == null){
                    instance = new RPCClient();
                }
            }
        }
        return instance;
    }

    public RPCClient init(String ip, int port){
        System.out.println("rpc client start ...");
        try {
            channel = SocketChannel.open(new InetSocketAddress(ip, port));
            channel.configureBlocking(false);

            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Object getRemoteProxy(final Class clazz){
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String clazzName = clazz.getSimpleName();
                if (args == null || args.length == 0) {// 表示没有参数 它传递的类型
                    // 接口名/方法名()
                    channel.write(ByteBuffer.wrap((clazzName + "/" + methodName + "()").getBytes()));
                } else {
                    int size = args.length;
                    String[] types = new String[size];
                    StringBuilder content = new StringBuilder(clazzName).append("/").append(methodName).append("(");
                    for (int i = 0; i < size; i++) {
                        types[i] = args[i].getClass().getName();
                        content.append(types[i]).append(":").append(args[i]);
                        if (i != size - 1)
                            content.append(",");
                    }
                    content.append(")");
                    channel.write(ByteBuffer.wrap(content.toString().getBytes()));
                }
                // 获取结果
                return getResult();
            }
        });
    }

    // 解析结果 如果结尾为null或NULL则忽略
    private Object getResult() {
        try {
            while (selector.select() > 0) {
                for (SelectionKey sk : selector.selectedKeys()) {
                    selector.selectedKeys().remove(sk);
                    if (sk.isReadable()) {
                        SocketChannel sc = (SocketChannel) sk.channel();
                        buffer.clear();
                        sc.read(buffer);
                        int position = buffer.position();

                        String result = new String(buffer.array(),0,position);
                        result = result.trim();
                        buffer.clear();

                        if (result.endsWith("null") || result.endsWith("NULL"))
                            return null;

                        String[] typeValue = result.split(":");
                        String type = typeValue[0];
                        String value = typeValue[1];
                        if (type.contains("Integer") || type.contains("int"))
                            return Integer.parseInt(value);
                        else if (type.contains("Float") || type.contains("float"))
                            return Float.parseFloat(value);
                        else if(type.contains("Long")||type.contains("long"))
                            return Long.parseLong(value);
                        else
                            return value;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
