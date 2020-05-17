package com.chz.springsecuriyathorization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"com.chz.springsecuriyathorization.mapper"})
@SpringBootApplication
public class SpringSecuriyAthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecuriyAthorizationApplication.class, args);
	}

}
