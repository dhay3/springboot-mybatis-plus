package com.chz.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@ControllerAdvice也是一个@Component, 所以可以通过@Oder来表明注入ioc的顺序
@Order(value = Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice//同样会经过视图解析器
@RestControllerAdvice//对比@restController
public class MyHandler {
    /**
     * 处理普通传参, 校验异常
     * 用于接收校验信息错误的handler
     */
//    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException e){
        StringBuilder msg = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            //获取属性在bean中的路径, 即test1.name , test1.
            Path path = violation.getPropertyPath();
            System.out.println(path.toString());
            //test1.name: 姓名不能为空, test1.age: 年龄不能为空
            //分隔错误信息
            String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            //拼接信息
            msg.append(strings[1]).append(violation.getMessage()).append(",");
        }
        return msg.substring(0,msg.length()-1);
    }

    /**
     * 处理实体类请求参数校验异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public List<String> handleBindException(BindException exception){
        return exception.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
