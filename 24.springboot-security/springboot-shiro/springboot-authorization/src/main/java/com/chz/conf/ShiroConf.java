package com.chz.conf;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.chz.conf.component.CustomizeRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro配置类
 * 三大核心 ShiroFilterFactoryBean: filter 过滤url,交给SecurityManager代理
 * SecurityManager: 代理filter和Realm
 * Realm: 连接数据库的桥梁,权限与用户认证
 */
@Configuration
public class ShiroConf {
    /**
     *
     * @param manager 安全代理
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //关联SecurityManager
        shiroFilterFactoryBean.setSecurityManager(manager);
        //通过set的方式设置url会放行请求
        //登入的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登入成功的url(由于请求是通过ajax发送的,所以不会生效,这里只起请求放行作用)
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //设置未授权跳转的url
        //shiroFilterFactoryBean.setUnauthorizedUrl("/");
        //设置过滤,必须使用LinkedHashMap,否则会出现资源加在一次就被拦截
        LinkedHashMap<String, String> filterChain = new LinkedHashMap<>();
    /*
    注意 anon 必须放在authc之上
    支持ant
    常用过滤器:
    1. anon (anonymous): 无需认证(登入)可以访问资源
    2. authc (authority): 必须认证才能访问
    3. user : 如果使用rememberMe的功能可以访问, 同时认证通过的也可以访问
    4. perms (permissions): 该资源必须拥有对应的资源权限才能访问
    5. roles: 该资源源必须拥有对应的角色才能访问
     */
        //filter会拦截所有请求,包括静态资源的请求
//        filterChain.put("/css/**", "anon");
//        filterChain.put("/js/**", "anon");
//        filterChain.put("/img/**", "anon");
        filterChain.put("/assert/**","anon");
        //放行webjars
        filterChain.put("/webjars/**","anon");
        //放行druid
        filterChain.put("/druid/**", "anon");
        //登出放行
        filterChain.put("/logout", "anon");
        filterChain.put("/register","anon");
        filterChain.put("/", "anon");
        //除上述url外都必须认证通过才能访问, 为通过认证自动访问setLoginUrl()设置的url
//        filterChain.put("/**", "authc");
        filterChain.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
        return shiroFilterFactoryBean;
    }

    /**
     * 关联Realm
     * @param userRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Realm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        //设置cookie管理器
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    /**
     * 用户认证与权限认证
     * 指定Shiro加密方式
     */
    @Bean
    public Realm userRealm() {
        CustomizeRealm realm = new CustomizeRealm();
        //告诉Shiro使用MD5加密
        realm.setCredentialsMatcher(MD5());
        return realm;
    }

    /**
     *  MD5加密
     */
    private HashedCredentialsMatcher MD5() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        matcher.setHashAlgorithmName("MD5");
        //加密次数
        matcher.setHashIterations(5);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }


    /**
     * cookie管理器
     * @return
     */
    private CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥
        byte[] cipherKey = Base64.decode("wrjUh2ttBPQLnT4JVhriug==");
        //设置密匙, 防止有人恶意修改cookie
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
    /**
     * 设置cookie相关属性
     * @return
     */
    private SimpleCookie rememberMeCookie(){
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        //如果httpOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        cookie.setHttpOnly(true);
        return cookie;
    }

    /**
     * 注解设置权限依赖类
     * 可用注解  1 @RequiresPermission 必须有指定权限
     *         2 @RequiresRole 必须具有指定角色
     *        3 @RequiresAuthentication 已经通过用户认证,即realm的doGetAuthenticationInfo
     *       4 @RequiresUser 已验证或者是已记住我的用户
     *      5 @RequiresGuest 没有身份验证或通过记住我登入过, 即游客身份
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor advisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    /**
     * 注解设置权限依赖类
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator creator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 开始shiro标签
     */
    @Bean
    public ShiroDialect dialect() {
        return new ShiroDialect();
    }
}
