package com.chz.springsecurirylogout;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"com.chz.springsecurirylogout.mapper"})
@SpringBootApplication
public class SpringSecurityLogoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityLogoutApplication.class, args);
	}

}
