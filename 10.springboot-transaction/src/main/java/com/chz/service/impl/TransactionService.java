package com.chz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionService extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Autowired
    @Qualifier("transactionOtherService")
    TransactionOtherService otherService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void method1() {
        Employee employee = new Employee();
        employee.setLastName("pink floyd").setAge(23).setEmail("123!").setGender("1");
        save(employee);
        //通过这种方式调用的事务method2()会生效
        otherService.method2();
        System.out.println("方法调用");
        if (true) {
            throw new RuntimeException("异常.........");
        }
        System.out.println("异常后");
    }

    @Override
    public boolean transaction(Integer form, Integer to, Integer age) {
        return false;
    }
}
