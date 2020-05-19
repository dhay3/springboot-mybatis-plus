import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.dao.EmployeeDao;
import com.chz.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//想要以spring环境运行必须要加该注解
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"conf/spring-config.xml"}) 错误
// 自动从output目录找
@ContextConfiguration(locations = {"/conf/spring-config.xml"})
//@ContextConfiguration(locations = {"classpath:/conf/spring-config.xml"})
public class CRUDTest {
    @Autowired
    EmployeeDao employeeDao;

    /**
     * 通用查询
     **/
    @Test //查询所有
    public void tests1() {
        List<Employee> employees = employeeDao.selectList(null);
        employees.forEach(System.out::println);

    }

    @Test //按id查询
    public void tests2() {
        Employee employee = employeeDao.selectById(13);
        System.out.println(employee);
    }

    @Test //allEq查询单个
    //select * from table where last_name = z3f and email is null
    public void tests3() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        //数据库字段名必须用String,不能用Object
        HashMap<String, Object> map = new HashMap<>();
        //map的key对应字段名
        map.put("last_name", "z3f");
        map.put("email", null);
//        为true则在map的value为null时调用 isNull 方法,为false时则忽略value为null的
        //条件可以不用返回,源码中返回是为链式编程
        queryWrapper.allEq(map);
        Employee employee = employeeDao.selectOne(queryWrapper);
        System.out.println(employee);
    }

    @Test //selectByMap 按照map条件查询
//    SELECT id,last_name,email,gender,age FROM tbl_employee WHERE last_name = ?
    public void testM1() {
        HashMap<String, Object> map = new HashMap<>();
        //key对应字段名
        map.put("last_name", "z3f");
        List<Employee> employees = employeeDao.selectByMap(map);
        System.out.println(employees);
    }

    @Test //selectMaps 需要wrapper 返回map形式
    public void testM2() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender", 1);
        //结果转成map放入list中
        List<Map<String, Object>> maps = employeeDao.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    @Test// selectBatchIds
    //SELECT id,last_name,email,gender,age FROM tbl_employee WHERE id IN ( ? , ? , ? )
    public void test5() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Employee> employees = employeeDao.selectBatchIds(list);
        employees.forEach(System.out::println);
    }

    @Test //selectPage
    public void test6() {
        Page<Employee> page = new Page<>(0, 2);
        //queryWrapper可以是null
        IPage<Employee> employeeIPage = employeeDao.selectPage(page, null);
        //要通过getRecords()拿到结果
//        System.out.println(employeeIPage.getRecords());
        List<Employee> records = employeeIPage.getRecords();
        records.forEach(System.out::println);
    }

    /*
    查询性别为1,并按年龄升序
     */
    @Test
    public void test9() {
        List<Employee> employees = employeeDao.selectList(new QueryWrapper<Employee>().lambda()
                .eq(Employee::getGender, 1)
                .orderByAsc(Employee::getAge));
        employees.forEach(System.out::println);
    }

    /*
    伪分页查询,如果要真的分页查询请配置paginationInterceptor
    SELECT id,last_name,email,gender,age FROM tbl_employee WHERE (age > ?)
     */
    @Test //page wrapper
    public void test10() {
        Page<Employee> page = new Page<>(1, 2);
        Page<Employee> employeePage = employeeDao.selectPage(page, new QueryWrapper<Employee>().lambda()
                .gt(Employee::getAge, 30));
        employeePage.getRecords().forEach(System.out::println);
    }

    /*
    orderBy
     */
    @Test
    public void test11() {
        List<Employee> age = employeeDao.selectList(new QueryWrapper<Employee>().
                orderBy(true, true, "age"));
        System.out.println(age);
    }

    /*
    wrapper select值提取指定字段封装到javabean
     */
    @Test
    public void test12() {
        QueryWrapper<Employee> wrapper = new QueryWrapper<Employee>().select("age", "gender").eq("last_name", "z3f");
        System.out.println(employeeDao.selectOne(wrapper));
    }

    /*
    count
     */
    @Test
    public void test13() {
        //查询带条件的count
        Integer integer = employeeDao.selectCount(new QueryWrapper<Employee>().eq("gender", 1));
        //查询所有的count
        Integer integer1 = employeeDao.selectCount(null);
    }

    /**
     * 通用插入
     **/
    @Test
    public void test2() {
        Employee employee = new Employee();
        employee.setLastName("Oka").setEmail("oka@").setGender(2).setAge(3).setSalary(1000D);
        employee.setLastName("z3").setGender(1);
        //insert显示sql当前赋过值的字段
        int num = employeeDao.insert(employee);
        //mp直接会回显主键
        //mybatis需要配置useGeneratedKeys,keyProperty才能回显主键
        Integer id = employee.getId();
        System.out.println("生效行数:" + num + "\t主键值:" + id);
    }

    /**
     * 通用更新
     **/
    @Test
    public void test3() {
        Employee employee = new Employee();
        //如果不想修改某个字段,不赋值即可
        employee.setId(13).setLastName("z3f").setAge(33);
        //显示sql当前赋过值的字段
        int i = employeeDao.updateById(employee);
        System.out.println("生效行数:" + i);
    }

    /**
     * 通用删除
     */
    @Test //deleteById
    public void test4() {
        int i = employeeDao.deleteById(6);
        System.out.println("生效行数:" + i);
    }

    @Test //deleteByMap
    public void test7() {
        HashMap<String, Object> map = new HashMap<>();
        //封装条件
        map.put("age", 22);
        employeeDao.deleteByMap(map);
    }

}
