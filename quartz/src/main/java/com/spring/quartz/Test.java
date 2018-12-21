package com.spring.quartz;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/20.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
@Component
@EnableScheduling
public class Test {

    @Scheduled(cron = "0/5 * * * * ?")//每隔5秒执行一次
    public void test() {
        System.out.println("sms msg has working！");
    }

}
