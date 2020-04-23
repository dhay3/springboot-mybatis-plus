package com.chz.springbootmybatisdruid;

import com.chz.springbootmybatisdruid.entity.Teacher;
import com.chz.springbootmybatisdruid.mapper.TeacherMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootMybatisDruidApplicationTests {
    @Autowired
    private TeacherMapper teacherMapper;
    @Test
    void contextLoads() {
    }
    @Test
    public void testConnect(){
//        Insert
//        Teacher teacher = new Teacher("大蛇",20,1);
//        System.out.println(teacherMapper.add(teacher));
//        select
//        teacherMapper.list().forEach(System.out::println);
//        update
//        Teacher teacher = new Teacher(8, "天天", 11, 2);
//        int update = teacherMapper.update(teacher);
//        get
//        System.out.println(teacherMapper.get(7));

//        System.out.println(teacherMapper.dynGet("天天", null));

//        System.out.println(teacherMapper.dynUpdate("王老五", null,8));

        System.out.println(teacherMapper.dynInsert(new Teacher("丁一", 12, 1)));
    }

}
