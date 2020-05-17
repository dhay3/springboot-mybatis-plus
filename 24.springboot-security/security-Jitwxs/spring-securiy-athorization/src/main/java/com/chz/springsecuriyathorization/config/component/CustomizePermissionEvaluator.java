package com.chz.springsecuriyathorization.config.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.springsecuriyathorization.entity.Role;
import com.chz.springsecuriyathorization.entity.UserDO;
import com.chz.springsecuriyathorization.mapper.RoleMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import sun.nio.cs.SingleByte;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 自定义角色权限添加与删除处理类
 */
public class CustomizePermissionEvaluator implements PermissionEvaluator {
    @Autowired
    RoleMapper roleMapper;

    /**
     * @param authentication
     * @param targetDomainObject DO, 即userDO
     * @param permission         权限
     * @return 返回true有资格访问, 返回false没有资格访问
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        //获取spring内置对象User
//        String username = authentication.getPrincipal().toString();
        //获取用户名
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            //模拟从数据库中难道对应角色的权限
            ArrayList<Object> perms = new ArrayList<>();
            if (StringUtils.equalsIgnoreCase(roleName, "ROLE_ADMIN")) {
                Collections.addAll(perms, "r", "w", "x");
            }
            if (StringUtils.equalsIgnoreCase(roleName, "ROLE_USER")) {
                Collections.addAll(perms, "r");
            }
            if (perms.contains(permission)) {
                //返回true放行
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
