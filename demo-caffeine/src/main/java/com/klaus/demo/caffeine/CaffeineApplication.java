package com.klaus.demo.caffeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Klaus
 */
@EnableCaching
@SpringBootApplication
public class CaffeineApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaffeineApplication.class, args);
    }
}