package com.xiaoze.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.koolearn.item.bean.OrderValidateBean;
import com.koolearn.item.bean.common.customerservice.CheckCondition;
import com.koolearn.item.service.common.customerservice.CheckBusinessService;
import com.xiaoze.api.model.LdapUser;
import com.xiaoze.api.service.DemoService;
import com.koolearn.hrms.service.ILdapService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DemoConsumerController
 * 消费者控制层
 * @author xiaoze
 * @date 2018/6/7
 */
@RestController
public class DemoConsumerController {

    @Reference(version = "${demo.service.version}")
    DemoService demoService;

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        String sayHello = demoService.sayHello(name);
        return sayHello;
    }


    @Reference
    ILdapService iLdapService;

    @Reference
    CheckBusinessService checkBusinessService;

    @RequestMapping("/")
    public String getUserByName() {
        CheckCondition checkCondition = new CheckCondition();
        checkCondition.setOrderProductIdList(Lists.newArrayList(1,2));
        OrderValidateBean orderValidateBean = checkBusinessService.validateForAgent(checkCondition);
        return "done";
    }

}
