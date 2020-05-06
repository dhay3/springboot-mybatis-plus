package com.chz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    public boolean transaction(Integer form, Integer to, Integer age) throws FileNotFoundException {

        //转账
        Employee formE = getById(form);
        System.out.println(formE);
        formE.setAge(formE.getAge() - age).setVersion(1);
        updateById(formE);
        toTransaction(to, age);
        return true;
    }

    public void toTransaction(Integer to, Integer age) throws FileNotFoundException {
        //收账
        Employee toE = getById(to);
        System.out.println(toE);
        toE.setAge(toE.getAge() + age).setVersion(1);
        updateById(toE);
        //checked exception 被throws后不会对事务产生影响,事务正常运行
        FileInputStream fileInputStream = new FileInputStream("/");
//        throw new RuntimeException();
    }

    /**
     * spring的事务回滚机制
     * \-- 不管是cheked exception或是unchecked exception只要是异常try-catch后,方法正常执行,事务不会生效,
     *    但是如果checked exception抛出异常不会对事务有任何影响,事务正常运行,不同于@ExceptionHandler和aop中的
     * \-- 同一个类中的两个事务方法, method1调用method2还是在同一个事务中, 同时回滚
     * 如果向要method1 回滚的同时 method2 提交成功, 那么要另外写一个类将method2放在该类中
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
