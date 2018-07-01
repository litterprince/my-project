package com.spring.action;

import com.spring.po.SysUserBean;
import com.spring.service.UserService;
import com.spring.util.PwdEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        SysUserBean user = new SysUserBean();
        user.setId(UUID.randomUUID().toString());
        user.setUserName("admin");
        user.setUserCode("admin");
        user.setPassword("123456");
        user.setLocked("0");
        user.setCreateTime(new Date());
        PwdEncrypt.encrypt(user);
        userService.insertOne(user);
        result.put("success", true);
        return result;
    }
}
