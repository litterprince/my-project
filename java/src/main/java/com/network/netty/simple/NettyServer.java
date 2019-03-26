package com.network.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //EventLoopGroup是用来处理IO操作的多线程事件循环器
        //bossGroup 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //启动 NIO 服务的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //配置 Channel
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            // 注册handler
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();
            // 等待服务器 socket 关闭 。
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public class NettyServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            //System.out.println("NettyServerHandler.channelRead");
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] result = new byte[byteBuf.readableBytes()];
            // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
            byteBuf.readBytes(result);
            String resultStr = new String(result);
            // 接收并打印客户端的信息
            System.out.println("Client said:" + resultStr);
            // 释放资源，这行很关键
            byteBuf.release();

            // 向客户端发送消息
            String response = "hello client!";
            // 在当前场景下，发送的数据必须转换成ByteBuf数组
            ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
            encoded.writeBytes(response.getBytes());
            ctx.write(encoded);
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // 当出现异常就关闭连接
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyServer(9999).run();
    }
}
