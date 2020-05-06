package com.chz.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController2 {
    @RequestMapping("/test5")
    public String test5() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("异常被捕获");
            e.printStackTrace();
        }
        return "hello world";
    }

    @RequestMapping("/test6")
    public void test6() throws IOException {
        System.out.println("controller IOException throws");
        throw new IOException();
    }

    @RequestMapping("/test7")
    public void test7()  {
        try {
            throw new IOException();
        } catch (IOException e) {
            System.out.println("controller IOException try-catch");
            e.printStackTrace();
        }
    }
    /*
    @ExceptionHandler 能接收抛出的checked exception,但是如果异常被try-catch就不能被接收
     */
    @ExceptionHandler(IOException.class)
    public void catchIoException() {
        System.out.println("handler  IOException");
    }
    /*
    如果unchecked exception 被try-catch了就不能被@ExceptionHandler接收
     */
    @ExceptionHandler(RuntimeException.class)
    public void catchRuntimeException() {
        System.out.println("handler  RuntimeException");
    }
}
