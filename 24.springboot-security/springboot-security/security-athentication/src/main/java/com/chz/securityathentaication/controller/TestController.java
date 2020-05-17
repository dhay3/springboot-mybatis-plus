package com.chz.securityathentaication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class TestController {

    @GetMapping("/toLogin")
    public String toLogin(Model model) {
        return "login";
    }

//    @ResponseBody
//    @PostMapping("/success")
//    public String success(){
//        log.info("success");
//        return "success";
//    }
//    @ResponseBody
    @PostMapping("/success")
    public Object getPrincipal(@AuthenticationPrincipal Authentication authentication){
        System.out.println(authentication);
        System.out.println(authentication.getCredentials());
        log.info("principal");
        return authentication;
    }

}
