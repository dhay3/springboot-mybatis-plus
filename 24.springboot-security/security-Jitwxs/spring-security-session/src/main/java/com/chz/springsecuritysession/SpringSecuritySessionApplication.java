package com.chz.springsecuritysession;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@MapperScan({"com.chz.springsecuritysession.mapper"})
@SpringBootApplication
public class SpringSecuritySessionApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecuritySessionApplication.class, args);
	}
}
