package com.chz.springsecuritysms.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码错误
 */
public class CodeValidateException extends AuthenticationException {
    private static final long serialVersionUID = 1L;
    public CodeValidateException(String msg) {
        super(msg);
    }
}
