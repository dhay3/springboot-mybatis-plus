package com.chz.springsecuritysms.filter;

import com.chz.springsecuritysms.code.ImageCode;
import com.chz.springsecuritysms.code.CodeValidateException;
import com.chz.springsecuritysms.code.SMSCode;
import com.chz.springsecuritysms.controller.ValidateController;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * 自定义短信校验,同图形验证码校验
 */
public class SMSCodeValidateFilter extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @Autowired
    AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //只对登入请求校验
        if (isProtectedUrl(request)) {
            //将httpServlet包装为ServletWebRequest
            try {
                validateCode(new ServletWebRequest(request));
            } catch (CodeValidateException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        //放行请求
        filterChain.doFilter(request, response);
    }

    /**
     * 校验验证码
     *
     * @param servletWebRequest 通过ServletWebRequest可以拿到webRequest(所有的web request请求)是一个适配器
     * @throws ServletRequestBindingException
     */
    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //获取smsCode
        String CodeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "smsCode");
        //从request获取name为mobile的值
        String mobile = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "mobile");
        SMSCode codeInSession = (SMSCode) sessionStrategy
                .getAttribute(servletWebRequest, ValidateController.SESSION_KEY_SMS_CODE + mobile);
        if (StringUtils.isBlank(CodeInRequest)) {
            throw new CodeValidateException("验证码不能为空");
        } else if (ObjectUtils.isEmpty(codeInSession)) {
            throw new CodeValidateException("验证码不存在");
        } else if (codeInSession.isExpire()) {
            throw new CodeValidateException("验证码过期");
        } else if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), CodeInRequest)) {
            throw new CodeValidateException("验证码不正确");
        }
        //如果校验通过, 删除session中的imageCode
        sessionStrategy.removeAttribute(servletWebRequest, ValidateController.SESSION_KEY_SMS_CODE);
    }

    /**
     * 过滤请求
     */
    private boolean isProtectedUrl(HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase("/login/mobile", request.getRequestURI())
                && StringUtils.equalsIgnoreCase("post", request.getMethod());
    }
}