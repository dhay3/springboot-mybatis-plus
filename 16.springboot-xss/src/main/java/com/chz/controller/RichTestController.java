package com.chz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试富文本
 */
@Controller
public class RichTestController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/f1")
    public String f1(@RequestParam("richText") String s, Model model) {
        System.out.println(s);
        model.addAttribute("f1", s);
        return "test";
    }

    @PostMapping("/f2")
    @ResponseBody
    public String f2(@RequestParam("richText") String s, HttpServletRequest request) {
        System.out.println(request.getParameter("richText"));
        System.out.println(s);
        return s;
    }
}
