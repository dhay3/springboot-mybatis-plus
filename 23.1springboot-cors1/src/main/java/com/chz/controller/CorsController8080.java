package com.chz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController8080 {
    /*
    测试跨域请求会不会发送当前项目的controller
     */
    @RequestMapping("/getCors")
    public String get(String msg) {
        System.out.println(msg);
        return msg;
    }
}
