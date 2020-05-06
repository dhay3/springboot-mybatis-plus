package com.chz.conf.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor2 implements HandlerInterceptor {
    //请求之前调用,如果该方法返回true会执行下一个Interceptor或Controller
    // 如果想要Controller继续执行最后一个Interceptor必需是true
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor2 请求之前");
        return false;
//        return true;

    }
    //请求处理之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor2 请求处理之后");
    }
    //视图渲染完毕(加载完css,js文件和el表达式)且当前Interceptor的preHandle返回是true时才会调用,主要用于抛异常
    //只要preHandle返回为true,无论被拦截的方法抛出异常与否都会执行
    //如果
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Interceptor2 视图处理完毕");
    }
}
