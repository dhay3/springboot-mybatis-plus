package com.chz;

import com.chz.pojo.SpELBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class SpringbootSpelApplicationTests {
    @Autowired
    SpELBean spELBean;

    @Test
    void contextLoads() throws IllegalAccessException, InstantiationException {
        //这里因为newInstance,出来的对象不再归spring管,所以值为null
//        SpELBean spELBean = ReflectionUtils.newInstance(clazz);
//        List<Field> fields = ReflectionUtils
//                .findFields(clazz, ObjectUtils::isEmpty,
//                        ReflectionUtils.HierarchyTraversalMode.BOTTOM_UP);
//        for (Field field : fields) {
//            ReflectionUtils.makeAccessible(field);
//            System.out.println(field.get(spELBean));
//        }
    }

    @Test
    public void test() {
        System.out.println(spELBean);
        Arrays.stream(spELBean.toString().split("=")).forEach(System.out::println);
    }
}
