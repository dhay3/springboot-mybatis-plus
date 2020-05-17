package com.chz.athentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SecurityAthenticationApplicationTests {

    @Test
    void contextLoads() {

    }


    @Test
    public void test1() {
        @Deprecated
        //spring-security内置对象
                UserDetails user = User
                //使用的是PasswordEncoderFactories.createDelegatingPasswordEncoder();编码
                .withDefaultPasswordEncoder()
                .username("zs")
                .password("123")
                .roles("user")
                .build();
        System.out.println(user);
        String password = user.getPassword();
        //这种方法不安全还是会显示明文的密码, 所以被抛弃了
        //{编码的形式}加密密码
        System.out.println(password);

        //使用该编码器能解决加密方式不统一的问题,按照编码后的密码检索
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //检查密码是要加密
        boolean b = encoder.upgradeEncoding(password);
        //检查密码是否匹配
        boolean matches = encoder.matches("123", password);
        System.out.println(password);
    }

    /**
     * 推荐形式加密
     */
    @Test
    public void test2() {
        //		SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
//		Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
//		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        //通常使用BCryPt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String psw = encoder.encode("12345");
        System.out.println(psw);
        assertTrue(encoder.matches("12345", psw));
        System.out.println(psw);
    }

    @Test
    public void test3() {
        //检查给出的对象是否有文本
        System.out.println(StringUtils.hasText("hello"));
    }
}
