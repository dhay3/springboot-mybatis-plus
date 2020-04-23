package com.chz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
@SpringBootTest
public class MPTest {
    @Autowired
    EmployeeMapper employeeDao;

    /*
    查询年龄18-40间的男性
    SELECT id,last_name,email,gender,age FROM tbl_employee WHERE (age BETWEEN ? AND ? AND gender = ?)
     */
    @Test
    public void test1() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age", 18, 40).eq("gender", 1);
        List<Map<String, Object>> maps = employeeDao.selectMaps(queryWrapper);
        System.out.println(maps);
    }

    /*
    查询邮箱中带有'qq'并且性别为1 或者 年龄大于30
    SELECT id,last_name,email,gender,age FROM tbl_employee WHERE (email LIKE ? AND gender = ? OR age >= ?)
     */
    @Test
    public void test2() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("email", "qq").eq("gender", 1).or()
                .ge("age", 30);
        List<Employee> employees = employeeDao.selectList(queryWrapper);
        employees.forEach(System.out::println);
    }

    /*
    修改名字叫做Black的邮箱为black123@qq
     UPDATE tbl_employee SET email=? WHERE (last_name = ?)
     */
    @Test
    public void test3() {
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        //where条件
        updateWrapper.lambda().eq(Employee::getLastName, "Black");
        Employee employee = new Employee();
        //想要修改的值
        employee.setEmail("black123@qq");
        employeeDao.update(employee, updateWrapper);
    }

    /*
        删除email为henry@163
        DELETE FROM tbl_employee WHERE (email = ?)
     */
    @Test
    public void test() {
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Employee::getEmail, "henry@163");
        employeeDao.delete(updateWrapper);
    }
}
