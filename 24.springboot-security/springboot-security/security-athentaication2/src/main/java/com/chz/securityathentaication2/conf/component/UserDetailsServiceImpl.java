package com.chz.securityathentaication2.conf.component;


import com.chz.securityathentaication2.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现用户认证需要实现UserDetailsService接口
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 首先经过该方法
     * 根据用户名获取信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名未找到");
        }
        //模拟数据库根据用户名查询信息
        UserDO userDO = new UserDO();
        userDO.setUserName("zs");
        //实际密码是从数据库中获取的加密密码
        userDO.setPassword(passwordEncoder.encode("123"));
//        System.out.println("password :=" + userDO.getPassword());
        if (ObjectUtils.isEmpty(userDO)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //SimpleGrantedAuthority用于存储用户(Authentication)的权限
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : userDO.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        //创建一个用于认证的内置User,封装成一个Authentication,交给DaoAuthenticationProvider验证
        return new User(username,
                userDO.getPassword(),
                userDO.getEnable(),
                userDO.getAccountNonExpired(),
                userDO.getCredentialNonExpired(),
                userDO.getAccountNonLocked(),
                //AuthorityUtils.commaSeparatedStringToAuthorityList模拟数据
                grantedAuthorities);
    }
}
