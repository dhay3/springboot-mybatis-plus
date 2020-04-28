package com.chz;

import com.chz.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

/*
AssertJ的api 支持流式编程 同时还支持数据库校验需要导入依赖
 */
@SpringBootTest
public class TestAssertJ {
    @Autowired
    DataSource dataSource;



    @Test
    public void test() {
        Employee employee = new Employee();
        employee.setLastName("zs").setAge(12).setGender("男");
        //这里对应的是泛型的参数 isNotEqualTo
        assertThat(employee.getLastName()).isEqualTo("zs").startsWith("z").endsWith("s");
        List<Employee> list = Arrays.asList(employee, new Employee().setLastName("ls").setAge(30));

        assertThat(list).hasSize(2).contains(employee);
        //链式过滤
//        assertThat(list).
//                filteredOn((e)->e.getLastName().contains("s")).
//                containsOnly(employee);
        //支持流式操作
        assertThat(list.stream().filter(e -> e.getAge() == 12).
                collect(Collectors.toList()).get(0)).
                isEqualTo(employee);
        System.out.println(employee);
    }

}
