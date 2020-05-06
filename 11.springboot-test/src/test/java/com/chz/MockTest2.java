package com.chz;

import com.chz.controller.EmployeeController;
import com.chz.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * 推荐使用该方法
 */
@SpringBootTest
public class MockTest2 {
    //测试controller
    private MockMvc mockMvc;
    //如果使用细粒度更高的controller, 中有service的@Autowire就需要在测试模块中注入, 否则就会报空指针异常
    @Autowired
    private EmployeeController employeeController;

    //细粒度跟高的测试
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                //可以设置多个controller
                employeeController)
                //可以统一设置状态
                .alwaysExpect(MockMvcResultMatchers.status().isOk())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void test1() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/employee/get/{id}", 15));
    }

    @Test
    public void test2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/employee/get/{id}", 15))
                //期望返回值是json类型的
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                //可以拿到request和response中的参数
                .andReturn();
        System.out.println(mvcResult.getRequest().getServletPath());
        System.out.println(mvcResult.getResponse().getContentType());
    }

    /*
    如果接收除`application/x-www-form-urlencoded`外的content-type要指定请求的contentType
     */
    @Test
    @Transactional//在测试模块加@Transacitonal不会真正执行, 所以不会对数据造成影响
    public void test3() throws Exception {
        //多使用Json的序列化对象
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = new Employee();
        employee.setLastName("战五渣").setAge(23).setGender("2").setEmail("333");
        //将对象序列化为json串
        String value = mapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/employee/save")
                //如果接收的Json数据要指定contentType
                .contentType(MediaType.APPLICATION_JSON)
                .content(value.getBytes()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Rollback//可以加上@Rollback以清除显示事务回滚, 默认true
    @Transactional
    public void test4() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("formId", "2");
        map.add("toId", "3");
        map.add("age", "10");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/tran")
                .params(map));
    }

    /*
    检查返回的json内容
     */
    @Test
    public void test5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/employee/get?name={name}", "Black"))
                //指定返回的json值, 这里的$表示json的根节点
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
                        .value("Black"));
    }

    /*
    检查返回的视图层
     */
    @Test
    public void test6() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/test"))
                .andExpect(MockMvcResultMatchers
                        //检查返回的视图层, 这里的viewName不能与请求的uri相同否则会抛出异常
                        .view().name("view"));
    }

    @Test
    public void test7() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/test"))
                .andExpect(MockMvcResultMatchers
//                        请求转发语重定向
//                       .forwardedUrl("/index.html")
                        .redirectedUrl("/view.html"));
    }

    @Test
    public void test8() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/model"))
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("key"))
                .andExpect(MockMvcResultMatchers.model().attribute("key", "value"));
    }

}
