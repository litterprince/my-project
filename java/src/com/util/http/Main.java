package com.util.http;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/19.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class Main {
    public static void main(String[] args) {
        String user = "test123";
        String pwd = "123456";
        String sysurl = "http://192.168.53.11:9000/token";
        //String uri = "/dev_test_enc/dove1.png";
        String uri = "/dev_test_enc/dove1.png";
        String fileStr = FileSysUtils.get(user, pwd, sysurl, uri);
        System.out.println("fileStr=" +fileStr);
    }
}
