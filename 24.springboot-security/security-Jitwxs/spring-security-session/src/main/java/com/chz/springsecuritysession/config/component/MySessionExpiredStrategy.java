package com.chz.springsecuritysession.config.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 自定义session失效策略
 */
public class MySessionExpiredStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    private ObjectMapper mapper;
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
//        Object principal = event.getSessionInformation().getPrincipal();
//        System.out.println(principal);
        HttpServletResponse response = event.getResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>(16);
        //event.getSessionInformation().getLastRequest()获取最后一次请求发送的时间
        map.put("msg","您的账号已经在别的地方登录，当前登录已失效。如果密码遭到泄露，请立即修改密码！"+event.getSessionInformation().getLastRequest());
        map.put("code","0");
        response.getWriter().print(map);
    }
}
