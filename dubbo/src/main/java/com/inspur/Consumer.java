package com.inspur;

import com.inspur.service.LicenseTransformService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();

        LicenseTransformService service = (LicenseTransformService) context.getBean("licenseTransform");
        service.startTransform();
    }
}
