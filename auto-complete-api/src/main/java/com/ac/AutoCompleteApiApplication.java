package com.ac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry point of auto-complete application deployment.
 * 
 * @author sarvesh
 */
@ComponentScan(basePackages="com.ac")
@SpringBootApplication
public class AutoCompleteApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoCompleteApiApplication.class, args);
    }
}
