package com.chz.log;

import com.chz.annotaion.Log;
import com.chz.dao.SysDao;
import com.chz.util.HttpContextUtils;
import com.chz.util.IPUtils;
import com.chz.domain.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/*
需要引入aop依赖
@Aspect表示当前类式aspect(before,after,afterReturning,afterThrowing,aroud)所在类
使用环绕通知来测试方法的执行时间和方法的参数记录
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    //切记只用是spring中的组件可以@Autowired不管是调用的类还是被使用的类
    @Autowired
    private SysDao sysDao;

    /**
     * 在所有标注了该注解的方法,执行环绕通知
     * joinpoint.getArgs() 获取传入方法的参数
     * joinpoint.getTarget() 获取被代理方法所在的类
     * joinpoint.getSignature().getName() 获取被代理方法名
     * joinpoint.getSignature().getDeclaringTypeName() 获取被代理方法所在类的全路径名
     * 通过强换可以拿到方法
     *
     * @param joinpoint 获取信息
     * @return 可以自定义返回值
     */
    @Around("@annotation(com.chz.annotaion.Log)")
    //如果式Around必须要采用ProceedingJoinPoint 不能式JoinPoint
    public Object around(ProceedingJoinPoint joinpoint) {
        joinpoint.getSignature().getName();
        log.info("进入测试=====================================================");
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            //执行代理目标函数,proceed之上before,之下afterReturning
            result = joinpoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        //获取执行的时间
        long time = System.currentTimeMillis() - beginTime;
        //保存信息
        saveLog(joinpoint, time);
        //返回值能被改变
        return result;
    }

    //因为@Around已经做了判断,就不用判断方法是否加注解
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        //获取代理的目标函数
        //通过这种方式获取指定方法
        SysLog sysLog = new SysLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        if (null != annotation) {
            //调用定义注解中value()方法
            sysLog.operation(annotation.value());
        }
        //获取代理方法所在的类的类名
        String className = joinPoint.getTarget().getClass().getName();
        System.out.println("类名" + className);
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("方法名" + method);
        sysLog.method(className + ":" + methodName);
        //获取代理对象请求的参数
        Object[] args = joinPoint.getArgs();
        System.out.println("请求参数" + Arrays.asList(args));
        LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        //获取代理指定方法的参数的形参名
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        System.out.println("请求的参数名" + Arrays.asList(parameterNames));
        if (null != args && null != parameterNames) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                builder.append(parameterNames[i]).append(":").append(args[i]);
            }
            sysLog.params(builder.toString());
        }
        HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
        sysLog.ip(IPUtils.getIpAddr(httpServletRequest));
        //模拟一个用户
        sysLog.username("chz");
        sysLog.time((int) time);
        sysLog.createTime(new Date());
        //保存信息到数据库
        sysDao.saveSysLog(sysLog);
    }
}
