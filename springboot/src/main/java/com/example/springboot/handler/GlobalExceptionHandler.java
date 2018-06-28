package com.example.springboot.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandle(HttpServletRequest request, Exception e){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("success", false);
        model.put("errMsg", e.getMessage());
        return model;
    }
}
