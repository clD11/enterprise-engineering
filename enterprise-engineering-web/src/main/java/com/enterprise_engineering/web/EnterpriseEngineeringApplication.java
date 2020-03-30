package com.enterprise_engineering.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.enterprise_engineering")
public class EnterpriseEngineeringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseEngineeringApplication.class, args);
    }

}