package com.chz.springbootconfig;

import com.chz.springbootconfig.pojo.PeopleProperties;
import com.chz.springbootconfig.pojo.PeopleValue;
import com.chz.springbootconfig.pojo.TestProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringbootConfigApplicationTests {
    @Autowired
    ApplicationContext context;
    @Autowired
    PeopleProperties peopleProperties;
//    @Autowired
    PeopleValue peopleValue;
    @Autowired
    TestProperty testProperty;
    @Test
    void contextLoads() {
    }
    @Test
    public void testConfigurationProperties(){
        System.out.println(peopleProperties);
    }
    @Test
    public void testValue(){
        System.out.println(peopleValue);
    }
    @Test
    public void setPropertySource(){
        System.out.println(context.getBean(TestProperty.class));
        System.out.println(testProperty);
    }

}
