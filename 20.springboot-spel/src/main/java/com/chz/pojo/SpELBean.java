package com.chz.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 测试 SpEL
 *
 * @author 82341
 */
@Data
@Component
//可以通过@ConfigurationProperties来自动绑定
//@ConfigurationProperties(prefix = "test2")
@PropertySource({"classpath:test.properties"})
public class SpELBean {
    // @Value 在spring包下, 必须将所在类注入到ioc中才会生效
    @Value("#{'老王'.concat('八')}")
    private String name;
    //调用注入到ioc中的bean, 支持三目运算
    @Value("#{spELBean.name.length()==3?-100:100}")
    private Integer age;
    // Elvis表达式, 与${}不同的是,${}使用Elvis表达式不用带?
    @Value("#{null?:100}")
    private Double score;
    @Value("#{'100kg'.toUpperCase()}")
    private String weight;
    /* 内置对象相当于
        system.getProperties
        map通过这种形式的来获取单个值*/
    @Value("#{systemProperties['user.dir']}")
    private String dir;
    //使用 T(...) 可以调用Jdk中的一些工具类的静态方法
    @Value("#{T(Math).random()*10}")
    private Double num;
    // T(...)对应Class类型
    @Value("#{T(Double)}")
    private Class<?> aClass;
    @Value("#{{'a','b','c'}}")
    private List<String> list1;
    //自定义的properties属性绑定list
    @Value("#{'${test2.list}'.split(',')}")
    private List<Integer> list3;
    //通过自定义的properties属性绑定map, key不带双引号
    @Value("#{${test2.map}")
    private Map<String, String> map;
}
