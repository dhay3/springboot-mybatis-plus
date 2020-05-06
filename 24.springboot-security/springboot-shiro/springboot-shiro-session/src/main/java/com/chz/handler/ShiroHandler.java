package com.chz.handler;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * 处理shiro 权限认证异常
 */
@ControllerAdvice
public class ShiroHandler {
    @ExceptionHandler(AuthorizationException.class)
    private ModelAndView handleAuthorizationException(AuthorizationException e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "你没有权限这么做");
        //如果要自定义传属性到getAttributes()中必须通过这种方法,不能通过@RequestStatus
        map.put("javax.servlet.error.status_code", HttpStatus.FORBIDDEN.value());
        return new ModelAndView("forward:/error", map);
    }
}
