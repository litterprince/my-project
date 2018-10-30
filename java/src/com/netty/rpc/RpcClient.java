package com.netty.rpc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netty.utils.ConstantParam;
import com.netty.utils.MD5Util;
import com.netty.utils.MsgMD5;
import com.netty.utils.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RpcClient {
    private static final int LARK_LENGTH_SIZE = 4;
    private static final short CLIENT_RESPONSE_TAG = 1000;
    private SocketChannel socketChannel;

    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyClientHandler());
                }
            });

            // Start the client.
            ChannelFuture future = b.connect(host, port).sync();

            // Wait until the connection is closed.
            future.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static class NettyClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf result = (ByteBuf) msg;
            byte[] result1 = new byte[result.readableBytes()];
            result.readBytes(result1);
            result.release();
            System.out.println("receive data:" + new String(result1));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // 当出现异常就关闭连接
            cause.printStackTrace();
            ctx.close();
        }

        // 连接成功后，向server发送消息
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            byte[] b = getSendData();
            System.out.println("send data:"+ new String(b));
            ByteBuf encoded = ctx.alloc().buffer(b.length);
            encoded.writeBytes(b);
            ctx.write(encoded);
            ctx.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        RpcClient client=new RpcClient();
        client.connect("127.0.0.1", 12311);
    }

    public static byte[] getSendData() {
        String className = "RpcClient_TrusteeshipData";
        String methodName = "encryptData";
        long param = 111;
        Map<String, String> conf = new HashMap<String, String>();
        conf.put("command", "RPC");
        conf.put("user", "Optool");
        conf.put("pwd", "82bb6a906a4d69faa7010c4eb28aa4cf");
        conf.put("secret", "769af463a39f077a0340a189e9c1ec28");
        conf.put("etcdService", "Trusteeship");//etcd服务名称

        String appId = StringUtils.getString(conf.get(ConstantParam.RPC_USER_APPID), "");
        String appKey = StringUtils.getString(conf.get(ConstantParam.RPC_USER_APPKEY), "");
        long timestamp = System.currentTimeMillis() / 1000;
        JSONArray values = new JSONArray();
        values.add(param);
        values.add(appId);
        values.add(timestamp);
        values.add(getToken(new long[]{param}, timestamp, appId, appKey));

        String command = StringUtils.getString(conf.get(ConstantParam.RPC_USER_COMMAND), "");
        String etcdService = StringUtils.getString(conf.get(ConstantParam.RPC_ETCD_SERVICE), "");
        String secret = StringUtils.getString(conf.get(ConstantParam.RPC_USER_SECRET), "");
        JSONObject jsonObject = getDataBody(conf, className, methodName, values);
        // 生成signature的MD5值
        String dataString = jsonObject.get("data").toString();
        String signature = MsgMD5.getSignatureMD5(dataString, secret);
        jsonObject.put("signature", signature);

        // 将字符串进行转义，添加到jsonObject
        jsonObject.put("data", dataString);
        byte[] data = formatPkg(command, jsonObject.toJSONString(), etcdService);
        return data;
    }

    public static String getToken(long[] dataId, long timestamp, String appId, String appKey) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(appId);
        strBuilder.append(appKey);
        strBuilder.append(StringUtils.getJoinString(dataId, ","));
        strBuilder.append(timestamp);
        return MD5Util.encrypt(strBuilder.toString());
    }

    public static JSONObject getDataBody(Map<String, String> conf, String className, String methodName, JSONArray params) {
        String user = StringUtils.getString(conf.get(ConstantParam.RPC_USER_USER), "");
        String pwd = StringUtils.getString(conf.get(ConstantParam.RPC_USER_PWD), "");

        //初始化jsonObject
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        //添加json
        jsonObject.put("data", dataObject);
        dataObject.put("params", params);
        dataObject.put("method", methodName);
        dataObject.put("class", className);
        dataObject.put("timestamp", System.nanoTime());
        dataObject.put("password", pwd);
        dataObject.put("user", user);
        dataObject.put("version", "2.0");
        return jsonObject;
    }

    private static byte[] formatPkg(String command, String pkg, String etcdService) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("3\n");
        stringBuilder.append(command + "\n");
        stringBuilder.append(pkg.getBytes().length + "\n");
        stringBuilder.append(pkg);
        //支持lark的TLV协议
        return getTLVData(stringBuilder.toString(), etcdService);
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
