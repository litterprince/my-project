package com.network.netty.morehandlers;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private final int port;

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(20000).start();
    }

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = null;
        try {
            eventLoopGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            b.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoInServerHandler1());
                            socketChannel.pipeline().addLast(new EchoOutServerHandler1());
                            socketChannel.pipeline().addLast(new EchoOutServerHandler2());
                            socketChannel.pipeline().addLast(new EchoInServerHandler2());
                        }
                    });
            ChannelFuture future = b.bind().sync();
            System.out.println("start listening port: "+future.channel());
            future.channel().closeFuture().sync();
        } finally {
            if (eventLoopGroup != null) {
                eventLoopGroup.shutdownGracefully().sync();
            }
        }
    }

    public class EchoInServerHandler1 extends ChannelInboundHandlerAdapter {
        /**
         * 从客户端接收到数据后调用
         *
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("in1"); //通知下一个InBoundHandler
            ctx.fireChannelRead(msg);
        }

        /**
         * 读取数据结束
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        /**
         * 发生异常时被调用
         *
         * @param ctx
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public class EchoInServerHandler2 extends ChannelInboundHandlerAdapter {
        /**
         * 从客户端接收到数据后调用
         *
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("in2"); //读取数据
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String str = new String(bytes, "UTF-8");
            System.out.println("Server receive Client data:" + str); //向客户端发送数据
            System.out.println("Server send data To Client...");
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello".getBytes()); ////通知下一个outBoundHandler out2
            ctx.write(byteBuf);
        }

        /**
         * 读取数据结束
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        /**
         * 发生异常时被调用
         *
         * @param ctx
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public class EchoOutServerHandler1 extends ChannelOutboundHandlerAdapter {
        /**
         * 向客户端发送消息
         *
         * @param ctx
         * @param msg
         * @param promise
         * @throws Exception
         */
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("out1");
            ByteBuf resp = Unpooled.copiedBuffer("HE".getBytes()); //通知下一个outBoundHandler out1
            ctx.write(resp);
            ctx.flush();
        }
    }

    public class EchoOutServerHandler2 extends ChannelOutboundHandlerAdapter {
        /**
         * 向客户端发送消息
         *
         * @param ctx
         * @param msg
         * @param promise
         * @throws Exception
         */
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("out2");
            ByteBuf resp = Unpooled.copiedBuffer("HE2".getBytes());
            ctx.write(resp);
            ctx.flush();
        }
    }
}
