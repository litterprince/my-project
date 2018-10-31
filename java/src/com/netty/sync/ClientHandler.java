package com.netty.sync;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netty.utils.ConstantParam;
import com.netty.utils.MD5Util;
import com.netty.utils.MsgMD5;
import com.netty.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final int REV_SLEEP_TIME = 50;
    private static final short CLIENT_RECEIVE = 2000;
    public static final int LARK_TAG_LENGTH = 2;
    private static final int LARK_LENGTH_SIZE = 4;
    private static final short CLIENT_RESPONSE_TAG = 1000;
    private ChannelHandlerContext ctx;
    private ChannelPromise promise;
    private String message;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public ChannelPromise sendMessage(Map<String, String> conf, JSONArray values) {
        if (ctx == null) throw new IllegalStateException();
        byte[] b = getSendData(conf, values);
        //System.out.println("send message:"+ new String(b));
        ByteBuf encoded = ctx.alloc().buffer(b.length);
        encoded.writeBytes(b);
        promise = ctx.writeAndFlush(encoded).channel().newPromise();
        return promise;
    }

    private byte[] getSendData(Map<String, String> conf, JSONArray values) {
        String command = StringUtils.getString(conf.get(ConstantParam.RPC_USER_COMMAND), "");
        String etcdService = StringUtils.getString(conf.get(ConstantParam.RPC_ETCD_SERVICE), "");
        String secret = StringUtils.getString(conf.get(ConstantParam.RPC_USER_SECRET), "");
        String className = StringUtils.getString(conf.get(ConstantParam.RPC_CLASS_NAME), "");
        String methodName = StringUtils.getString(conf.get(ConstantParam.RPC_METHOD_NAME), "");
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

    private JSONObject getDataBody(Map<String, String> conf, String className, String methodName, JSONArray params) {
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

    private byte[] formatPkg(String command, String pkg, String etcdService) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("3\n");
        stringBuilder.append(command + "\n");
        stringBuilder.append(pkg.getBytes().length + "\n");
        stringBuilder.append(pkg);
        //支持lark的TLV协议
        return getTLVData(stringBuilder.toString(), etcdService);
    }

    private byte[] getTLVData(String data, String etcdService) {
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

    private byte[] writeI16(short i16) {
        byte[] i16out = new byte[2];
        i16out[0] = (byte) (255 & i16 >> 8);
        i16out[1] = (byte) (255 & i16);

        return i16out;
    }

    private byte[] writeI32(int i32) {
        byte[] i32out = new byte[4];
        i32out[0] = (byte) (255 & i32 >> 24);
        i32out[1] = (byte) (255 & i32 >> 16);
        i32out[2] = (byte) (255 & i32 >> 8);
        i32out[3] = (byte) (255 & i32);
        return i32out;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] b = new byte[result.readableBytes()];
        result.readBytes(b);
        message = getReceive(b);
        promise.setSuccess();
        result.release();
        //关闭链路
        ctx.close();
    }

    public String getMessage() {
        return message;
    }

    private String getReceive(byte[] b) throws Exception {
        try {
            int recTag = (short) ((b[0] & 255) << 8 | b[1] & 255);
            int totalLen = (int) ((b[2] & 255) << 24 | (b[3] & 255) << 16 | (b[4] & 255) << 8 | b[5] & 255);
            StringBuilder sb = new StringBuilder();
            int startIndex = LARK_TAG_LENGTH + LARK_LENGTH_SIZE;
            int remainSize = totalLen - startIndex;
            if (recTag != CLIENT_RECEIVE) {
                readLarkProtoInfo(b, sb, startIndex, remainSize, totalLen);
                throw new RuntimeException("receive lark agent error, tag:" + recTag + ", msg: " + sb.toString());
            }
            // L1V1
            int l1 = byteArrayToInt(b, startIndex);
            String recValue = byteArrayToString(b, startIndex + LARK_LENGTH_SIZE, l1);
            remainSize -= (LARK_LENGTH_SIZE + l1);
            startIndex += (LARK_LENGTH_SIZE + l1);
            // LNVN
            readLarkProtoInfo(b, sb, startIndex, remainSize, totalLen);
            return recValue.substring(3, recValue.length() - 1);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean readLarkProtoInfo(byte[] b, StringBuilder sb, int startIndex, int remainSize, int totalLen) {
        if (startIndex + remainSize > totalLen || remainSize <=0) {
            return true;
        }
        try {
            int length = byteArrayToInt(b, startIndex); //读取L...长度
            sb.append(byteArrayToString(b, startIndex + LARK_LENGTH_SIZE, length));
            remainSize -= (LARK_LENGTH_SIZE + length);
            startIndex += (LARK_LENGTH_SIZE + length);
            if (startIndex + remainSize > totalLen || remainSize <=0) {
                return true;
            }
            return readLarkProtoInfo(b, sb, startIndex, remainSize, totalLen);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String byteArrayToString(byte[] b, int start, int length) throws Exception {
        return new String(b, start, length, "utf-8");
    }

    private int byteArrayToInt(byte[] b, int start) {
        return b[start+3] & 0xFF |
                (b[start+2] & 0xFF) << 8 |
                (b[start+1] & 0xFF) << 16 |
                (b[start] & 0xFF) << 24;
    }
}
