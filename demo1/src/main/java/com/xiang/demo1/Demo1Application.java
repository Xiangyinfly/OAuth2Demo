package com.xiang.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class Demo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class,args);
    }
}
