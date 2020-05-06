package com.chz.controller;


import com.chz.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    @Qualifier("employeeServiceImpl")
    private IEmployeeService employeeService;

    @GetMapping("/tran")
    public String transaction(Integer fromId, Integer toId  , Integer age) throws FileNotFoundException {
        if (!employeeService.transaction(fromId, toId, age)) {
            return "失败";
        }
        return "成功";
    }
}

