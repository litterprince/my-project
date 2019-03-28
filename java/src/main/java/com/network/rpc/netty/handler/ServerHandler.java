package com.network.rpc.netty.handler;

import com.network.rpc.netty.RPC;
import com.network.rpc.netty.RPCClient;
import com.network.rpc.netty.RPCServer;
import com.network.rpc.netty.config.ServerConfig;
import com.network.rpc.netty.util.Request;
import com.network.rpc.netty.util.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String requestJson = (String) msg;
        System.out.println("receive request:" + requestJson);
        Request request = RPCClient.requestDecode(requestJson);
        Object result = invoke(request);
        //netty的write方法并没有直接写入通道(为避免多次唤醒多路复用选择器)
        //而是把待发送的消息放到缓冲数组中，flush方法再全部写到通道中

        //记得加分隔符 不然客户端一直不会处理
        Response response = new Response();
        assert request != null;
        response.setRequestId(request.getRequestId());
        response.setResult(result);
        String respStr = RPCServer.responseEncode(response);
        assert respStr != null;
        ByteBuf responseBuf = Unpooled.copiedBuffer(respStr.getBytes());
        ctx.writeAndFlush(responseBuf);
    }

    public static Object invoke(Request request){
        Object result = null;
        String implClassName = RPC.getServerConfig().getServerImplMap().get(request.getClassName());
        //TODO: 思考，这里的Map里面存储的impl名字，而不是class对象
        try {
            Class clazz = Class.forName(implClassName);
            Object[] parameters = request.getParameters();
            int num = parameters.length;
            Class[] parameterTypes = new Class[num];
            for (int i = 0; i < num; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
            Method method = clazz.getMethod(request.getMethodName(), parameterTypes);
            result = method.invoke(clazz.newInstance(), parameters);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }
}
