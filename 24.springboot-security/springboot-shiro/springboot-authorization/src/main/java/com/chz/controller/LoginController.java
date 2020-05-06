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
public class LoginController {
    @Autowired
    IUserService userService;

    /**
     * 无权限跳转, 以及注销跳转页
     */
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 注册
     */
    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        String password = MD5Utils.encrypt(user.getPassword(), user.getName());
        user.setPassword(password);
        userService.save(user);
        return "注册成功";
    }

    /**
     * 登入校验
     * @param user 可以将多个参数绑定到一个对象,将另外一个参数绑定到一个简单参数
     * @param rememberMe checkbox中如果钩中就是true,如果没有钩中就是false
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public String login(User user,
                        //这里不能用@RequetBody来接收,因为发送的是Json串
                        @RequestParam(value = "rememberMe", required = false) Boolean rememberMe) {
        System.out.println(rememberMe);
        //调用两次数据库貌似错在@RequestBody
        log.info("在执行用户认证时调用了数据库,原因不明");
        //获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                //设置remember属性
                //如果是重要页面不应该设置rememberMe
                //记住我,会生成一个cookie,如果没有rememberMe默认是一个临时的cookie浏览器关闭就死亡
                new UsernamePasswordToken(user.getName(), user.getPassword(), rememberMe);
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

    /**
     * 注销
     */
    @GetMapping("/logout")
    public String logOut(){
        SecurityUtils.getSubject().logout();
        //这里用重定向,注意地址栏url的显示
        return "redirect:/login";
    }
}

