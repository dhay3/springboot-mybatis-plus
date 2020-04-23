package com.chz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 82341
 * 接口继承BaseMapper<>,泛型就是当前接口所操作的实体类
 */
@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
