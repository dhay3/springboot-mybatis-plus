package com.chz.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 配置类
 * 开启swagger2
 */
@Configuration
@EnableSwagger2
public class SwaggerConf {
    //扫描restful api的包路径
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.chz.controller";
    //版本
    public static final String VERSION = "1.1";

    /*
    这里需要注入bean
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //会扫描包下所有@Api注解的controller,不需要指明entity所在的类(entity配置的注解自动生效)
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                //可以根据url路径设置那些请求加入文档, 忽略哪些请求
                .paths(PathSelectors.any())
                .build();
    }

    /*
     设置api的信息 ,这些信息会展示在文档中
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题
                .title("swagger first doc")
                //设置文档描述
                .description("api 接口文档")
                .contact(new Contact("chz", "https://www.chz.com", "kikochz@163.com"))
                .version(VERSION)
                .build();
    }
}
