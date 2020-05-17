package com.chz.securityathentaication.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//没有实际作用,只是标记
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    //可以显示的注入的encoder或是通过PasswordEncoderFactories.createDelegatingPasswordEncoder();来声明加密
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 请求拦截与放行
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //和shiro一样要在拦截所有之前放行
                //放行登入页面
//                .antMatchers("/toLogin").permitAll()
                .antMatchers("/toLogin").permitAll()
                //所有请求都被拦截
                .anyRequest()
                //需要认证
                .authenticated()
                .and()
                .formLogin()
                //如果没有验证跳转的url或页面
                .loginPage("/toLogin")
                //默认是/login, 具体查看UsernamePasswordAuthenticationFilter
                //用户验证的url, 即点击登入的url,和shiro一样默认放行,该url会被自动处理,无需再写一个controller
                .loginProcessingUrl("/login")
                //验证成功跳转url,由于是forward方式所以对应的mapping也要是post
                .successForwardUrl("/success");
    }

    /**
     * 推荐将静态资源单独分离
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略拦截,ant风格
        web.ignoring()
                //放行静态资源
//                .antMatchers("/login.html")
                .antMatchers("/index","/")
                .antMatchers("/css/**");
    }

    /**
     * 用户认证
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
}
