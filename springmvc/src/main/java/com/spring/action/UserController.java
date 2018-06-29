package com.spring.action;

import com.spring.po.UserBean;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/index")
    public String index(HttpServletRequest request){
        UserBean param = new UserBean();
        param.setUserName("123");
        List<UserBean> userList = userService.findByParam(param);
        return "index";
    }
}
