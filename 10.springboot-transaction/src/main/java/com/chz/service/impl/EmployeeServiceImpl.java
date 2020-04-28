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
 * @since 2020-04-27
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    /*
       使用年龄模拟转账
       @Transactional 注解可以被应用于接口定义和接口方法、类定义和类的 public 方法上。
       @Transactional如果方法执行成功,会隐式提交事务

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

    /**
     * spring的事务回滚机制
     * \-- 只针对unchecked exception回滚 RuntimeException如空指针,数组越界,etc ; 不会对checked exception回滚 如ioException,Exception也是
     * \-- 如果unchecked exception被捕捉的话, 事务不再生效作为普通方法运行
     * \-- 同一个类中的两个事务方法, method1调用method2还是在同一个事务中, 同时回滚
     *      如果向要method1 回滚的同时 method2 提交成功, 那么要另外写一个类将method2放在该类中
     */
//    @Transactional(propagation = Propagation.REQUIRED)
    public void method1() {
        Employee employee = new Employee();
        employee.setLastName("pink floyd").setAge(23).setEmail("123!").setGender("1");
        save(employee);
        method2();
        System.out.println("方法调用");
        if (true) {
            throw new RuntimeException("异常.........");
        }
        System.out.println("异常后");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void method2() {
        Employee employee = new Employee();
        employee.setLastName("oasis").setAge(12).setEmail("321!").setGender("1");
        save(employee);
    }
}
