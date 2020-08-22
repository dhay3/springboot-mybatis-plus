package com.chz.conf;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//方法一 设置全局的cors配置
//@Configuration
public class CorsConf implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //addMapping对应该项目下的url
        //allowedOrigins表示允许接收哪个域传信息, *表示所有域
        //表示那些url允许跨域接收信息
//        registry.addMapping("/cors").allowedOrigins("http://localhost:8080");
        registry.addMapping("/**").allowedOrigins("*");
    }
}
