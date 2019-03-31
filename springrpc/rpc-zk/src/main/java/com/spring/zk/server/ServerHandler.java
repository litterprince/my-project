package com.spring.zk.server;

import com.spring.zk.message.RpcRequest;
import com.spring.zk.message.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.Map;

public class ServerHandler extends ChannelHandlerAdapter {
    private final Map<String, Object> handlerMap;

    public ServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    // TODO: 学习，实现服务端方法调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        RpcResponse rpcResponse = new RpcResponse();
        try {
            RpcRequest rpcRequest = (RpcRequest) msg;
            rpcResponse.setRequestId(rpcRequest.getRequestId());
            rpcResponse.setResult(invoke(rpcRequest));
        } catch (Exception e) {
            rpcResponse.setError(e);
            e.printStackTrace();
        }
        ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private Object invoke(RpcRequest rpcRequest) throws Exception {
        String className = rpcRequest.getClassName();
        String methodName = rpcRequest.getMethodName();
        Method method;
        if (!handlerMap.containsKey(className)) {
            throw new Exception("服务未找到!className=" + className);
        }

        Class clz = (Class) handlerMap.get(className);
        method = clz.getMethod(methodName, rpcRequest.getParameterTypes());
        return method.invoke(clz.newInstance(), rpcRequest.getParameters());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
