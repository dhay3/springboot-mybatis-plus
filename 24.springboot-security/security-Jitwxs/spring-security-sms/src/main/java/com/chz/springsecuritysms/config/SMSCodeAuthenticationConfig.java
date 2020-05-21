package com.chz.springsecuritysms.config;

import com.chz.springsecuritysms.filter.SMSCodeAuthenticationFilter;
import com.chz.springsecuritysms.config.component.SMSCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 一种登入方式一种配置类
 */
@Configuration
public class SMSCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        SMSCodeAuthenticationFilter smsCodeAuthenticationFilter = new SMSCodeAuthenticationFilter();
        //获取共享的AutenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //配置校验失败处理器
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        //配置校验成功处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        SMSCodeAuthenticationProvider provider = new SMSCodeAuthenticationProvider();
         provider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(provider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
