package com.chz.springsecuritysms.config.component;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SMSCodeAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //未校验的token
        SMSCodeAuthenticationToken smsCodeAuthenticationToken = (SMSCodeAuthenticationToken)authentication;
        //获取手机号码
        String mobile = authentication.getPrincipal().toString();
        //更具手机号码查询用户,如果用户不存抛出异常,交给自定义的AuthenticationFailureHandler处理
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        //校验通过, 设置token信息
        SMSCodeAuthenticationToken authenticationResult = new SMSCodeAuthenticationToken(mobile,userDetails.getAuthorities());
        authenticationResult.setDetails(userDetails);
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //判断authentication是不是SMSCodeAuthenticationToken的子类或子接口
        return SMSCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
