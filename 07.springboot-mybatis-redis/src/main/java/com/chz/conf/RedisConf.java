package com.chz.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

//redis配置类
@Configuration
public class RedisConf extends CachingConfigurerSupport {
    /*
    配置自定义RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //配置连接工厂
        template.setConnectionFactory(factory);
        template.setKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        return template;
    }

    /*
    org.springframework.cache.interceptor包下的
    自定义key的生成策略,对应@Cacheable中的keyGenerator
    实例对象+方法名+参数名
     */
    @Bean
    public KeyGenerator keyGenerator() {
        /*
        target调用缓存方法的实例
        method调用缓存的方法
        params方法的参数
         */
        return (tagert, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(tagert.getClass().getName())
                    .append(method.getName());
            for (Object param : params) {
                sb.append(param.toString());
            }
            //返回key
            return sb.toString();
        };
    }

    /*
    自定义缓存管理
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        return RedisCacheManager.builder(factory).
                //默认缓存策略
                        cacheDefaults(redisCacheConfiguration(600L)).
                //配置不同缓存域,不同过期时间
                        withInitialCacheConfigurations(RedisCacheConfigurationMap()).
                //更新删除上锁
                        transactionAware().
                        build();
    }

    /*
    配置redis的cache策略
     */
    private RedisCacheConfiguration redisCacheConfiguration(Long sec) {
        return RedisCacheConfiguration.defaultCacheConfig().
                //设置key的序列化,采用stringRedisSerializer
                        serializeKeysWith
                        (RedisSerializationContext.SerializationPair.fromSerializer(keySerializer())).
                //设置value的序列化，采用Jackson2JsonRedis
                        serializeValuesWith
                        (RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer())).
                //设置cache的过期策略
                        entryTtl(Duration.ofSeconds(sec)).
                //不缓存null的值
                        disableCachingNullValues();
    }

    /*
    不同缓存域,不同过期时间,map的key可以被@Cacheable中的value使用
     */
    private Map<String, RedisCacheConfiguration> RedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("userInfo", redisCacheConfiguration(3000L));
        redisCacheConfigurationMap.put("otherInfo", redisCacheConfiguration(1000L));
        return redisCacheConfigurationMap;
    }

    /*
    key采用序列化策略
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /*
    value采用序列化策略
     */
    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //序列化所有类包括jdk提供的
        ObjectMapper om = new ObjectMapper();
        //设置序列化的域(属性,方法etc)以及修饰范围,Any包括private,public 默认是public的
        //ALL所有方位,ANY所有修饰符
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //enableDefaultTyping 原来的方法存在漏洞,2.0后改用如下配置
        //指定输入的类型
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);
        return serializer;
    }
}
