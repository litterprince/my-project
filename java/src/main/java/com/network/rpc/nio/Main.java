package com.network.rpc.nio;

import com.network.rpc.service.HelloService;
import com.network.rpc.service.HelloServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        final int port = 8080;
        new Thread(new Runnable() {
            @Override
            public void run() {
                RPCServer server = RPCServer.getInstance(port);
                server.addClass(HelloServiceImpl.class);
                try {
                    server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        RPCClient client = RPCClient.getInstance();
        client.init("localhost", port);
        HelloService service = (HelloService) client.getRemoteProxy(HelloService.class);
        System.out.println(service.sayHello("jeff"));
    }
}
