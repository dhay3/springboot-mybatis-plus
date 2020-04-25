package com.chz;

import com.chz.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheTest {
    @Autowired
    IEmployeeService employeeService;
    @Test
    public void testConnect(){
        employeeService.list().forEach(System.out::println);
    }
}
