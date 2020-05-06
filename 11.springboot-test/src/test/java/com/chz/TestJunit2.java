package com.chz;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/*

 */
@SpringBootTest
public class TestJunit2 {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeTransaction
    public void beforeTran() {
        System.out.println("beforeTran...");
    }

    @AfterTransaction
    public void afterTran() {
        System.out.println("afterTran...");
    }

    /*
    测试JdbcTestUtils ,ReflectionUtils
    会实际生效
    使用@Transactional(注意这里的用法与普通的事务不一样), 能执行方法但是不会对数据库产生影响
     */
    @Test
//    @Commit
//    @Rollback
    @Transactional
    public void testTran() {
        System.out.println(JdbcTestUtils.countRowsInTable(jdbcTemplate, "tbl_employee"));
        JdbcTestUtils.deleteFromTableWhere(jdbcTemplate, "tbl_employee",
                "last_name='Black'");
        System.out.println("test transaction...");
    }

    @Test
    public void test() {
        System.out.println("test----------------------");
    }

}
