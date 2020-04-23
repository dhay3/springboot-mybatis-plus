package com.chz.springbootmybatisplusdruid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.chz.springbootmybatisplusdruid.dao"})
@SpringBootApplication
public class SpringbootMybatisPlusDruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisPlusDruidApplication.class, args);
    }

}
