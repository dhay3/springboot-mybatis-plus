package com.chz.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class MyPointcut {
    //@Pointcut所在的类无需注入ioc,advice调用该pointcut的signature
    @Pointcut("execution(* com.chz.target.TargetObject.*())")
    public void pointcutMethod() {
    }
}
