package com.chz.springsecurity01.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class LoginController {
    @RequestMapping("/")
    public String showHome() {
        //获取当前登录用户的用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户：" + name);
        return "home";
    }

    @GetMapping("/login")
    public String toLogin(){
        //返回静态页面使用redirect
        return "login";
    }
    //这里对应数据库中的权限是spring规定的格式ROLE_xxx
    @ResponseBody
    //需要添加@EnableGlobalMethodSecurity(prePostEnabled = true)
    //@PreAuthorize用于判断用户是否有指定的权限, 没有就不能访问
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String printAdmin(){
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }

    @GetMapping("/logout")
    public String hello(){
        return "redirect:/login";
    }
}
