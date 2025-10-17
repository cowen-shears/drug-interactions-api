package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.example.app",
    "com.example.domain",
    "com.example.adapters"
})
public class DrugInteractionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DrugInteractionsApplication.class, args);
    }
}
