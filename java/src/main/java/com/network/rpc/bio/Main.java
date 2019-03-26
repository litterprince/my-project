package com.network.rpc.bio;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private final static int port = 8080;
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server server = new ServiceCenter(port);
                    server.register(HelloService.class, HelloServiceImpl.class);
                    server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        HelloService service = RpcClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", port));
        System.out.println(service.sayHello("jeff"));
    }
}
