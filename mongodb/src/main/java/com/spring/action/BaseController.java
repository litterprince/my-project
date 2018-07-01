package com.spring.action;

import com.spring.po.UserBean;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BaseController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/index")
    public String index(){
        List<UserBean> list = userService.findList(0,10);
        return "index";
    }
}
