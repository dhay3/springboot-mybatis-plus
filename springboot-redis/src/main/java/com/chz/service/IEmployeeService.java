package com.chz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.entity.Employee;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chz
 * @since 2020-04-24
 * 如果使用mybatis-plus配置缓存自能对自定义的使用
 */
@Service
public interface IEmployeeService extends IService<Employee> {

}
