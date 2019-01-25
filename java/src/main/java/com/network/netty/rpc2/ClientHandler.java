package main.java.com.network.netty.rpc2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.CountDownLatch;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String message;
    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
        latch.countDown();
    }

    public ChannelPromise sendMessage(byte[] b) throws InterruptedException {
        latch.await();
        if (ctx == null) throw new IllegalStateException();
        //System.out.println("send message:"+ new String(b));
        ByteBuf encoded = ctx.alloc().buffer(b.length);
        encoded.writeBytes(b);
        promise = ctx.writeAndFlush(encoded).channel().newPromise();
        return promise;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] b = new byte[result.readableBytes()];
        result.readBytes(b);
        message = new String(b);
        promise.setSuccess();
        result.release();
        //关闭链路
        //ctx.close();
    }

    public String getMessage() {
        return message;
    }
}
