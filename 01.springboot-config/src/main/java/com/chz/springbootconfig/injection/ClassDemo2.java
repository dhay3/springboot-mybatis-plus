package com.chz.springbootconfig.injection;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
//@Order不决定加载的顺序,只决定内执行的顺序
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClassDemo2 {
    private String name = "ClassDemo2";

    public ClassDemo2() throws InterruptedException {
        System.out.println(name);
        TimeUnit.SECONDS.sleep(2);
    }
}
