package com.chz.securityathentaication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class SecurityController {

    @GetMapping("/toLogin")
    public String toLogin(Model model) {
        return "login";
    }


    @ResponseBody
    @GetMapping("/success")
    //只用通过设置successHandler的方式才能拿到Authentication
    public Object getPrincipal(Authentication authentication){
        //也可以通过该方法拿到Authentication对象
        //SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        System.out.println(authentication.getCredentials());
        log.info("principal");
        return authentication;
    }
}
