package com.spring.zk.client;

import com.spring.zk.coder.RpcDecoder;
import com.spring.zk.coder.RpcEncoder;
import com.spring.zk.message.RpcRequest;
import com.spring.zk.message.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RpcClient extends ChannelHandlerAdapter {
    private String host;
    private int port;

    private RpcResponse response;

    private final Object lock = new Object();

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // TODO: 问题，这里的唤醒有效吗
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        this.response = (RpcResponse) msg;
        synchronized (lock){
            lock.notifyAll();
        }
    }

    // TODO: 问题，每次都要创建netty效率不高，需要换个方式
    public RpcResponse send(RpcRequest request) throws Exception {
        // start client netty
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                    socketChannel.pipeline().addLast(new RpcDecoder(RpcResponse.class));
                    socketChannel.pipeline().addLast(RpcClient.this);
                }
            }).option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = b.connect(host, port).sync();
            future.channel().writeAndFlush(request).sync();

            synchronized (lock){
                lock.wait();
            }
            // TODO: 问题，这里没有关闭阻塞的代码，客户端断开时服务端会报错
            // future.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
