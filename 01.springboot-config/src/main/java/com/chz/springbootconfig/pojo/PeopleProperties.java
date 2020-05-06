package com.chz.springbootconfig.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;


/*
通过getter和setter注入
通过这种方式配置的bean最好不用@component这种方式注入(虽然也可以),
推荐采用官方的注入方法@EnableConfigurationProperties
*/
@ConfigurationProperties(prefix = "people")
//JSR 303 校验, 用于检验入参
@Validated
public class PeopleProperties {
    @NotBlank
    private String name;
    @NotNull
    @Max(20)
    private Integer age;
    private Date date;
    private List<Object> list;
    private Map<String, Object> map;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "PeopleProperties{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", list=" + list +
                ", map=" + map +
                '}';
    }
}
