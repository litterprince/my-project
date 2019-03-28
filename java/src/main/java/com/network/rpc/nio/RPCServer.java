package com.network.rpc.nio;

import org.apache.commons.net.SocketClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * 它接受的内容是class/方法名(参数类型:参数,参数类型:参数)
 */
public class RPCServer {
    private static RPCServer instance = null;
    private HashMap<String, Class> serviceRegistry = new HashMap<>();
    private Selector selector;
    private ServerSocketChannel ssc;

    private RPCServer(int port){
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false); // 非阻塞
            ssc.bind(new InetSocketAddress(port));

            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RPCServer getInstance(int port) {
        if(instance == null){
            synchronized (RPCServer.class){
                if(instance == null) {
                    instance = new RPCServer(port);
                }
            }
        }
        return instance;
    }

    public RPCServer addClass(Class impl){
        String name = impl.getInterfaces()[0].getSimpleName();
        serviceRegistry.put(name, impl);
        return this;
    }

    public void start() throws IOException {
        System.out.println("service start ...");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            while (selector.select() > 0){
                for(SelectionKey key : selector.selectedKeys()){
                    selector.selectedKeys().remove(key);
                    if(key.isAcceptable()){
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        // key.interestOps(SelectionKey.OP_ACCEPT);
                    } else if(key.isReadable()){
                        SocketChannel sc = (SocketChannel) key.channel();
                        try {
                            remoteHandMethod(byteBuffer, key, sc);
                        } catch (Exception e){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void remoteHandMethod(ByteBuffer byteBuffer, SelectionKey key, SocketChannel sc) throws IOException,
            NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        byteBuffer.clear();
        int read = sc.read(byteBuffer);
        int pos = byteBuffer.position();
        // class/方法名(参数类型:参数,参数类型:参数)
        String msg = new String(byteBuffer.array(), 0, pos).trim();
        String[] clazzData = msg.split("/");
        String className = clazzData[0];
        String methodName = clazzData[1].substring(0, clazzData[1].indexOf("("));
        String temp = clazzData[1].substring(clazzData[1].indexOf("(") + 1, clazzData[1].indexOf(")"));
        // 上一步的格式如下 1 ""，"string:nice"
        String[] typeValues = decodeParamsTypeAndValue(temp);

        Class cl = serviceRegistry.get(className);
        Object result;
        if(cl == null){
            result = "Void:null";
            sc.write(ByteBuffer.wrap(result.toString().getBytes()));
            key.interestOps(SelectionKey.OP_READ);
            return;
        }

        Method method;
        Object obj = cl.newInstance();
        if (typeValues == null) {
            method = cl.getMethod(methodName);
            result = method.invoke(obj);
        } else {
            Class[] types = new Class[typeValues.length];
            Object[] values = new Object[typeValues.length];
            resolveParam(types, values, typeValues);
            method = cl.getDeclaredMethod(methodName, types);
            result = method.invoke(obj,values);
        }

        if (result == null) {
            result = "Void:null";
        } else {
            result = result.getClass().getSimpleName() + ":" + result;
        }

        // 发送结果回去
        sc.write(ByteBuffer.wrap(result.toString().getBytes()));
        key.interestOps(SelectionKey.OP_READ);
    }

    private void resolveParam(Class[] types, Object[] values, String[] typeValues){
        int i = 0;
        for(String typeValue : typeValues){
            String type = typeValue.split(":")[0];
            String value = typeValue.split(":")[1];
            if(type.contains("Integer") || type.contains("int")){
                values[i] = Integer.parseInt(value);
                types[i] = Integer.class;
            } else if(type.contains("Long") || type.contains("long")){
                values[i] = Long.parseLong(value);
                types[i] = Long.class;
            } else if(type.contains("Float") || type.contains("float")){
                values[i] = Float.parseFloat(value);
                types[i] = Float.class;
            } else if(type.contains("Double") || type.contains("double")){
                values[i] = Double.parseDouble(value);
                types[i] = Double.class;
            } else {
                values[i] = value;
                types[i] = String.class;
            }

            i ++;
        }
    }

    // 它返回的格式是 参数类型：参数值
    private String[] decodeParamsTypeAndValue(String params) {
        if (params == null || params.equals(""))
            return null;
        if (!params.contains(","))
            return new String[] { params };
        return params.split(",");
    }
}
