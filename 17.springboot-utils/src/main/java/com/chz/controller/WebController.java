package com.chz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@Controller
public class WebController {
    @ResponseBody
    @RequestMapping("/hello")
    public String testWebUtils(HttpServletRequest request) throws FileNotFoundException {
        ServletContext context = request.getServletContext();
        String realPath = WebUtils.getRealPath(context, "/hello");
        System.out.println(realPath);
        //获取session中的value
        Object hello = WebUtils.getSessionAttribute(request, "hello");
        return "hello world";
    }
}
