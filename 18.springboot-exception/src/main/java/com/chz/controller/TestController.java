package com.chz.controller;

import com.chz.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class TestController {
    @RequestMapping("/test1")
    public ResponseEntity<?> testControllerAdvice(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new UserNotExistException(id);
        }
        //能设置返回的状态码,和请求头 可以拆解为@ResponseBody和@ResponseStatus
        //如果设置的HttpStatus是有异常的status,body将无效,泛型是body的类型
        return new ResponseEntity<String>("正常", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    //指定返回的状态码, 避免和ResponseEntity一起使用, 即使请求正常返回时状态码也会被覆盖
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping("/test2")
    public String testRes() {
        return "你好 世界";
    }

    //如果加了reason属性reason就一定会发出异常(sendError), 而异常的statusCode就是指定的
    //是在方法运行后,修改状态码,抛出异常
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "请求不可用")
    @RequestMapping("/test3")
    public String testSuccess() {
        return "success";
    }

    /*
     * @ResponseBody 只有在请求正常或是带有reason时才会将状态码该指定的, 方法异常时不会修改状态码
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CONTINUE)//100
    @RequestMapping("/test4")
    public void testStatusCode() {
        throw new RuntimeException();
    }

}
