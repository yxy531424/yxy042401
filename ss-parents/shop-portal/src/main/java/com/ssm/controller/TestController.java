package com.ssm.controller;


import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public  Object  test(){

      return "hello world";
    }


}
