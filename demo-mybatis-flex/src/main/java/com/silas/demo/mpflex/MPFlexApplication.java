package com.silas.demo.mpflex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Klaus
 */
@SpringBootApplication
@MapperScan("com.silas.demo.mpflex.mapper")
public class MPFlexApplication {

    public static void main(String[] args) {
        SpringApplication.run(MPFlexApplication.class, args);
    }
}
