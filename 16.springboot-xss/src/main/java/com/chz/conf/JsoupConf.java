package com.chz.conf;

        import com.chz.util.XssFilter;
        import org.springframework.boot.web.servlet.AbstractFilterRegistrationBean;
        import org.springframework.boot.web.servlet.FilterRegistrationBean;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

        import java.util.HashMap;

@Configuration
public class JsoupConf {
    /**
     * 注册jsoup Filter
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssfilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean =
                new FilterRegistrationBean<>(new XssFilter());
//        filterRegistrationBean.setFilter(new XssFilter());
        //设置在filter中执行的顺序,为第一执行
        filterRegistrationBean.setOrder(1);
        //指明filter是否开启,默认开启
        filterRegistrationBean.setEnabled(true);
        //拦截所有请求
        filterRegistrationBean.addUrlPatterns("/*");
        HashMap<String, String> initParmas = new HashMap<>();
        //路径自行替换为static下的,首页
        initParmas.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*,/index");
        initParmas.put("isIncludeRichText", "true");
        //设置filter的init-param
        filterRegistrationBean.setInitParameters(initParmas);
        return filterRegistrationBean;
    }
}
