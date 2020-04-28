package com.chz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TransactionOtherService extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void method2() {
        Employee employee = new Employee();
        employee.setLastName("oasis").setAge(12).setEmail("321!").setGender("1");
        save(employee);
    }

    @Override
    public boolean transaction(Integer form, Integer to, Integer age) {
        return false;
    }
}
