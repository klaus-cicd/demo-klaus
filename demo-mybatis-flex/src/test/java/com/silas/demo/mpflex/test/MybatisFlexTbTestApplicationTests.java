package com.silas.demo.mpflex.test;

import com.silas.demo.mpflex.mapper.TestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MybatisFlexTbTestApplicationTests {

    @Autowired
    private TestMapper testMapper;

    @Test
    void contextLoads() {

    }

}