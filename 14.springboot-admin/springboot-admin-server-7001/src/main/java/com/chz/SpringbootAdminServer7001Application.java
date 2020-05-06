package com.chz;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//表示当前类是adminServer,注册中心
@EnableAdminServer
@SpringBootApplication
public class SpringbootAdminServer7001Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdminServer7001Application.class, args);
    }

}
