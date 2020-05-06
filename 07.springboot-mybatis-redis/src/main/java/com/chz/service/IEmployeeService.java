package com.chz.service;

import com.chz.entity.Employee;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 如果不指定缓存,spring默认采用内置简单缓存,生产一般要指定具体的缓存
 * 缓存的是方法的返回值
 */
@CacheConfig(cacheNames = {"emps"})//配置统一的cacheNames
public interface IEmployeeService {
    /*
    @Cacheable,在调用方法前会查看value中对应key的缓存(方法的返回值),如果有就不会调用函数
       value(cacheNames): 缓存域,可以理解为redis中Hset的key
       key: 缓存键,可以理解为redis中Hset的field,但是这里存的是参数的值
     */
    @Cacheable(key = "#p0")//key存的是参数的值,p0表示第一个参数
    public List<Employee> list(String name, Integer id);
    //spEL表达式支持级联
    //避免用户重复提交
    @Cacheable(key = "#p0.id")
    public boolean add(Employee employee);

    //因为缓存存的是返回值,返回Employee是为了修改缓存中值,避免脏读
    @CachePut(key = "#p0.id")
    Employee update(Employee employee);

    //    @Cacheable(key = "#p0")//一般用唯一的值,数据库采用主键
    //keyGenerator使用自定义的key生成策略,value取值采用就近原则
//    @Cacheable(value = "userInfo", keyGenerator = "keyGenerator")
    @Cacheable(cacheNames = "userInfo", keyGenerator = "keyGenerator")
    public Employee get(Integer id);

    @CacheEvict(key = "#p0")//删除时删除value中key为id值的缓存
    public boolean delete(Integer id);
}
