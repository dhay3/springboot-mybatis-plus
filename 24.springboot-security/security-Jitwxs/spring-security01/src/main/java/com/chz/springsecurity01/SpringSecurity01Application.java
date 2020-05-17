package com.chz.springsecurity01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"com.chz.springsecurity01.mapper"})
@SpringBootApplication
public class SpringSecurity01Application {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity01Application.class, args);
	}

}
