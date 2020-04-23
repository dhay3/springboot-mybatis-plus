package com.chz.springbootmybatisplusdruid;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.springbootmybatisplusdruid.dao.UserMapper;
import com.chz.springbootmybatisplusdruid.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisPlusDruidApplicationTests {
    @Autowired
    UserMapper mapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testConnect() {
        System.out.println(mapper.queryUserRoles("chz"));
    }

    @Test
    public void testMp() {
        List<User> users = mapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testEq() {
        System.out.println(mapper.selectOne(new QueryWrapper<User>().eq("name", "chz")));
    }
}
