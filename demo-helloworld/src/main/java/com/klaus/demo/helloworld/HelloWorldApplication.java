package com.klaus.demo.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Klaus
 */
@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}
