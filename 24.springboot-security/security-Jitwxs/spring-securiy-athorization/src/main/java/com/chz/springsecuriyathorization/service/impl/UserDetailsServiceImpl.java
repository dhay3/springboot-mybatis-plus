package com.chz.springsecuriyathorization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.springsecuriyathorization.entity.Role;
import com.chz.springsecuriyathorization.entity.UserDO;
import com.chz.springsecuriyathorization.entity.UserRole;
import com.chz.springsecuriyathorization.mapper.RoleMapper;
import com.chz.springsecuriyathorization.mapper.UserMapper;
import com.chz.springsecuriyathorization.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service("cusUserDetails")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        UserDO user = userMapper
                .selectOne(new QueryWrapper<UserDO>().eq("name", username));
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //根据用户id查询用户对应的角色id
        List<UserRole> userRoles = userRoleMapper
                .selectList(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            //根据用户角色id查询角色
            Integer roleId = userRole.getRoleId();
            Role role = roleMapper.selectById(roleId);
            //添加用户角色
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(user.getName(), user.getPassword(), authorities);
    }
}
