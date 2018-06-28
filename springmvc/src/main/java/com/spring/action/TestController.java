package com.spring.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    @RequestMapping(value="/index")
    public String index(HttpServletRequest request){
        request.setAttribute("str","你好");
        return "index";
    }
}
