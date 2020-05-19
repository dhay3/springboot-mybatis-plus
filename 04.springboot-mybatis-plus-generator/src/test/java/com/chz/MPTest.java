package com.chz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MPTest {
    @Autowired
    EmployeeMapper employeeDao;
    @Autowired
    IEmployeeService employeeService;

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

    /*
    dao能封装localDateTime,但是service不能
     */
    @Test
    public void testService() {
//        System.out.println(employeeService.getOne(new QueryWrapper<Employee>().eq("id", 13)));
//        System.out.println(employeeService.getById(13));
        System.out.println(employeeDao.selectById(13));
//        employeeDao.selectById()
    }

    /**
     * 分页查询必须配置分页插件才会生效
     */
    @Test
    public void testService2() {
        Page<Employee> employeePage = new Page<>(0, 3);
        Page<Employee> page = employeeService.page(employeePage, null);
        List<Employee> employees = page.getRecords();
        employees.forEach(System.out::println);
    }

    /**
     * 测试乐观锁, 丢失更新
     */
    @Test
    public void  testLock(){
        Employee employee = new Employee();
        employee.setLastName("wanglaowu").setVersion(1);
        employeeService.update(employee,new UpdateWrapper<Employee>().eq("id",13));
    }

    /**
     * 测试逻辑删除
     */
    @Test
    public void testLogicalDelete(){
        employeeService.removeById(13);
    }

    /**
     * 测试自动填充
     */
    @Test
    public void insert(){
        Employee employee = new Employee();
        employee.setLastName("中国").setEmail("cn@123").setGender("2");
        employeeService.save(employee);
        System.out.println(employee.getId());
        System.out.println(employee);
    }

    /**
     * 批量查询
     */
    @Test
    public void bathQuery(){
        List<Employee> employees = employeeService.listByIds(Arrays.asList(1, 2, 34, 5));
        employees.stream().forEach(System.out::println);
    }
}
