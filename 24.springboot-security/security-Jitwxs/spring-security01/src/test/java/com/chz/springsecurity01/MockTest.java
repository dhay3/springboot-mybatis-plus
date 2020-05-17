package com.chz.springsecurity01;

import com.chz.springsecurity01.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MockTest {
    @Autowired
    UserMapper userMapper;
    @Test
    public void testDB(){
        System.out.println(userMapper.selectById(1));
    }
}
