package com.chz.mapper;

import com.chz.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-04-28
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
