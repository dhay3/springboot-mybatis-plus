package com.chz.conf;

import com.chz.conf.component.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Myconf implements WebMvcConfigurer {
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/*");
        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/*");
    }

    //配置嵌入式的servlet容器
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
            //定制嵌入式的servlet容器相关规则
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.setPort(8085);
            }
        };
    }

    @Bean
    public ServletRegistrationBean<MyServlet> servletServletRegistrationBean() {
        ServletRegistrationBean<MyServlet> servlet =
                new ServletRegistrationBean<>(new MyServlet(), "/myServlet");
        servlet.setLoadOnStartup(1);
        return servlet;
    }

    @Bean
    public FilterRegistrationBean<MyFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<MyFilter> filter =
                new FilterRegistrationBean<>(new MyFilter());
        filter.addUrlPatterns("/*");
//        filter.setUrlPatterns(Arrays.asList("/*"));
        return filter;
    }

    @Bean
    public ServletListenerRegistrationBean<MyListener> listenerServletListenerRegistrationBean() {
        return new ServletListenerRegistrationBean<>(new MyListener());
    }

}
