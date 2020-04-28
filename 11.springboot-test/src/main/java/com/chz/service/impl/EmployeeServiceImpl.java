package com.chz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-04-26
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    /*
    使用年龄模拟转账
    @Transactional 注解可以被应用于接口定义和接口方法、类定义和类的 public 方法上。
    @Transactional如果方法执行成功,会隐式提交事务
    @Transactional中的异常被捕获不会造成事务回滚, 如果想要让事务回滚抛出一个异常即可
    且@Transactional的方法不能在本类中被调用, 如果在本类中被调用
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean transaction(Integer form, Integer to, Integer age) {
        //转账
        Employee formE = getById(form);
        System.out.println(formE);
        formE.setAge(formE.getAge() - age).setVersion(1);
        updateById(formE);

        //收账
        Employee toE = getById(to);
        System.out.println(toE);
        toE.setAge(toE.getAge() + age).setVersion(1);
        updateById(toE);
        return true;
    }

}
