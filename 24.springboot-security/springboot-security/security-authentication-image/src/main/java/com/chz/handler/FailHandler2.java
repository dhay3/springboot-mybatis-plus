package com.chz.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理校验失败
 */
public class FailHandler2 implements AuthenticationFailureHandler {
    @Autowired
    ObjectMapper mapper;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 验证失败重定向登录页面, 或是不用设置
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, "/toLogin");
    }
}
