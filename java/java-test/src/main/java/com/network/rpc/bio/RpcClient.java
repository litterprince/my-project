package com.network.rpc.bio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RpcClient<T> {
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress address){
        Object obj = Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectInputStream input = null;
                ObjectOutputStream output = null;
                try{
                    socket = new Socket();
                    socket.connect(address);

                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeUTF(serviceInterface.getName());
                    output.writeUTF(method.getName());
                    output.writeObject(method.getParameterTypes());
                    output.writeObject(args);

                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                } finally {
                    if(output != null) {output.close();}
                    if(input != null) {input.close();}
                    if(socket != null) {socket.close();}
                }
            }
        });
        return (T) obj;
    }
}
