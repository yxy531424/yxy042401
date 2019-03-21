package com.ssm.controller;

import com.ssm.dto.ResponseDto;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/login")
    public String userLogin2(@RequestParam String userAccount, @RequestParam String password, HttpSession session){
        ResponseDto responseDto= userService.login1(userAccount,password);
        Integer code=responseDto.getError_code();
        if(code==200){
            session.setAttribute("ActiveUser",responseDto.getObj());
            return  "index";

        }else{
            return "login";
        }
    }
}
