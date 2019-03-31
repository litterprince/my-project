package com.spring.zk.client;

import com.spring.zk.message.RpcRequest;
import com.spring.zk.message.RpcResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class RpcClient extends ChannelHandlerAdapter {
    private String host;
    private int port;

    private RpcResponse response;

    private final Object obj = new Object();

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // TODO: 实现接收消息功能
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    // TODO: 实现发送请求功能
    public RpcResponse send(RpcRequest request) throws Exception {
        // start client netty

        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
