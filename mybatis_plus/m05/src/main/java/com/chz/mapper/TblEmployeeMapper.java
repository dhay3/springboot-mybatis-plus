package com.chz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.entity.TblEmployee;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-04-11
 */

public interface TblEmployeeMapper extends BaseMapper<TblEmployee>  {
    //如果不使用自定义全局配置,就必须在mapper.xml中写出
    //使用ISqlInject
//    @Select("select * from tbl_employee")
    void deleteEmployee(String name);
}
