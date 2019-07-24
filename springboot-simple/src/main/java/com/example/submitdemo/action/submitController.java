package com.example.submitdemo.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class submitController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> test(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String param = request.getParameter("param");
        String param1 = request.getParameter("param1");
        String str1 = URLDecoder.decode(param1, "UTF-8");
        String str = URLDecoder.decode(param, "UTF-8");
        Map<String, String> map = new HashMap<>();
        map.put("state", "1");
        return map;
    }

    @RequestMapping(value = "/test1")
    public String test1(HttpServletRequest request){
        String param = request.getParameter("param");
        return "index";
    }
}
