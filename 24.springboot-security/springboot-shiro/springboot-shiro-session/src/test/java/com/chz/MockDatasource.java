package com.chz;

import com.chz.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MockDatasource {

    private final  int i;
    public MockDatasource(){
        this.i=10;
    }
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test(){
        //toString()会触发一次懒加载
        System.out.println(userMapper.queryUser("zsf"));
    }
    @Test
    public void test1(){
        final int i =10;
        class User{
           public void f(){
               System.out.println(i);
           }
        }
    }
}
