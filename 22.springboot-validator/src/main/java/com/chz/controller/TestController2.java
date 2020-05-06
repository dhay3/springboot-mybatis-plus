package com.chz.controller;

import com.chz.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class TestController2 {
    /**
     * @ NotBlank 不能为空字符串或是null
     * @ NotNull 不能为null
     * 如果入参不符合要求, 发出BAD_REQUEST 400, 通过这种方式发出的statusCode不能被@ResponseStatus修改
     */
    @ResponseBody
    @RequestMapping("/test1")
    public String test1(@NotBlank(message = "姓名不能为空") String name,
                        @NotNull(message = "年龄不能为空") Integer age) {
        System.out.println("name=" + name + "   age=" + age);
        return "hello world";
    }

    /**
     * @ Validated或是@Valid 要加在入参的时候才会生效加在校验的类上不会生效
     * 但是除了@ConfigurationProperties外
     * 如果User中有复杂对象,要在成员变量上加@Validated
     */
    @RequestMapping("/test6")
    //因为gender没有指定groups所以不会被判断
    public String test6(@Validated(User.IAllFiled.class) User user) {
        System.out.println(user);
        return "test6";
    }


}
