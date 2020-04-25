package com.chz;

import com.chz.entity.Employee;
import com.chz.mapper.EmployeeMapper;
import com.chz.service.IEmployeeService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringbootMybatisRedisApplicationTests {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    IEmployeeService employeeService;
    /*
    凡是XxxTemplate都不需要自己配置,直接向spring拿即可
    按类型找,这里自动使用的是StringRedisTemplate,所以这里不能是使用Object
     */
    @Autowired
//            RedisTemplate<String, String> template;
            RedisTemplate<String, Object> template;

    @Test
    void contextLoads() {
    }

    @Test
    public void testConnect() {
//        employeeMapper.list(null,null).forEach(System.out::println);
        Employee employee1 = employeeMapper.get(35);
        Employee employee2 = employeeMapper.get(35);
    }

    /**
     * opsForValue() 对应redis的set,get
     * opsForHash() 对应redis的hset,hget,hmset,hmget
     * opsForValue() 对应redis的lpush,rpush,lpop,rpop,lrange
     * opsForSet() 对应redis 的set
     * opsForZset() 对应redis 的zset
     */
    @Test
    public void testRedis() {
        //设置键的序列化器,两者选一,但是这种的弊端就是每次都需要设置
//        template.setKeySerializer(RedisSerializer.string());
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(RedisSerializer.string());
        template.opsForValue().set("ping", "pong");
        //如果使用默认的ReidsTemplate只能使用key只能使用String类型和value是String类型的
//            template.opsForHash().put("stu","name",1);
//        System.out.println(template.opsForValue().get("ping"));
    }

    /*
    测试自定义redis模板
     */
    @Test
    public void testCustomerRedis() {
        Employee employee = new Employee();
        employee.setLastName("王利发").setAge(23).setEmail("zs@qq").setGender("1");
        //可以设置一个对象到redis中
//        template.opsForValue().set("emp", employee);
    }

    //测试redis缓存是否有效
    @Test
    public void test() {
        System.out.println(employeeService.get(16));
        System.out.println(employeeService.get(16));
    }
}
