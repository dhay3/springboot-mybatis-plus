package com.chz;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动类(配置源)
 */
@SpringBootApplication
public class SpringbootSpringbootApplication {

//    public static void main(String[] args) {
//        SpringApplication application =
//                new SpringApplication(SpringbootSpringbootApplication.class);
//        //设置banner 是否开启
//        application.setBannerMode(Banner.Mode.OFF);
//        //设置是否以web环境启动
////        application.setWebApplicationType(WebApplicationType.NONE);
//        //设置使用配置文件
//        application.setAdditionalProfiles("dev");
//        //设置是否懒加载, 默认false
//        application.setLazyInitialization(true);
//        //是否打印该项目的信息到控制台中, 即com.chz.*
//        application.setLogStartupInfo(false);
//        application.run(args);
//    }

    /**
     * springboot 支持fluent自定义启动类
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringbootSpringbootApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .profiles("dev")
                .web(WebApplicationType.NONE)
                .lazyInitialization(true)
                .logStartupInfo(false)
                    .run(args);
    }

}
