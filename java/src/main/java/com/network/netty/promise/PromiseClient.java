package com.network.netty.promise;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class PromiseClient {
    private ClientHandler clientHandler = new ClientHandler();
    private final static int CONNECT_TIMEOUT = 6000;

    public static void main(String[] args) throws InterruptedException {
        PromiseClient client = new PromiseClient("127.0.0.1", 9999);
        System.out.println("" + client.getResponse());
    }

    public PromiseClient(String host, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT);//连接超时时间
            b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch)  {
                    ch.pipeline().addLast("handler", clientHandler);
                }
            });
            Channel channel = b.connect(host, port)
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
        } catch (Exception e) {
            e.printStackTrace();
            workerGroup.shutdownGracefully();
        }
    }

    public String getResponse() throws InterruptedException {
        ChannelPromise promise = clientHandler.sendMessage("hello".getBytes());
        promise.await();
        return clientHandler.getMessage();
    }

    public class ClientHandler extends ChannelInboundHandlerAdapter {
        private ChannelHandlerContext ctx;
        private ChannelPromise promise;
        private String message;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            this.ctx = ctx;
        }

        public ChannelPromise sendMessage(byte[] b) {
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
            ctx.close();
        }

        public String getMessage() {
            return message;
        }
    }
}
