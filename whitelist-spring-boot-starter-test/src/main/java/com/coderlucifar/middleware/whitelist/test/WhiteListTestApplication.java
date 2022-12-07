package com.coderlucifar.middleware.whitelist.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.coderlucifar.middleware.*")
public class WhiteListTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(WhiteListTestApplication.class, args);
    }
}
