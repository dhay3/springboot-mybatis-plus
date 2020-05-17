package com.chz.springbootjson.controller;

import com.chz.springbootjson.pojo.User;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TestJsonController {
    /*
        如果不是使用自己配置的ObjectMapper会使用JacksonAutoConfiguration的中自动配置的ObjectMapper
     */
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/testCusObjectMapper")
    public User get() throws JsonProcessingException {
//    public String get() throws JsonProcessingException {
        User user = new User();
//        user.setName("李文良").setAge(12).setDate(new Date()).setPassword(123);
        user.setName("李文良").setAge(12).setDate(new Date()).setPassword(123);
        System.out.println(user);
        String s = objectMapper.writeValueAsString(user);
//        return s;
        return user;
    }

    @GetMapping("/serialization")
    public String serialization() throws JsonProcessingException {
        String json = "{\"name\":\"张三\",\"age\":\"23\"}";
//        读取json串转pojo
        User user = objectMapper.readValue(json, User.class);
//        User user = new User();
//        user.setName("仲达").setAge(12).setDate(new Date());
        //将对象转为json串
        return objectMapper.writeValueAsString(user);
    }

    @GetMapping("/jsonNode1")
    public String jsonNode1() throws JsonProcessingException {
        String json = "{\"name\":\"张三\",\"age\":\"23\"}";
        //将json串转为JsonNode,树状操作jsonNode
        JsonNode node = objectMapper.readTree(json);
        String name = node.get("name").asText();
        System.out.println(name);
        String age = node.get("age").asText();
        System.out.println(age);
        return name + age;
    }

    @GetMapping("/jsonNode2")
    public String jsonNode2() throws JsonProcessingException {
        String json = "{\"car\":{\"1\":\"bwm\",\"2\":\"benz\",\"3\":\"qq\"},\"plane\":\"747\"}";
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode car = jsonNode.get("car");
        //只能打印单个节点
//        System.out.println(car.asText());
        System.out.println(car.get("2").asText());
        return jsonNode.asText();
    }

    //对应属性上的JsonView相同才会将属性打印
//    @JsonView(User.UserNameFieldView.class)
    @JsonView(User.AllUserFieldView.class)
    @RequestMapping("/view")
    public User JsonView() {
        User user = new User();
        //因为gender没有带@Jsonview所以不会输出
        user.setName("李文良").setAge(12).setDate(new Date()).setPassword(123).setGender("1");
        return user;
    }
    @GetMapping("/str")
    public String getStr(){
        return "hello world";
    }
}
