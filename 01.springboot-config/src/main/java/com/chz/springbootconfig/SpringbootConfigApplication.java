package com.chz.springbootconfig;

import com.chz.springbootconfig.pojo.PeopleProperties;
import com.chz.springbootconfig.pojo.TestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@EnableConfigurationProperties bean会被注册到ioc中
@EnableConfigurationProperties({PeopleProperties.class, TestProperty.class})
//主入口类同时也一个配置类
@SpringBootApplication
@RestController
public class SpringbootConfigApplication {
    @Autowired
    PeopleProperties peopleProperties;
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(
                SpringbootConfigApplication.class);
        //关闭banner打印,不会关闭test中的
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
    @RequestMapping("/index")
    public String index(){
        return "configurationProperties"+ peopleProperties.getName();
    }

}
