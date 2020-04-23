package com.chz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.entity.Employee;
import com.chz.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService;

    @RequestMapping("/get/{id}")
    public Employee get(@PathVariable("id") Integer id) {
        return employeeService.getById(id);
    }

    @RequestMapping("/add")
    public boolean add() {
        //为了方便直接自定参数
        Employee employee = new Employee();
        employee.setLastName("王广发").setAge(12).setEmail("12345@gf");
        return employeeService.save(employee);
    }

    /*
    分页查询
     */
    @RequestMapping("/page")
    public List<Employee> page() {
        Page<Employee> employeePage = new Page<>(1, 3);
        Page<Employee> page = employeeService.page(employeePage,
                //可以输入字段名,也可以通过表映射类的getAge来设置,注入此类
                new QueryWrapper<Employee>().orderByAsc("age"));
        return page.getRecords();
    }

    /*
    逻辑删除
     */
    @RequestMapping("/delete")
    public boolean logicDelete(Integer id) {
        return employeeService.removeById(id);
    }

    /*
    乐观锁
     */
    @RequestMapping("/lock")
    public boolean lock() {
        Employee employee = new Employee();
        employee.setLastName("成龙").setAge(111).setEmail("cl@cl").setId(33);
        //乐观锁必须设置version,且version必须相同才能设置
        employee.setVersion(2);
        return employeeService.updateById(employee);
    }
}

