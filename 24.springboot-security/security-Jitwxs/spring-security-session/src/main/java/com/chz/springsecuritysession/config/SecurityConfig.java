package com.chz.springsecuritysession.config;


import com.chz.springsecuritysession.code.ValidateCodeFilter;
import com.chz.springsecuritysession.config.component.CustomizePermissionEvaluator;
import com.chz.springsecuritysession.config.component.MySessionExpiredStrategy;
import com.chz.springsecuritysession.handler.AuthorizationAccessDeniedHandler;
import com.chz.springsecuritysession.handler.MyAuthenticationFailureHandler;
import com.chz.springsecuritysession.handler.MyAuthenticationSuccessHandler;
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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    /**
     * 保存用户的token到数据库(持久化cookie)
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    /**
     * 登入认证校验失败处理器
     */
    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new MyAuthenticationFailureHandler();
    }

    /**
     * 登入成功处理器
     */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new MySessionExpiredStrategy();
    }

    /**
     * 权限异常处理类
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AuthorizationAccessDeniedHandler();
    }

    /**
     * 自定义权限认证核心类
     */
    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CustomizePermissionEvaluator();
    }

    /**
     * 配置自定义权限认证器到处理器中
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpression() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator());
        return handler;
    }

    /**
     * 图形验证码过滤器
     */
    @Bean
    public OncePerRequestFilter validatedCodeFilter() {
        return new ValidateCodeFilter();
    }

    /**
     * 包含用户的SessionInformation信息
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 主要配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //在UsernamePasswordAuthenticationFilter之前添加自定义的过滤器处理图形验证码校验
        http.addFilterBefore(validatedCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                //添加自定义的权限异常处理
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                //url过滤链
                .authorizeRequests()
                //允许匿名访问,认证通过可以访问所有,除有角色认证的api外
                .antMatchers("/login",
                        "/code/image",
                        "/session/invalid").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .and()
                //登出配置
                .logout().permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "SESSIONID")
                .and()
                //记住我配置
                .rememberMe()
                .tokenValiditySeconds(60)
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .and()
                //session相关配置
                .sessionManagement()
                //session失效跳转到/session/invalid, 两者选一
                .invalidSessionUrl("/session/invalid")
//                .invalidSessionStrategy()
                //允许登入的最大个数,如果在其他客户端使用当前的账号登入,前者的session将失效
                //类似于队列,先进先出
                .maximumSessions(1)
                //如果为true当登入数达到最大值时禁止其他用户登入，是否允许后者登入
                .maxSessionsPreventsLogin(true)
                //旧用户被强制登出之后的处理策略
                .expiredSessionStrategy(sessionInformationExpiredStrategy())
                .sessionRegistry(sessionRegistry());
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/home.html");
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
