package com.example.Pruebaf5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.Pruebaf5")  
public class Pruebaf5Application {
    public static void main(String[] args) {
        SpringApplication.run(Pruebaf5Application.class, args);
    }
}
