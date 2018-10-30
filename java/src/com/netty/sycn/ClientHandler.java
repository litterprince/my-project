package com.netty.sycn;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private Object message;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public ChannelPromise sendMessage(byte[] b) {
        if (ctx == null) throw new IllegalStateException();
        System.out.println("send message:"+ new String(b));
        ByteBuf encoded = ctx.alloc().buffer(b.length);
        encoded.writeBytes(b);
        promise = ctx.writeAndFlush(encoded).channel().newPromise();
        return promise;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] b = new byte[result.readableBytes()];
        result.readBytes(b);
        message = b;
        promise.setSuccess();
        result.release();
        System.out.println("receive message:" + new String(b));
    }
}
