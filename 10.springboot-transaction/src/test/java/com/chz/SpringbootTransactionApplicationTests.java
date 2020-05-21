package com.chz;

import com.chz.entity.Employee;
import com.chz.service.IEmployeeService;
import com.chz.service.impl.EmployeeServiceImpl;
import com.chz.service.impl.TransactionService;
import org.apache.ibatis.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@SpringBootTest
class SpringbootTransactionApplicationTests {
    @Autowired
    @Qualifier("employeeServiceImpl")
    IEmployeeService employeeService;
    @Autowired
    EmployeeServiceImpl employeeServiceImpl;
    @Autowired
    @Qualifier("transactionService")
    TransactionService transactionService;

    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        Employee employee = new Employee();
        employee.setId(2).setVersion(1).setLastName("娃娃");
        employeeService.updateById(employee);
    }

    //在测试单元中对数据库的操作应该加上@Transactional
    @Test//junit也对事务生效
//    @Commit//效果与@Rollbakc(value=true)效果一样
//    @Rollback(value = false)//提交事务
    @Transactional//如果想要测试单元中的数据不会注入到数据库中开启@Transactional即可,会即使方法调用成功也会自动rollback
    public void test1() throws FileNotFoundException {
//        transactionService.method1();
//        employeeServiceImpl.method1();
//        try {
        employeeService.transaction(2, 3, 5);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }




}
