package com.spring.util;

import com.spring.annotation.RpcService;
import com.spring.service.HelloService;
import com.spring.service.HelloServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;

        // 获取注解相关
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RpcService.class);
        // 获取bean
        HelloService service = applicationContext.getBean(HelloServiceImpl.class);
    }

    public static <T> T getBean(Class<?> clz){
        return (T) applicationContext.getBean (clz);
    }
}
