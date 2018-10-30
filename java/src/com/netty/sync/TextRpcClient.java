package com.netty.sync;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.netty.utils.MD5Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import com.netty.utils.ConstantParam;
import com.netty.utils.StringUtils;

import java.util.Map;

public class TextRpcClient {
    private ClientHandler clientHandler = new ClientHandler();
    private Map<String, String> conf = Maps.newHashMap();

    public void connect(Map<String, String> conf) throws Exception {
        this.conf = conf;
        String host = StringUtils.getString(conf.get(ConstantParam.RPC_USER_HOST), "127.0.0.1");
        int port = StringUtils.getInt(conf.get(ConstantParam.RPC_USER_PORT), 12311);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(clientHandler);
                }
            });
            Channel channel = b.connect(host, port).sync().channel();
            while (!channel.isActive()) {
                Thread.sleep(1000);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getResponse(JSONArray values) throws Exception {
        ChannelPromise promise = clientHandler.sendMessage(conf, values);
        promise.await();
        return clientHandler.getMessage();
    }

    public static String getToken(long[] dataId, long timestamp, String appId, String appKey) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(appId);
        strBuilder.append(appKey);
        strBuilder.append(StringUtils.getJoinString(dataId, ","));
        strBuilder.append(timestamp);
        return MD5Util.encrypt(strBuilder.toString());
    }

    public Map<String, String> getConf() {
        return conf;
    }
}
