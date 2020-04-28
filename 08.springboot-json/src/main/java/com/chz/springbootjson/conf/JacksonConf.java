package com.chz.springbootjson.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/*
jackson是将pojo转为json,或是json对象转为pojo
json是对象,json串是一条字符串
 */
@Configuration
public class JacksonConf {
    /*
    ObjectMapper负责pojo转json, 或是json转pojo
    起ObjectReader和ObjectWriter的作用
     */
//    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        //设置json转pojo,pojo转json 日期的格式化
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return  mapper;
    }
}
