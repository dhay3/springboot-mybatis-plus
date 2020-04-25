package com.chz.controller;

import com.chz.entity.Employee;
import com.chz.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CacheController {
    @Autowired
    IEmployeeService employeeService;

    @RequestMapping("/list")
    public List<Employee> list() {
        return employeeService.list(null, null);
    }

    @GetMapping("/add")
    public Employee add() {
        Employee employee = new Employee();
        employee.setLastName("埃里克").setEmail("eric@").setAge(12).setGender("1");
        employeeService.add(employee);
        return employeeService.get(employee.getId());
    }

    @GetMapping("/get/{id}")
    public Employee get(@PathVariable("id") Integer id) {
        return employeeService.get(id);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return employeeService.delete(id);
    }
}
