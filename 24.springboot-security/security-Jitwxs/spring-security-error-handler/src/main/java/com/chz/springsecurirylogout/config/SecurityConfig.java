package com.chz.config;
import com.chz.springrememberme.handler.FailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
//开启@PreAuthorize()
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    /**
     * 保存用户的token到数据库
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new FailureHandler();
    }
    /**
     * 凡是后最为url的方法都不需要permitAll(),除了defaultSuccessUrl()
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                //以post方式请求转发到/
                .successForwardUrl("/")
//                .defaultSuccessUrl("/").permitAll()
//                //登入失败跳转url,重定向
//                .failureUrl("/fail1")
//                以post的方式请求转发,一般采用这种方法
                .failureForwardUrl("/fail2")
//                .failureHandler(failureHandler())
                //如果不配置默认/login,post作为检验
//                .loginProcessingUrl("/");
                .and()
                //默认/logout
                //默认/logout,同时会删除数据库中记录的数据
                .logout().permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                //清除Authentication
                .clearAuthentication(true)
                //清除session
                .invalidateHttpSession(true)
                //清除cookie
                .deleteCookies("JSESSIONID")
                .and()
                //记住我默认2周
                .rememberMe()
                //设置token有效时间
                .tokenValiditySeconds(60)
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .and()
                .csrf()
                .disable();
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
