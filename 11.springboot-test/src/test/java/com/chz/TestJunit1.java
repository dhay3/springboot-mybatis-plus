package com.chz;

import com.chz.entity.Employee;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
使用静态导包可以不用每次都写出工具类
 */
@SpringBootTest
class TestJunit1 {
    @Test
    void contextLoads() {
    }

    /*
    同junit 4 @Before
    @BeforeTransaction 在事务之前运行
    注意在测试模块中的@Transactional不会正真的运行, 就会不会对数据库中的数据造成影响
     */
    @BeforeEach
    public void testBefore() {
        System.out.println("before...");
    }

    /*
    同junit 4 @After
    @AfterTransaction 在事务之后运行
    */
    @AfterEach
    public void testAfter() {
        System.out.println("after...");
    }


    /*
    Assert和Assume效果差不多
    声明
     */
    @Test
    @DisplayName("test....")//指定展示的方法名
    public void testAssert() {
        Employee person = new Employee();
        person.setAge(23);
        //只有expected和actual的值相同才会继续运行, 否则抛出异常
        assertEquals(23, person.getAge());
        //只有condition是true时才会继续运行, 如果出错则抛出自定义message,可以是一个supplier接口也
        assertTrue(2 > 1, () -> "2比1大");
//        assertFalse();
        //没有参数没有返回值的函数式接口
        assertAll("hello", () -> {
            int age = person.getAge();
            //声明name不能为null
            assertNotNull(age);
//            assertNull();
//            assertAll();
        });
    }

    @Test
    @EnabledOnOs({OS.MAC})//没有任何实际功能指定操作系统欧冠
    @EnabledOnJre(JRE.JAVA_8)//没有任何实际功能指定jre环境
    @Disabled("只有bug被修复了才能使用")//没有实际功能,只是作为警示
    public void testAssume() {
        System.out.println("这是一个测试");
    }
}
