import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/3.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class Test {
    private static Random random = new Random();
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE + 1 < Integer.MAX_VALUE);
    }

    private static String formatUserIp(String ip){
        if(ip!=null && !ip.equals("") && ip.contains(",")){
            String[] uIps = ip.split(",");
            for(String uIp: uIps){
                if(uIp!=null && !uIp.startsWith("10.17")){
                    return uIp.replaceAll("\\s|\n", "");
                }
            }
        }
        return ip;
    }

    public static void testSub(){
        String str = "http://a/b/c/d";
        if(str.indexOf("?") > 0){
            str = str.substring(0, str.indexOf("?"));
        }
        System.out.println(str);
    }

    public static void testDecoder(){
        String emptyStr = "";
        String trueStr = "true";
        String falseStr = "false";
        JSONObject obj = new JSONObject();
        obj.put("emptyStr", emptyStr);
        obj.put("trueStr", trueStr);
        obj.put("falseStr", falseStr);
        obj.put("b", true);
        System.out.println(obj.getBoolean("emptyStr"));
        System.out.println(obj.getBoolean("trueStr"));
        System.out.println(obj.getBoolean("falseStr"));
        System.out.println(obj.getBoolean("b"));

        String str = getMsgData();
        System.out.println(str);
        String str1 = null;
        try {
            str1 = new String(new BASE64Decoder().decodeBuffer(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(str1);
    }

    private static String getMsgData(){
        String[] ipList = new String[]{"61.139.2.21", "218.88.212.31", "61.139.2.69", "172.20.4.125", "171.104.0.55", "59.49.129.35", "119.57.71.6", "219.78.113.243", "121.56.0.23"};
        Date time = new Date();
        Long timeStamp = time.getTime();
        String pageId = UUID.randomUUID().toString().replace("-", "");

        JSONArray resDataList = new JSONArray();
        JSONObject resData = new JSONObject();
        resData.put("resourceUrl", getRandomUrl());
        resData.put("ip", ipList[random.nextInt(ipList.length)]);
        resData.put("isLoadSuccess", random.nextBoolean());
        resData.put("isHijacked", random.nextBoolean());
        resData.put("errorMsg", getRandomString(1000));
        resData.put("fileName", getRandomString(10));
        resData.put("deleteMD5", getRandomString(10));
        resData.put("H5Content", getRandomString(1000));
        resData.put("H5InIframe", getRandomString(9));
        resDataList.add(resData);

        JSONObject resData1 = new JSONObject();
        resData1.put("resourceUrl", getRandomUrl());
        resData1.put("ip", ipList[random.nextInt(ipList.length)]);
        resData1.put("isLoadSuccess", random.nextBoolean());
        resData1.put("isHijacked", random.nextBoolean());
        resData1.put("errorMsg", getRandomString(1000));
        resData1.put("fileName", getRandomString(9));
        resData1.put("deleteMD5", getRandomString(10));
        resData1.put("H5Content", getRandomString(1000));
        resData1.put("H5InIframe", getRandomString(10));
        resDataList.add(resData1);

        JSONObject pageData = new JSONObject();
        pageData.put("id", pageId);
        pageData.put("uid", getRandomString(10));
        pageData.put("url", getRandomUrl());
        pageData.put("timeStamp", timeStamp);
        pageData.put("ua", getRandomString(10));
        pageData.put("device", random.nextInt(2));
        pageData.put("zipName", getRandomString(10) + ".zip");
        pageData.put("resourcePages", resDataList);

        String result = new BASE64Encoder().encode(pageData.toJSONString().getBytes());

        return result;
    }

    private static String getRandomString(int length){
        if(length <= 3) length = 3;
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        StringBuilder sb = new StringBuilder();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    private static String getRandomUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(getRandomString(random.nextInt(5))).append("/")
                .append(getRandomString(random.nextInt(5))).append("/")
                .append(getRandomString(random.nextInt(5))).append("/")
                .append(getRandomString(random.nextInt(5))).append("/");
        return sb.toString();
    }

    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }

    public static void testToml(){
        String str ="'''\n" +
                "Roses are red\n" +
                "Violets are blue'''";
        //String regEx = "(\\s*([a-zA-Z_0-9\\-\\u4e00-\\u9fa5]+)\\s*=\\s*)?\"(.*)\"\\s*";
        String regEx = "'''(.*)'''";
        Pattern pat = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matcher = pat.matcher(str);
        boolean flg1 = matcher.matches();
        System.out.println(flg1);
    }

    public static void testRegEx(String str, String regEx){
        Pattern pat = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matcher = pat.matcher(str);
        boolean flg1 = matcher.matches();
        System.out.println(flg1);
    }

    public static void testNums(int[] nums){
       nums[2] = 10;
    }
}