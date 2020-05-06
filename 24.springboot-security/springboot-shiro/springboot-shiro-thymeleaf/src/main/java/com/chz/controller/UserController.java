package com.chz.controller;

import com.chz.entity.User;
import com.chz.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 在访问api时 shiro会根据@RequiresRoles和@RequiresPermission调用doGetAuthorizationInfo()
 */
@Slf4j
@RestController
//如果没有指定method,@RequestMapping会接收所有类型的请求
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @RequiresRoles("admin")
    @RequiresPermissions("user:add")
    @PostMapping
    public String save(User user) {
        userService.save(user);
        return "添加成功";
    }
    @RequiresPermissions("user:delete")
    @RequiresRoles("admin")
    @ResponseBody
    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") Integer id) {
        userService.removeById(id);
        return "删除成功";
    }
    @RequiresPermissions("user:update")
    //logical.OR表示二者中的一个即可
    @RequiresRoles(value = {"teacher","admin"},logical = Logical.OR)
    @ResponseBody
    @PutMapping
    public String update(User user) {
        userService.updateById(user);
        return "更新成功";
    }
    @RequiresPermissions("user:select")
    @ResponseBody
    @GetMapping
    public List<User> list() {
        List<User> list = userService.list();
        System.out.println(list);
        return list;
    }
    @RequiresPermissions("user:select")
    @RequiresRoles(value = {"teacher","student","admin"},logical = Logical.OR)
    @GetMapping("/{id}")
    public User get(@PathVariable("id") Integer id) {
        //这里调用的只是mybatis-plus封装的方法所以不会打印出集合的信息
        User user = userService.getById(id);
        System.out.println(user);
        return user;
    }
}
