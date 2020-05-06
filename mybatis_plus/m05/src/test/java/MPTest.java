import com.chz.mapper.TblEmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring-config.xml"})
public class MPTest {
    @Autowired
    TblEmployeeMapper employeeDao;

    @Test
    public void test() {
        //自定义sql还是通过传统mybatis 比较好
        employeeDao.deleteEmployee("tbl_employee");
//        System.out.println(tblEmployees);
    }

}
