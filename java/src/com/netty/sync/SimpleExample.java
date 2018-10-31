package com.netty.sync;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netty.utils.ConstantParam;
import com.netty.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class SimpleExample extends TextRpcClient {
    public static void main(String[] args) throws Exception {
        Map<String, String> conf = new HashMap<String, String>();
        conf.put("command", "RPC");
        conf.put("user", "Optool");
        conf.put("pwd", "82bb6a906a4d69faa7010c4eb28aa4cf");
        conf.put("secret", "769af463a39f077a0340a189e9c1ec28");
        conf.put("etcdService", "Trusteeship");//etcd服务名称
        conf.put("className", "RpcClient_TrusteeshipData");
        conf.put("methodName", "encryptData");
        SimpleExample client = new SimpleExample();
        client.connect(conf);
        Long data = client.getDecryptData(111);
        System.out.println("data:" +data);
        exit(0);
    }

    public Long getDecryptData(long data) throws Exception {
        String appId = StringUtils.getString(this.getConf().get(ConstantParam.RPC_USER_APPID), "");
        String appKey = StringUtils.getString(this.getConf().get(ConstantParam.RPC_USER_APPKEY), "");
        long timestamp = System.currentTimeMillis() / 1000;
        JSONArray values = new JSONArray();
        values.add(data);
        values.add(appId);
        values.add(timestamp);
        values.add(getToken(new long[]{data}, timestamp, appId, appKey));

        String response = this.getResponse(values);
        // 将返回值转换为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (0 != jsonObject.getIntValue("error")) {
            return null;
        }

        return StringUtils.getLong(jsonObject.getString("msg"), -1);
    }
}
