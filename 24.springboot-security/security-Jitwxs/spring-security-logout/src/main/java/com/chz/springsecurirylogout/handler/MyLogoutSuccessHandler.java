package com.chz.springsecurirylogout.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登出成功处理器
 */
@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private ObjectMapper mapper;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        response.setContentType("application/json;charset=utf-8");
        log.info("退出成功, 用户名{}",username);
        response.sendRedirect("/login");
    }
}
