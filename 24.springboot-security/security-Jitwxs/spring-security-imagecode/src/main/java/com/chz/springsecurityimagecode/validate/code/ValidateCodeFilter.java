package com.chz.springsecurityimagecode.validate.code;

import com.chz.springsecurityimagecode.controller.ValidateController;
import lombok.extern.slf4j.Slf4j;
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
import java.io.IOException;

/**
 * 自定义过滤器,如果没有权限验证,每发送一次请求调用一次
 */
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {
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
            } catch (ValidateCodeException e) {
                //如果校验失败,请求转发
//                request.getRequestDispatcher("/fail").forward(request,response);
                failureHandler.onAuthenticationFailure(request,response,e);
//                这里如果不return会放行请求,验证码不争取也会校验成功,必须要return
                return;
            }
        }
        //放行请求
        filterChain.doFilter(request, response);
    }

    /**
     *  校验验证码
     * @param servletWebRequest 通过ServletWebRequest可以拿到webRequest(所有的web request请求)是一个适配器
     * @throws ServletRequestBindingException
     */
    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //通过session key获取到对应的imageCode
        ImageCode codeInSession = (ImageCode) sessionStrategy
                .getAttribute(servletWebRequest, ValidateController.SESSION_KEY_IMAGE_CODE);
        //从request获取name为imageCode的值
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
        if (StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空");
        }else if (ObjectUtils.isEmpty(codeInSession)){
            throw new ValidateCodeException("验证码不存在");
        }else if (codeInSession.isExpire()){
            //如果验证码超时失效,删除session中存的值
            sessionStrategy.removeAttribute(servletWebRequest, ValidateController.SESSION_KEY_IMAGE_CODE);
            throw new ValidateCodeException("验证码过期");
        }else if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(),codeInRequest)){
            throw new ValidateCodeException("验证码不正确");
        }
        //如果校验通过, 删除session中的imageCode
        sessionStrategy.removeAttribute(servletWebRequest,ValidateController.SESSION_KEY_IMAGE_CODE);
    }

    /**
     * 过滤请求
     */
    private boolean isProtectedUrl(HttpServletRequest request) {
        //url 获取的会包括协议和端口号, uri 不包括协议和端口号
        return StringUtils.equalsIgnoreCase("/login", request.getRequestURI())
                && StringUtils.equalsIgnoreCase("post", request.getMethod());
    }
}
