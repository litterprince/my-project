package com.spring.util;

import com.spring.po.SysUserBean;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PwdEncrypt {
    //随机数生成器
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    //指定散列算法为md5
    private static String algorithmName = "MD5";
    //散列迭代次数
    private static final int hashIterations = 1;

    public static void encrypt(SysUserBean user) {
        user.setSalt(randomNumberGenerator.nextBytes().toString());//.toHex()
        String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getSalt()) ,hashIterations).toString();//.toHex()
        user.setPassword(newPassword);
    }
}
