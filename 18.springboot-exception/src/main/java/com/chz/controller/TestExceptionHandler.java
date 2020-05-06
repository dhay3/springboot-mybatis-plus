package com.chz.controller;

import com.chz.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收全局异常
 * @ControllerAdvice 同样可以经过视图解析器
 * springboot处理异常的顺序是 先是自定义的@ControllerAdvice或是@ExceptionHandler
 * 如果加了@ResponseBody就返回json不经过BasicErrorController
 * 如果没加就会经过BasicController然后跳转到对应的异常页
 */
@ControllerAdvice(basePackageClasses = {TestController.class})//可以通过basicPackageClasses处理指定的controller
public class TestExceptionHandler extends ResponseEntityExceptionHandler {
    /*
    方法一
    浏览器和客户端(postman)拿到的都是json串
    方法中没有抛出异常时请求将正常执行, 那么最后的状态码为200

    如果将@ResponseStatus(不管有没有reason)加在异常类上了, @ExceptionHandler捕获异常
    方法执行完毕后将抛出指定statusCode的异常
    该效果与在@ExceptionHandler上使用@ResponseStatus效果一样
     */
    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
    //这里的Exception用于接收错误信息
    private Map<String, Object> handleUserNotExistException(Exception e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", ((UserNotExistException) e).getId());
        //message属性是父类的
        map.put("msg", e.getMessage());
        return map;
    }

    /*
    第二种方法, 如果想要将自定义的属性打印并跳转到错误页面
    通过@ResponseStatus的reaso属性抛出异常
    BasicErrorController会自动捕捉sendError()
    需要写一个AbstractErrorController并重写getErrorAttributes()
    因为@ResponseStatus是在成功方法执行完时改变状态码并抛出异常
    但是这种方法不能将handler中的属性带出,即接收不到model
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "请求不可用")
//    @ExceptionHandler(UserNotExistException.class)
    private ModelAndView handleUserNotExistException1(UserNotExistException e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("customer", "用户名不存在");
        map.put("msg",e.getMessage());
        //通过这种模式跳转到/error, BasicErrorController无法拿到map中设置的值
        return new ModelAndView("forward:/error", map);
    }

    /*
    推荐使用该方法
    第三种方式通过设置javax.servlet.error.status_code
    可以将handler中设置的属性通过WebRequest拿到
     */
        @ExceptionHandler(UserNotExistException.class)
        private ModelAndView handleUserNotExistException2(UserNotExistException e, HttpServletRequest request) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("customer", "用户名不存在");
            //这里不能使用HttpStatusCode的枚举类, 因为源码指定了这个key对应的返回类型是Integer而不能是枚举类
            request.setAttribute("javax.servlet.error.status_code", HttpStatus.BAD_REQUEST.value());
            //将map放入request域中, 这里会被/error接收,所以webRequest.getAttribute("customer",0)能被获取到
            return new ModelAndView("forward:/error", map);
            //如果controller之间跳转带有参数最好使用这种方式
        }
}
