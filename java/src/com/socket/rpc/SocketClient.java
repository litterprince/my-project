package com.socket.rpc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netty.rpc.utils.ConstantParam;
import com.netty.rpc.utils.MD5Util;
import com.netty.rpc.utils.MsgMD5;
import com.netty.rpc.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class SocketClient {
    private static final int LARK_LENGTH_SIZE = 4;
    private static final short CLIENT_RESPONSE_TAG = 1000;

    public static void main(String[] args) {
        try {
            //创建Socket对象
            Socket socket=new Socket("127.0.0.1",12311);

            //根据输入输出流和服务端连接
            OutputStream sender=socket.getOutputStream();//获取一个输出流，向服务端发送信息
            byte[] b = getSendData();
            System.out.println("send data:"+ new String(b));
            sender.write(b);
            sender.flush();
            socket.shutdownOutput();//关闭输出流

            //接收
            InputStream receiver = socket.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            boolean firstLine = true;
            int readLen;
            int totalLen = 0;
            byte[] bs = new byte[1024];
            short recTag = 0;
            int recLength1 = 0;
            while ((readLen = receiver.read(bs)) != -1) {
                bos.write(bs, 0, readLen);
                //判断数据是否读完
                totalLen += readLen;
                if (firstLine) {
                    firstLine = false;
                    recTag = (short) ((bs[0] & 255) << 8 | bs[1] & 255);
                    recLength1 = ((bs[2] & 255) << 24 | (bs[3] & 255) << 16 | (bs[4] & 255) << 8 | bs[5] & 255);
                }
                if (recLength1 + 6 == totalLen)
                    break;
            }
            byte[] buf = bos.toByteArray();
            System.out.println("receive data:"+new String(buf));

            //关闭相对应的资源
            bos.close();
            receiver.close();
            sender.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getToken(long[] dataId, long timestamp, String appId, String appKey) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(appId);
        strBuilder.append(appKey);
        strBuilder.append(StringUtils.getJoinString(dataId, ","));
        strBuilder.append(timestamp);
        return MD5Util.encrypt(strBuilder.toString());
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
