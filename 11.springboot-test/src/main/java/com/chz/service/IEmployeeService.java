package com.chz.service;

import com.chz.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chz
 * @since 2020-04-26
 */
public interface IEmployeeService extends IService<Employee> {
    boolean transaction(Integer form, Integer to, Integer age);
}
