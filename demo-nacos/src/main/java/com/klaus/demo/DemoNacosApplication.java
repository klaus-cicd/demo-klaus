package com.klaus.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author Klaus
 */
@SpringBootApplication
@Slf4j
public class DemoNacosApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(DemoNacosApplication.class, args);
        Environment environment = configurableApplicationContext.getBean(Environment.class);
        log.info(environment.getProperty("server.port"));
        log.info(environment.getProperty("user.name"));
    }
}