package com.chz.springsecuritysms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"com.chz.springsecuritysms.mapper"})
@SpringBootApplication
public class SpringSecuritySmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecuritySmsApplication.class, args);
	}

}
