package com.chz.springsecuritysession.controller;

import com.chz.springsecuritysession.entity.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.SingleByte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private SessionRegistry sessionRegistry;
    //如果没有指定的mapping就会跳转导该mapping
    @RequestMapping("/index")
    public String showHome() {
        //获取当前登录用户的用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户：" + name);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("pringcipal===>",principal);
        return "home";
    }

    @GetMapping("/login")
    public String toLogin() {
        //返回静态页面使用redirect
        return "login";
    }

    //这里对应数据库中的权限是spring规定的格式ROLE_xxx
    @ResponseBody
    //需要添加@EnableGlobalMethodSecurity(prePostEnabled = true)
    //@PreAuthorize用于判断用户是否有指定的权限, 没有就不能访问
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/user")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }

    @ResponseBody
    //提交的信息为当前用户,这里的domain object指的是DO
    @PreAuthorize("hasPermission(#userDO,'r')")
    @GetMapping("/checkR")
    public String hasPermC(UserDO userDO) {
        System.out.println(userDO);
        return "权限认证通过";
    }

    @ResponseBody
    @GetMapping("/fail1")
    public Object fail1(HttpServletRequest req, HttpServletResponse resp) {
        log.info("failure into this ");
        //设置编码防止乱码
        resp.setContentType("application/json;charset=utf-8");
        AuthenticationException exception = (AuthenticationException)
                req.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        return exception;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @PostMapping("/fail2")
    public String fail2() {
        log.info("into fail2");
        return "账号或密码错误";
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @GetMapping("/session/invalid")
    public String sessionInvalid(){
        log.info("session===>失效");
        return "session 已失效,重新登入";
    }

    /**
     * 根据用户名踢出用户
     */
    @ResponseBody
    @GetMapping("/kick")
    public String kick(@RequestParam String username){
        int count =0;
        //获取所有的内置User对象
        List<Object> users = sessionRegistry.getAllPrincipals();
        log.info("users======>",users.toString());
        for (Object principal : users) {
            if (principal instanceof User){
                String principalName = ((User) principal).getUsername();
                //将session中同名的移除
                if (StringUtils.equalsIgnoreCase(principalName,username)){
                    //获取所有的session, 不包括失效的session, 这里的参数principal不是principalName
                    List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principal, false);
                   if (!ObjectUtils.isEmpty(allSessions)&&allSessions.size()>0){
                       for (SessionInformation sessionInfo : allSessions) {
                           //session立马失效, 会调用sessionExpiredStrategy
                           sessionInfo.expireNow();
                           count++;
                       }
                   }
                }
            }
        }
        return "清除session共"+count+"个";
    }
}
