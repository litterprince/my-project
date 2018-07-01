package com.spring.action;

import com.spring.po.AuthUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    @RequestMapping(value="/login")
    public String login(HttpServletRequest request){
        return "login";
    }
    
    @RequestMapping(value="/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkLogin(HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            subject.login(token);//登陆验证
        }
        AuthUser authUser = (AuthUser) subject.getPrincipal();
        Assert.isTrue(subject.hasRole("admin"),"[Assertion failed] - this expression must be true");
        result.put("success", true);
        return result;
    }
}
