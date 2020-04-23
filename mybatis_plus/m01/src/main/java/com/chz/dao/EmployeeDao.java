package com.chz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.entity.Employee;

/**
 * @author 82341
 * 接口继承BaseMapper<>,泛型就是当前接口所操作的实体类
 */
//@Mapper //并不需要@mapper,与springboot不同,加了@Mapper同样要配置MapperScaner
public interface EmployeeDao extends BaseMapper<Employee> {
}
