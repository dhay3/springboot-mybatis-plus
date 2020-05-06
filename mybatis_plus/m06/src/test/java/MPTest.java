import com.chz.dao.EmployeeDao;
import com.chz.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring-config.xml"})
public class MPTest {
    @Autowired
    EmployeeDao employeeDao;

    @Test//逻辑删除
//     UPDATE tbl_employee SET deleted=1 WHERE id=? AND deleted=0
    public void test() {
        employeeDao.deleteById(2);
    }

    @Test
    public void test2() {
        Employee employee = new Employee();
        employee.setLastName("王老五").setDate(new Date());
        employeeDao.insert(employee);
    }
}
