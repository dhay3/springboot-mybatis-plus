package com.chz.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chz.entity.Employee;
import com.chz.service.IEmployeeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-04-28
 */
@Api("employee controller")
//@ApiModel
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @ApiIgnore//忽略该api
    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    /*
    value是对方法的描述, notes是注意点
     */
    @ApiOperation(value = "查询所有", notes = "注意实体类有乐观锁")
    @GetMapping("/list")
    public List<Employee> list() {
        return employeeService.list();
    }

    @ApiOperation(value = "查询用户", notes = "通过id查询")
    @GetMapping("/{id}")
    public Employee get(@PathVariable("id") Integer id) {
        return employeeService.getById(id);
    }

    @ApiOperation(value = "添加用户", notes = "传过来的参数可以不一样")
    @PostMapping("/add")
    public boolean add(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    /*
    name 参数名
    value 参数的简要描述
    required 参数是否必须
    dataTypeClass 参数的数据类型
    parameterType http参数类型
     */
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer", paramType = "path")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return employeeService.removeById(id);
    }

    @ApiOperation(value = "更新用户", notes = "根据用户id更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataTypeClass = Integer.class, paramType = "path"),
            @ApiImplicitParam(name = "employee", value = "更新的参数", dataTypeClass = Employee.class)

    })
    @PutMapping("/{id}")
    public boolean update(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        return employeeService.update(employee,
                new UpdateWrapper<Employee>().eq("id", id));
    }


}

