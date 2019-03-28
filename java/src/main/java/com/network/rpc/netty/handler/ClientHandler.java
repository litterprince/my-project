package com.network.rpc.netty.handler;

import com.network.rpc.netty.RPCClient;
import com.network.rpc.netty.RPCServer;
import com.network.rpc.netty.util.Request;
import com.network.rpc.netty.util.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    //TODO: 思考，会有线程安全问题吗
    public static ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        String responseJson = (String) msg;
        Response response = (Response) RPCServer.responseDecode(responseJson);

        assert response != null;
        synchronized (RPCClient.requestLockMap.get(response.getRequestId())){
            //唤醒在该对象锁上wait的线程
            Request request = RPCClient.requestLockMap.get(response.getRequestId());
            request.setResult(response.getResult());
            request.notifyAll();
        }
    }
}
