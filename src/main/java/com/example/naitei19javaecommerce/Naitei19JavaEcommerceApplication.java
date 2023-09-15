package com.example.naitei19javaecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Naitei19JavaEcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Naitei19JavaEcommerceApplication.class, args);
    }

}
