package com.klaus.demo.log4j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Klaus
 */

@Slf4j
@SpringBootApplication
public class Log4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Log4jApplication.class, args);
        log.info("Demo log4j run: {}", "begin");
    }
}
