package com.network.rpc.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceCenter implements Server {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    private final static HashMap<String, Class> serviceRegistry = new HashMap<>();

    private boolean running = true;

    private int port;

    public ServiceCenter(int port) {
        this.port = port;
    }

    @Override
    public void stop() {
        this.running = false;
        executor.shutdown();
    }

    @Override
    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("start server");
        try {
            while (!Thread.interrupted() && isRunning()) {
                executor.execute(new ServiceTask(server.accept()));
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }

    @Override
    public void register(Class serviceInterface, Class impl) {
        serviceRegistry.put(serviceInterface.getName(), impl);
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    /**
     * 内部静态类
     * 1、只能访问外部类的静态成员
     * 2、可以单独实例化
     */
    private static class ServiceTask implements Runnable {
        private Socket client;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                // 序列化
                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                // reflect
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found!");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                // 结果反序列化
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            } catch (IOException | InstantiationException | InvocationTargetException |
                    NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if(input != null){
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(output != null){
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(client != null){
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
