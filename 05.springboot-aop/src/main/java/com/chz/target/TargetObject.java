package com.chz.target;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class TargetObject {
    /*
    try-catch 后正常运行代码不存在异常,只是打印异常, 所以@AfterThrowing将不会被执行
    如果是抛出异常, 那么@AfterThrowing将会执行
     */
    public void targetMethod() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("");
        System.out.println("哈哈哈");
    }
}
