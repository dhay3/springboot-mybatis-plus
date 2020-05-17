package com.chz.springsecuritysession.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码错误
 */
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = 1L;
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
