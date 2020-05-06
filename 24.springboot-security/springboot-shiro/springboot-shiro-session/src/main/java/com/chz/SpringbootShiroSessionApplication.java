package com.chz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.chz.mapper"})
@SpringBootApplication
public class SpringbootShiroSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootShiroSessionApplication.class, args);
	}

}
