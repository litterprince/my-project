package com.spring.zk.client;

import com.spring.zk.coder.RpcDecoder;
import com.spring.zk.coder.RpcEncoder;
import com.spring.zk.message.RpcRequest;
import com.spring.zk.message.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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

    public <T> T create(Class<?> interfaceClass) {
        Object obj = Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setClassName(interfaceClass.getName());
                        request.setMethodName(method.getName());
                        request.setParameters(args);
                        request.setParameterTypes(method.getParameterTypes());

                        if(serviceDiscovery != null ) {
                            serverAddress = serviceDiscovery.discover();
                        }

                        String[] array = serverAddress.split(":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);

                        // TODO: 问题，每次方法调用都要启动一次netty，性能不高
                        // start client netty
                        EventLoopGroup group = new NioEventLoopGroup();
                        ClientHandler handler = new ClientHandler();
                        try {
                            Bootstrap b = new Bootstrap();
                            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                                    socketChannel.pipeline().addLast(new RpcDecoder(RpcResponse.class));
                                    socketChannel.pipeline().addLast(handler);
                                }
                            }).option(ChannelOption.SO_KEEPALIVE, true);

                            ChannelFuture future = b.connect(host, port).sync();
                            future.channel().writeAndFlush(request).sync();

                            future.channel().closeFuture().sync();
                        } catch (Exception e){
                            e.printStackTrace();
                        } finally {
                            group.shutdownGracefully();
                        }
                        RpcResponse response = handler.getRpcResponse();

                        if (response.getError() != null) {
                            throw response.getError();
                        } else {
                            return response.getResult();
                        }
                    }
                }
        );
        return (T) obj;
    }
}
