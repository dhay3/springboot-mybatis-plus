package com.chz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class MockTest1 {
    //测试controller
    private MockMvc mockMvc;
    //也可使用属性的形式来配置
//    @Autowired
//    private WebApplicationContext wac;
//    @Autowired
//    IEmployeeService employeeService;

    @BeforeEach//这里的wac会自动配置,全局测试,推荐使用该配置
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /*
  通过MockMvc模拟发送请求
   */
    @Test
    public void setMockMvc() throws Exception {
        //模拟get,通过url传值
//        mockMvc.perform(MockMvcRequestBuilders.
//                get("/employee/get/{id}", 15))
//        .andDo(MockMvcResultHandlers.print());

//        //模拟post,通过参数传值
//        mockMvc.perform(MockMvcRequestBuilders.
//                post("/employee/get?name={id}", "Black"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//        //也可以通过param传值
        mockMvc.perform(MockMvcRequestBuilders.
//        也可以使用params 传一个map
        post("/employee/get").param("name", "z3f"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


        //模拟文件上传
//        mockMvc.perform(MockMvcRequestBuilders.
//                multipart("/employee/upload")
//                .file("file", "文件内容".getBytes())
//                .contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
    }
}
