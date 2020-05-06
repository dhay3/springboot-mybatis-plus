package com.chz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.entity.Employee;

import java.io.FileNotFoundException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chz
 * @since 2020-04-27
 */
public interface IEmployeeService extends IService<Employee> {
    boolean transaction(Integer form, Integer to, Integer age) throws FileNotFoundException;
}
