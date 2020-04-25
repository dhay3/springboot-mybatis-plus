package com.chz.controller;

import com.chz.pojo.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @GetMapping("/list")
    public String list(Model model) {
        List<Account> accounts = Arrays.asList(new Account("zs", 20),
                new Account("ls", 21),
                new Account("dy", 111));
        model.addAttribute("accounts",accounts);
        return "list";
    }
    @GetMapping("/elvis")
    public String elvis(Model model){
        model.addAttribute("withOrWithOutU",true);
        //不能用数字作为key,thymeleaf拿不到值
        model.addAttribute("BOrNot2B",false);
        model.addAttribute("heart",null);
        model.addAttribute("date",new Date());
        return "elvis";
    }
}
