package com.chz.controller;

import com.chz.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class LogController {
    //如果使用了lombook的@Slf4j就可以不用每次都写出loggerFactory
//    private Logger log = LoggerFactory.getLogger(this.getClass());
    @ResponseBody
    @GetMapping("/login")
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        //logback没有FATAL
        //日志级别为TRACE<DEBUG<INFO<WARN<ERROR<FATAL,低于日志级别不会显示日志信息
        log.trace("TRACE...........");
        log.debug("DEBUG...........");
        log.info("INFO.................");
        log.warn("WARN.............");
        log.error("ERROR..............");
        HashMap<String, Object> map = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!Objects.equals(username, " ") && Objects.equals(password, " ")) {
            User user = new User(username, password);
            request.getSession().setAttribute("user", user);
            map.put("result", "1");
        } else {
            map.put("result", "2");
        }
        return map;
    }
}
