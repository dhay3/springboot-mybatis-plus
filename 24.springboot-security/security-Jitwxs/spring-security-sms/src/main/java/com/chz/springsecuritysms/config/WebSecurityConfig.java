package com.chz.springsecuritysms.config;


import com.chz.springsecuritysms.filter.ImageCodeValidateFilter;
import com.chz.springsecuritysms.config.component.CustomizePermissionEvaluator;
import com.chz.springsecuritysms.filter.SMSCodeAuthenticationFilter;
import com.chz.springsecuritysms.filter.SMSCodeValidateFilter;
import com.chz.springsecuritysms.handler.AuthorizationAccessDeniedHandler;
import com.chz.springsecuritysms.handler.MyAuthenticationFailureHandler;
import com.chz.springsecuritysms.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
//开启@PreAuthorize()验证用户权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;
    @Autowired
    private MyAuthenticationFailureHandler failureHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SMSCodeAuthenticationConfig smsCodeAuthenticationConfig;


    /**
     * 保存用户的token到数据库
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //指定数据源
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AuthorizationAccessDeniedHandler();
    }

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CustomizePermissionEvaluator();
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpression() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator());
        return handler;
    }

    /**
     * 校验图形验证码
     */
    @Bean
    public ImageCodeValidateFilter imageCodeFilter() {
        return new ImageCodeValidateFilter();
    }

    /**
     * 短信验证码
     */
    @Bean
    public SMSCodeValidateFilter SMSCodeFilter() {
        return new SMSCodeValidateFilter();
    }

    /**
     * 凡是后最为url的方法都不需要permitAll(),除了defaultSuccessUrl()
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加图形验证码过滤器
        http.addFilterBefore(imageCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                //添加短信验证码过滤器
                .addFilterBefore(SMSCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .authorizeRequests()
                //放行图形验证码,与短信验证码
                .antMatchers("/login",
                        "/checkPerms",
                        "/code/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout().permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .tokenValiditySeconds(60)
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .and()
                .csrf()
                .disable()
                //引入其他登入校验配置类
                .apply(smsCodeAuthenticationConfig);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/home.html", "/webjars/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            //自定义加密, 明文加密明文返回
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }
}
