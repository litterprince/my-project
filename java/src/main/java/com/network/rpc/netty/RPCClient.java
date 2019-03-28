package com.network.rpc.netty;

import com.network.rpc.netty.handler.ClientHandler;
import com.network.rpc.netty.util.ConstantUtil;
import com.network.rpc.netty.util.Request;
import com.network.socket.simple.Client;
import com.thread.lock.lock.ConditionDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RPCClient {
    public static ConcurrentHashMap<String, Request> requestLockMap = new ConcurrentHashMap<>();
    private static RPCClient instance;

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private RPCClient() {
        // start client netty
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //以换行符分包 防止念包半包 2048为最大长度 到达最大长度没出现换行符则抛出异常
                        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(ConstantUtil.MSG_MAX_LENGTH));
                        //将接收到的对象转为字符串
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture f = b.connect(RPC.getClientConfig().getHost(), RPC.getClientConfig().getPort()).sync();
            //TODO: 思考，f.channel().closeFuture().sync();会造成阻塞，不适合这里
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static RPCClient connect(){
        if(instance == null){
            synchronized (RPCClient.class){
                if(instance == null){
                    instance = new RPCClient();
                }
            }
        }
        return instance;
    }

    public static void send(Request request){
        try {
            //TODO: 思考，if the connection is established
            if(ClientHandler.ctx == null) {
                lock.lock();
                System.out.println("wait connect success ...");
                condition.await();
                lock.unlock();
            }

            // send request
            String requestJson = null;
            try {
                requestJson = requestEncode(request);
            } catch (Exception e) {
                e.printStackTrace();
            }

            assert requestJson != null;
            ByteBuf byteBuffer = Unpooled.copiedBuffer(requestJson.getBytes());
            ClientHandler.ctx.writeAndFlush(byteBuffer);
            System.out.println("调用"+request.getRequestId()+"已发送");

            synchronized (request) {
                request.wait();
            }
            System.out.println("调用"+request.getRequestId()+"接收完毕");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //TODO: build request id
    public static String buildRequestId(String name) {
        return null;
    }

    //TODO: request encode
    public static String requestEncode(Request request) {
        return null;
    }

    //TODO: request decode
    public static Request requestDecode(String requestJson) {
        return null;
    }
}
