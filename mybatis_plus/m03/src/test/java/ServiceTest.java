import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.entity.TblEmployee;
import com.chz.service.impl.TblEmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/conf/spring-config.xml")
public class ServiceTest {
    @Autowired
    TblEmployeeServiceImpl tblEmployeeService;

    @Test
    public void test1() {
        TblEmployee one = tblEmployeeService.
                getOne(new QueryWrapper<TblEmployee>().eq("last_name", "z3f"));
        System.out.println(one);
    }
}
