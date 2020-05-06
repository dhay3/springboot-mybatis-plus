package com.chz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Interceptor 只能对Controller请求进行拦截
 * Filter 对所有请求和响应拦截
 * 假如不在controller中 filter会拦截, 但是interceptor不会拦截
 */
//@RestController
@Controller
public class MyController {
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello world");
        return "hello world";
    }

    /**
     * 返回模板引擎或是json,一次请求 filter和interceptor拦截一次
     */
    @RequestMapping("/test1")
    public String test1() {
        System.out.println("test1 执行");
        return "test1";
    }

    /**
     * 重定向发送两次请求,所以filter和Interceptor都会执行两次
     */
    @RequestMapping("/test2")
    public String test2(){
        System.out.println("test2 执行");
        //templates下的只能通过模板引擎访问到, 不能通过重定向或是请求转发访问到
        return "redirect:/test2.html";
    }

    /**
     * forward发送一次请求, 但是会被interceptor会执行两次(这里不明白不知道为什么会执行两次), filter执行一次
     */
    @RequestMapping("/test3")
    public String test3(){
        System.out.println("test3 执行");
        return "forward:/test3.html";
    }
}
