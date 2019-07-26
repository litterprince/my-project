package com.example.dbmt.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(){
        return "main";
    }

    @RequestMapping("/index")
    public String menu(){
        return "index";
    }
}
