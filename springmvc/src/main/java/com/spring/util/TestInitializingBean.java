package com.spring.util;

import org.springframework.beans.factory.InitializingBean;

/**
 * 初始化Bean的两种方法
 */
public class TestInitializingBean implements InitializingBean {
    /**
     * 方法一：
     * xml中配置了bean，spring初始化完bean后会触发该方法
     * 配置内容如：<bean id="testInitializingBean" class="com.TestInitializingBean" ></bean>
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("hi");
    }

    /**
     * 方法二：
     * 通过xml指定init方法
     * 配置内容如：<bean id="testInitializingBean" class="com.TestInitializingBean" init-method="testInit"></bean>
     */
    public void initMethod(){
        System.out.println("this is init method");
    }
}
