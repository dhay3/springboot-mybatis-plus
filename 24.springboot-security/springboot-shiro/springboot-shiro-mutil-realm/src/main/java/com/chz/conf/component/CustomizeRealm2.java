package com.chz.conf.component;

import com.chz.entity.Perm;
import com.chz.entity.Role;
import com.chz.entity.User;
import com.chz.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

@Slf4j
public class CustomizeRealm2 extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    /**
     * 获取用户权限和角色
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (ObjectUtils.isEmpty(principals)){
            throw new UnknownAccountException();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //从pricipals中获取登入的用户信息
        String username = principals.getPrimaryPrincipal().toString();
        User user = userService.queryUser(username);
        for (Role role : user.getRoles()) {
            //获取角色,并给用户添加角色
            info.addRole(role.getRole());
            for (Perm perm : role.getPerms()) {
                //获取角色对应的权限,并给用户添加权限
                info.addStringPermission(perm.getPerm());
            }
        }
        return info;
    }

    /**
     * 登入认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取用户名
        String username = usernamePasswordToken.getUsername();
        User user = userService.queryUser(username);
        //判断数据库中是否有该用户
        if (ObjectUtils.isEmpty(user)) {
            //如果不存在抛出用户不存在异常
            throw new UnknownAccountException();
        }
        //获取加密后的密码
        String password = user.getPassword();
        //盐值
        ByteSource salt = ByteSource.Util.bytes(username);
        //密码校验由shiro完成
        //principal,hashedcredentials,salt,realName
        SimpleAuthenticationInfo info =
                //校验加密后的密码盐值是否匹配
                new SimpleAuthenticationInfo(username,
                        password,
                        salt,
                        getName());
        return info;
    }
}
