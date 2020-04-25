package com.chz.service.impl;

import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-04-24
 *
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
