package com.netty.rpc.utils;

/**
 * 
 * 描述:文本通信的加密信息 .
 * @author haow
 *
 */
public class MsgMD5 {

    /**
     * 密码生成MD5算法.
     * @param user  服务的用户名
     * @param rpcSecret secret
     * @return 返回md5值
     */
    public static String getPasswordMD5(String user, String rpcSecret) {
        // md5加密算法
        String md5string = user + ":" + rpcSecret;
        return MD5Util.encrypt(md5string);
    }

    /**
     * 数字签名生成MD5算法.
     * @param data 数据
     * @param rpc_secret_key secret
     * @return 签名后的结果
     */
    public static String getSignatureMD5(String data, String rpc_secret_key) {
        // md5加密算法
        String md5string = data + "&" + rpc_secret_key;
        return MD5Util.encrypt(md5string);
    }
}
