package com.chz.securityathentaication2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理校验成功
 */
public class SuccessHandler2 implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper mapper;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 返回指定url
     * 或是通过setSucessUrl()来指定
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, "/success");
    }
}
