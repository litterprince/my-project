package com.util.http;

import com.alibaba.fastjson.JSONObject;
import com.util.MD5;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/19.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class FileSysUtils {
    public static String getToken(String user, String pwd, String uri) {
        JSONObject object = new JSONObject();
        object.put("user", user);
        object.put("uri", uri);
        object.put("time", System.currentTimeMillis()/1000);
        String params = object.toJSONString();
        byte[] data = params.getBytes(StandardCharsets.UTF_8);
        String encodedSign = safeUrlBase64Encode(data);
        String accessKey = MD5.encode(params + MD5.encode(pwd));
        return accessKey + ":" + encodedSign;
    }

    /**
     * @param user
     * @param pwd
     * @param sysurl
     * @param uri
     * @return String
     * @throws
     * @Title: get
     * @Description: 读取文件
     */
    public static String get(String user, String pwd, String sysurl, String uri) {
        String param = getToken(user, pwd, uri);
        StringBuilder sb = new StringBuilder();
        sb.append(sysurl).append(uri).append("?token=").append(param);
        System.out.println("sb = " + sb.toString());
        return AsyncHttpClientHelper.fetchHttpGet(sb.toString());
    }

    public static String safeUrlBase64Encode(byte[] data){
        String encodeBase64 = new BASE64Encoder().encode(data);
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replaceAll("[\\s*\t\n\r]", "");
        return safeBase64Str;
    }
}
