package com.spring.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "/test")
public class TestController {

    @RequestMapping("/")
    public String index(HttpServletRequest request, ModelMap map){
        return "index";
    }
}
