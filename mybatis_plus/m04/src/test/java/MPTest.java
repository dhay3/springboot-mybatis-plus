import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.entity.TblEmployee;
import com.chz.mapper.TblEmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring-config.xml"})
public class MPTest {
    @Autowired
    TblEmployeeMapper employeeDao;

    /*
    测试分页插件PaginationInterceptor
    SELECT id,last_name,email,gender,age FROM tbl_employee LIMIT ?,?
     */
    @Test
    public void test() {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/conf/spring-config.xml");
//        TblEmployeeMapper employeeDao = context.getBean(TblEmployeeMapper.class);
        Page<TblEmployee> page = employeeDao.selectPage(new Page<TblEmployee>(3, 1), null);
        System.out.println("总条数" + page.getTotal());
        System.out.println("当前页数" + page.getCurrent());
        System.out.println("每页显示的条数" + page.getSize());
        System.out.println("是否有前一页" + page.hasPrevious());
        System.out.println("是否有后一页" + page.hasNext());
        page.getRecords().forEach(System.out::println);
    }

    /*
    测试恶意sql插件SqlExplainInterceptor
     */
    @Test
    public void test2() {
        TblEmployee employee = new TblEmployee();
        employee.setGender("1");
        employeeDao.update(employee, null);
    }

    /*
    乐观锁插件OptimisticLockerInterceptor,表对应的实体类要加@version
    UPDATE tbl_employee  SET last_name=?,
    email=?,
    version=?  WHERE id=?   AND version=?
    只有设置的version与数据库中的version对应sql才会生效,不会报错
     */
    @Test
    public void test3() {
        TblEmployee employee = new TblEmployee();
        employee.setId(18);
        employee.setLastName("哆啦Amen");
        employee.setEmail("dd@amen");
        //必须设置setVersion,每更新一次version就会加1,只有version一样才能修改
        employee.setVersion(1);
        employeeDao.updateById(employee);
    }

}
