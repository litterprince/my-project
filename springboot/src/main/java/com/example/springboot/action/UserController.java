package com.example.springboot.action;

import com.example.springboot.po.UserBean;
import com.example.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, ModelMap model){
        //int i = 1/0;
        return "index";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> query(HttpServletRequest request){
        List<UserBean> users = userService.getAll();
        Map<String, Object> map = new HashMap<String, Object>();
        String total = String.valueOf(userService.getCount());
        map.put("total", total);
        map.put("rows", users);
        return map;
    }
}
