package com.chz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringbootMybatisRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisRedisApplication.class, args);
    }

}
