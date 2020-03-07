package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class MyZuulController{

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> test(){
        Map<String,String> result = new HashMap<>();
        result.put("name","Jerry");
        result.put("age","18");
        return result;
    }
}
