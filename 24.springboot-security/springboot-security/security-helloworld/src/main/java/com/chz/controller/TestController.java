package com.chz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    //访问默认用户名为user, 密码会打印在控制台
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
}
