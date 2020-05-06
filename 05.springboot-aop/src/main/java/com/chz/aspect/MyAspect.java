package com.chz.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect//标明该类是一个切面
@Component//联想spring需要将advice包装类注入到ioc, 所以这里要加@Component
public class MyAspect {
    @Pointcut("execution(* com.chz.target.TargetObject.*())")
    public void pointcutMethod() {
    }

    /*
    可以将advice和pointcut分离在不同的类,pointcut所在的类无需注入到ioc中
     */
//    @Before("execution(* com.chz.target.TargetObject.*())")
    //aspectj.lang 下的JoinPoint
    public void advice(JoinPoint joinPoint) {
        System.out.println("advice invoke ...");
    }

    /*
    可以通过returning属性拿到代理对象的方法的返回值
     */
//    @AfterReturning(pointcut = "com.chz.pointcut.MyPointcut.pointcutMethod()",
//    returning = "retVal")
    public void afterReturningMethod(Object retVal) {
        System.out.println(retVal);
        System.out.println("afterReturning invoke ...");
    }

    //    @AfterThrowing(pointcut ="com.chz.pointcut.MyPointcut.pointcutMethod()",
//    throwing = "e")
    public void exceptionMethod(Exception e) {
        System.out.println(e.getMessage());
        System.out.println("afterThrowing invoke ...");
    }

    /*
    相当于finally 在异常后也会执行
     */
//    @After("com.chz.pointcut.MyPointcut.pointcutMethod()")
    public void AfterMethod() {
        System.out.println("after invoke ...");
    }

    /*
    方法的参数必须是ProceedingJointPoint
     */
    @Around("com.chz.pointcut.MyPointcut.pointcutMethod()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        Object proceed = null;
        try {
            //在proceed之前的部分就相当于@Before修饰的方法
            System.out.println("Before通知");
            proceed = joinPoint.proceed();//执行被代理对象的方法,并拿到返回值的返回值
            System.out.println("返回值" + proceed);
            System.out.println("函数名" + joinPoint.getSignature().getName());
            System.out.println("参数个数" + joinPoint.getArgs().length);
            System.out.println("被代理对象" + joinPoint.getTarget());
            System.out.println("被代理对象的全路径名" + joinPoint.getSignature().getDeclaringTypeName());
            //在proceed之后的部分就相当于@AfterReturning修饰的方法
            System.out.println("AfterReturning通知");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //@AfterThrowing修饰的方法
            System.out.println("异常通知");
        } finally {
            //@After
            System.out.println("最终通知");
        }
        //可以在通知中被代理对象的方法的返回值,要求返回值类型与被代理对象的方法的类型一致
        return proceed;
    }
}
