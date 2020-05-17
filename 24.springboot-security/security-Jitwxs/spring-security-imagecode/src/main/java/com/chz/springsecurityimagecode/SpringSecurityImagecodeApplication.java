package com.chz.springsecurityimagecode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.chz.springsecurityimagecode.mapper")
@SpringBootApplication
public class SpringSecurityImagecodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityImagecodeApplication.class, args);
	}

}
