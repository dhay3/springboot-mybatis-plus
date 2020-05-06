package com.chz.controller;


import com.chz.entity.User;
import com.chz.service.IUserService;
import com.chz.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */
@Slf4j
@Controller
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }
    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody User user){
        String password = MD5Utils.encrypt(user.getPassword(),user.getName());
        user.setPassword(password);
        userService.save(user);
        return "注册成功";
    }
    @ResponseBody
    @PostMapping(value = "/login")
    //可以将多个参数绑定到一个对象,将另外一个参数绑定到一个简单参数
    public String login(User user,
                        @RequestParam(value = "rememberMe",required = false) Boolean rememberMe) {
        System.out.println(rememberMe);
        //调用两次数据库貌似储在@RequestBody
        //checkbox中如果钩中就是true,如果没有钩中就是null
        log.info("在执行用户认证时调用了数据库,原因不明");
        //获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                //设置remember属性
                //记住我,会生成一个cookie,如果没有rememberMe默认是一个临时的cookie浏览器关闭就死亡
                new UsernamePasswordToken(user.getName(), user.getPassword(),rememberMe);
        try {
            currentUser.login(token);
            //@ResponseBody返回的如果是一个字符串,直接写到客户端;如果是对象才会将对象转为json串
            //如果登入成功,返回0通过ajax判断重定向
            return "0";
            //为了安全不应该具体的显示错误
        } catch (UnknownAccountException e) {
            System.out.println("用户名错误");
            return "用户名错误或密码错误";
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
            return "用户名错误或密码错误";
        }
    }
}

