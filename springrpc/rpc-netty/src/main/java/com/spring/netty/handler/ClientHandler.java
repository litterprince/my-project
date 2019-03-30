package com.spring.netty.handler;

import com.spring.netty.RPC;
import com.spring.netty.RPCClient;
import com.spring.netty.util.Request;
import com.spring.netty.util.Response;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {
    // TODO 思考，会有线程安全问题吗
    public static ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        String responseJson = (String) msg;
        Response response = (Response) RPC.responseDecode(responseJson);

        assert response != null;
        synchronized (RPCClient.requestLockMap.get(response.getRequestId())){
            //唤醒在该对象锁上wait的线程
            Request request = RPCClient.requestLockMap.get(response.getRequestId());
            request.setResult(response.getResult());
            request.notifyAll();
        }
    }
}
