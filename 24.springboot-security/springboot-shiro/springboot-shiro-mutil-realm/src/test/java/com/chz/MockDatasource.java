package com.chz;

import com.chz.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MockDatasource {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test(){
        //toString()会触发一次懒加载
        System.out.println(userMapper.queryUser("zsf"));
    }
    @Test
    public void test1(){

    }
}
