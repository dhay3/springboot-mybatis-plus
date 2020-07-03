package com.chz.springbootconfig.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*
自定义的properties(不包括yml)可以配合@ConfigurationProperties一起使用
这里不知道为什么要加@Component才会生效???无法通过@EnableConfigurationProperites注入值
如果不加可以@ConfigurationProperties使用@Value的方式来注入值,但是同样的前提是必须在ioc中
*/
@Component
@PropertySource("classpath:test.properties")
@ConfigurationProperties(prefix = "test")
public class TestProperty {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
