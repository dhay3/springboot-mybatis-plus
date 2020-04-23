package com.chz.springbootconfig.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class PeopleValue {
    //通过@Value单个注入值
    @Value("${people.name}")
    private String name;
    @Value("${people.age}")
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
        return "PeopleValue{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
