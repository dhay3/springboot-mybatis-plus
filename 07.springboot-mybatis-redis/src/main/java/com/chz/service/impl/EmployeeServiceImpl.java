package com.chz.service.impl;

import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public List<Employee> list(String name, Integer id) {
        return employeeMapper.list(name, id);
    }

    @Override
    public boolean add(Employee employee) {
        return employeeMapper.add(employee);
    }

    @Override
    public Employee update(Employee employee) {
        employeeMapper.update(employee);
        return employeeMapper.get(employee.getId());
    }

    @Override
    public Employee get(Integer id) {
        return employeeMapper.get(id);
    }

    @Override
    public boolean delete(Integer id) {
        return employeeMapper.delete(id);
    }
}
