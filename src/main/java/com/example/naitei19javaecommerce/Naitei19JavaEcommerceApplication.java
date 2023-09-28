package com.example.naitei19javaecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
public class Naitei19JavaEcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(Naitei19JavaEcommerceApplication.class, args);
    }
}