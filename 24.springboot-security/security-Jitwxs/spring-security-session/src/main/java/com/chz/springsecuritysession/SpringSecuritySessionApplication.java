package com.chz.springsecuritysession;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.chz.springsecuritysession.mapper"})
@SpringBootApplication
public class SpringSecuritySessionApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecuritySessionApplication.class, args);
	}
}
