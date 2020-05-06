package com.chz.conf;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

/**
 * Mvc配置类
 */
@Configuration
public class MvcConf implements WebMvcConfigurer {
    @Bean
    public MyErrorAttributes errorAttributes() {
        return new MyErrorAttributes();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }

    private static class MyErrorAttributes extends DefaultErrorAttributes {
        @Override
        public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
            Object map =
                    webRequest.getAttribute("msg", RequestAttributes.SCOPE_REQUEST);
            Map<String, Object> superAttr = super.getErrorAttributes(webRequest, includeStackTrace);
            if (ObjectUtils.isEmpty(map)) {
                return superAttr;
            }
            superAttr.put("msg", map);
            return superAttr;
        }
    }
}
