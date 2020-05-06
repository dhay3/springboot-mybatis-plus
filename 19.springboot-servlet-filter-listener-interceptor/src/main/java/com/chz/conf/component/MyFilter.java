package com.chz.conf.component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
/*
通过@ServletComponentScan(basePackages = {"com.chz.conf.component"})
@Component
FilterRegistrationBean
filter会拦截请求和响应, 但是不会拦截请求转发的请求, 重定向的还是会被拦截
 */
//@Component
@WebFilter("/*")
public class MyFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //请求前处理
        System.out.println("doFilter 之前");
        chain.doFilter(req, resp);
        //响应前处理
        System.out.println("doFilter 之后");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
