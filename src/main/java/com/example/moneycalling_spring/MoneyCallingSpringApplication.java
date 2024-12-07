package com.example.moneycalling_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.example.moneycalling_spring")
@EnableScheduling
public class MoneyCallingSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyCallingSpringApplication.class, args);
    }

}
