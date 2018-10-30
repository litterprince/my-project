package com.netty.rpc;

import com.netty.simple.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RpcServer {
    private int port;
    private static final int LARK_LENGTH_SIZE = 4;
    private static final short CLIENT_RESPONSE_TAG = 1000;

    public RpcServer(int port) {
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
            ByteBuf result = (ByteBuf) msg;
            byte[] result1 = new byte[result.readableBytes()];
            // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
            result.readBytes(result1);
            String resultStr = new String(result1);
            // 接收并打印客户端的信息
            System.out.println("receive data:" + resultStr);
            // 释放资源，这行很关键
            result.release();

            // 向客户端发送消息
            byte[] b = getTLVData("hello", "jeffService");
            // 在当前场景下，发送的数据必须转换成ByteBuf数组
            ByteBuf encoded = ctx.alloc().buffer(2 * b.length);
            encoded.writeBytes(b);
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
        new RpcServer(9999).run();
    }


    private static byte[] getTLVData(String data, String etcdService) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //TL
            bos.write(writeI16(CLIENT_RESPONSE_TAG));
            final byte[] bytes = data.getBytes("utf-8");
            bos.write(writeI32(LARK_LENGTH_SIZE + bytes.length + LARK_LENGTH_SIZE + etcdService.length()));
            //L1V1
            bos.write(writeI32(bytes.length));
            bos.write(bytes);
            //L2V2
            bos.write(writeI32(etcdService.length()));
            bos.write(etcdService.getBytes("utf-8"));
            //send data value.
            return bos.toByteArray();
        } catch (IOException e) {

        }
        return null;
    }

    private static byte[] writeI16(short i16) {
        byte[] i16out = new byte[2];
        i16out[0] = (byte) (255 & i16 >> 8);
        i16out[1] = (byte) (255 & i16);

        return i16out;
    }

    private static byte[] writeI32(int i32) {
        byte[] i32out = new byte[4];
        i32out[0] = (byte) (255 & i32 >> 24);
        i32out[1] = (byte) (255 & i32 >> 16);
        i32out[2] = (byte) (255 & i32 >> 8);
        i32out[3] = (byte) (255 & i32);
        return i32out;
    }
}
