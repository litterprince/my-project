package com.network.netty.rpc2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RpcClient {
    private final int CONNECT_TIMEOUT = 6000;
    private ClientHandler clientHandler = new ClientHandler();
    private Channel channel;
    private Bootstrap bootstrap;
    private static RpcClient instance = null;

    public static RpcClient getInstance(String host, int port) {
        if(instance == null){
            synchronized(RpcClient.class) {
                if(instance==null) {
                    instance = new RpcClient(host, port);
                }
            }
        }
        return instance;
    }

    private RpcClient(String host, int port){
        init(host, port);
    }

    private void init(String host, int port){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT);//连接超时时间
            bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("handler", clientHandler);
                }
            });
            if(channel==null || !channel.isActive()){
                connect(host, port);
            }
        } catch (Exception e) {
            e.printStackTrace();
            workerGroup.shutdownGracefully();
        }
    }

    private void connect(String host, int port) throws InterruptedException {
        this.channel = bootstrap.connect(host, port)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (!future.isSuccess()) {
                            System.err.println("Connect to host error: " + future.cause());
                        }
                    }
                })
                .sync().channel();
        while (!channel.isActive()) {
            Thread.sleep(1000);
        }
    }

    public String getResponse(byte[] b) throws Exception {
        ChannelPromise promise = clientHandler.sendMessage(b);
        promise.await();
        return clientHandler.getMessage();
    }
}
