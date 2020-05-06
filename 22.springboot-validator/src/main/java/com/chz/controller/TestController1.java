package com.chz.controller;

import com.chz.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.Map;

/**
 * @ Valid 和 @Validated 比较 @Valid有jsr 303 提供,而 @Validated有spring提供
 * 效果一样但是,后者比前者功能更强大, 提供分组(类似于@JsonView), 验证排序
 */
@Slf4j
@Validated
@Controller
public class TestController1 {

    /**
     * 如果属性加了groups使用@Validated或是@Valid 不指定groups,那么带有groups的属性验证不会生效
     */
    @ResponseBody
    @RequestMapping("/test2")
    public String test2(@Validated(User.IAllFiled.class) User user, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .forEach(System.out::println);
        }
        System.out.println(user);
        return "test2";
    }

    /**
     * @ Valid 不支持分组校验
     */
    @ResponseBody
    @RequestMapping("/test3")
    public String test3(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .forEach(System.out::println);
        }
        System.out.println(user);
        return "test3";
    }

    /**
     * BindingResult是用于接收前一个参数校验(validate或valid)错误信息, 前端将不再显示错误信息
     * statusCode == 200, 后端也不会抛出异常
     * 可以通过抛出一个异常或是, 使用@ResponseStatus指定code和reason来达到跳转到错误页面
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "参数请求错误")
    @ResponseBody
    @RequestMapping("/test4")
    public String test4(@Validated User user, BindingResult result) {
        //判断是否有error
        if (result.hasErrors()) {
            int errorCount = result.getErrorCount();
            log.info("异常个数=" + errorCount);
            //获取全局与属性的错误
            //result.getAllErrors();
            //只获取属性校验的错误
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("message:" + fieldError.getDefaultMessage());
            }
        }
        System.out.println(user);
        return "test4";
    }

    @ResponseBody
    @RequestMapping("/test5")
    public Map<String, Object> test5(@Validated User user, BindingResult result) {
        Map<String, Object> model = null;
        if (result.hasErrors()) {
            model = result.getModel();
        }
        System.out.println(user);
        return model;
    }


}
