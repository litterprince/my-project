package com.spring.zk.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public class ServerHandler extends ChannelHandlerAdapter {
    private final Map<String, Object> handlerMap;

    public ServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    // TODO: 实现服务端方法调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    private Object invoke() {
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
