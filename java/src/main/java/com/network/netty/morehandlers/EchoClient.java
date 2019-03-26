package com.network.netty.morehandlers;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.jboss.netty.bootstrap.ClientBootstrap;

import java.net.InetSocketAddress;

public class EchoClient {
    private final int port;

    public static void main(String[] args) throws InterruptedException {
        new EchoClient(20000).start();
    }

    public EchoClient(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = null;
        try {
            eventLoopGroup = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //连接服务器
            ChannelFuture channelFuture = b.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            if (eventLoopGroup != null) {
                eventLoopGroup.shutdownGracefully().sync();
            }
        }
    }

    public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        /**
         * 从服务器接收到数据后调用
         *
         * @param channelHandlerContext
         * @param byteBuf
         * @throws Exception
         */
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            System.out.println("Client read Server data...");
            ByteBuf msg = byteBuf;
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            String str = new String(bytes, "UTF-8");
            System.out.println("Server data :" + str);
        }

        /**
         * 客户端连接服务端后被调用
         *
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client connect start! Send Data....");
            byte[] bytes = "Query Data List".getBytes();
            ByteBuf buffer = Unpooled.buffer(bytes.length);
            buffer.writeBytes(bytes);  //发送
            ctx.writeAndFlush(buffer); //flush
        }

        /**
         * 发生异常时被调用
         *
         * @param ctx
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws
                Exception {
            System.out.println("Client exceptionCaught..."); //释放资源
            ctx.close();
        }
    }
}
