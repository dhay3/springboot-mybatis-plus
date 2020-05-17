package com.chz.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Configuration
    public static class SecurityConf extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //使用base64加密, 使用自定义的url，表单式登入
            http.formLogin(form->form.loginPage("/login").permitAll())//表单方式登入
//            http.formLogin()//表单方式登入
            //使用http basic 认证使用base64加密，弹窗式登入界面
//            http.httpBasic()
//                    .and()
                    .authorizeRequests()//授权配置
                    .anyRequest()//所有请求
                    .authenticated();//都需要认证
        }
    }
}
