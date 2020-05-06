package com.chz;

import com.chz.target.TargetObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringbootAopApplicationTests {
    @Autowired
    TargetObject targetObject;

    @Test
    void contextLoads() throws IOException {
        targetObject.targetMethod();
    }

}
