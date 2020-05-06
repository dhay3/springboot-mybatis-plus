package com.chz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//会扫描所有的filter,servlet,listener
//@ServletComponentScan(basePackages = {"com.chz.conf.component"})
@SpringBootApplication
public class SpringbootServletFilterListenerInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootServletFilterListenerInterceptorApplication.class, args);
    }

}
