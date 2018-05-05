package com.spring.action;

import com.alibaba.fastjson.JSONObject;
import com.inspur.sso.auth.common.encrypt.MD5;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.UrlBase64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.Security;
import java.util.Date;

@Controller
public class QueryController {

    @RequestMapping("/")
    public String index(HttpServletRequest request, ModelMap map){
        return "index";
    }

    @RequestMapping("/test")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        String strAdress = "";//回传接口
        String id = "440002-44000220171123175831-20171123182437-1";//证照模板id
        String app_code = "INSPUR-DZZW-DZZZ";//写死
        String url = "http://localhost:8082";//证照ip地址
        String app_url = url + "/license/input/setting/show?id="+id+"&showType=approval&strAdress="+strAdress;
        // 当前登录用户
        String uid = "74A7970574474C3BA8529E05AFD0EF58";//用户id
        try {
            String applicationURL =toAppTokenUrl(url,uid,"","",app_code,request);
            app_url = encrypt(app_url);
            applicationURL += "&app_url=" + app_url + "&app_code=" + app_code;
            applicationURL = response.encodeURL(applicationURL);
            response.sendRedirect(applicationURL);
            return null;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String encrypt(String var0) {
        if (var0 == null) {
            return "";
        } else if (var0.length() == 0) {
            return "";
        } else {
            BigInteger var1 = new BigInteger(var0.getBytes());
            BigInteger var2 = new BigInteger("0933910847463829232312312");
            BigInteger var3 = var2.xor(var1);
            return var3.toString(16);
        }
    }

    public String toAppTokenUrl(String url,String userId, String username, String pwd,String appCode, HttpServletRequest request) {
        long loginTime = System.currentTimeMillis();
        String loginType = "no";

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        JSONObject var3 = new JSONObject();
        var3.put("userId", userId);
        var3.put("account", username);
        var3.put("password", pwd);
        var3.put("time", (new Date()).getTime());
        String var4 = encryptAES(var3.toJSONString());
        return url.trim() + "/main/sso?client_id=" + appCode.trim() + "&response_type=" + loginType + "&token=" + var4 + "&time=" + loginTime;
    }

    public String encryptAES(String var1) {
        Object var2 = null;
        String var3 = null;

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, getSecretKey("inspurh2wmABdfM7i3K80mAS"));
            byte[] var6 = cipher.doFinal(var1.getBytes());
            if (var6 != null) {
                var3 = encryptBASE64(var6);
            }
        } catch (Exception var5) {

        }

        return var3;
    }

    public SecretKey getSecretKey(String var1) {
        SecretKey secretKey = null;
        try {
            byte[] var2 = MD5.md5Raw(var1.getBytes("UTF-8"));
            secretKey = new SecretKeySpec(var2, "AES");
        } catch (Exception var3) {

        }
        return secretKey;
    }

    public static String encryptBASE64(byte[] var0) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] var1 = UrlBase64.encode(var0);
        return new String(var1, "UTF-8");
    }
}
