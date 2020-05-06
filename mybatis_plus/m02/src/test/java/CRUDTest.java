import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/spring-config.xml"})
public class CRUDTest {
    /*
    ARInsert
     */
    @Test
    public void testARInsert() {
        Employee employee = new Employee();
        employee.setLastName("大张伟").setGender(1).setAge(30).setEmail("qq@");
        boolean result = employee.insert();
        System.out.println(result);
    }

    /*
    ARUpdate
     */
    @Test
    public void testUpdate() {
        Employee employee = new Employee();
        employee.setId(16).setLastName("张三");
        UpdateWrapper<Employee> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("last_name", "zs");
//        employee.update(updateWrapper);
        employee.updateById();
    }

    /*
    ARQuery
     */
    @Test
    public void testARQuery() {
        Employee employee = new Employee();
        List<Employee> employees = employee.selectList(null);
        List<Employee> employees1 = employee.selectAll();
        Integer integer = employee.selectCount(new QueryWrapper<Employee>().eq("gender", 1));
//        employees.forEach(System.out::println);
        employees1.forEach(System.out::println);
        System.out.println("性别为1:" + integer);
    }

    /*
    ARDelete
     */
    @Test
    public void testARDelete() {
        Employee employee = new Employee();
        employee.deleteById(2);
    }

    /*
    AR 分页
    SELECT id,last_name,email,gender,age FROM tbl_employee
     */
    @Test
    public void testARPage() {
        Employee employee = new Employee();
        Page<Employee> page = new Page<>(1, 3);
        Page<Employee> employeePage = employee.selectPage(page, null);
        System.out.println(employeePage.getRecords());
    }
}
