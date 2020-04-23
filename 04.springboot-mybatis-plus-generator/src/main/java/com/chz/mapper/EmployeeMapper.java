package com.chz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *  mybatis-plus不会自动生成@Mapper, 需要手动添加
 * @author chz
 * @since 2020-04-23
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
