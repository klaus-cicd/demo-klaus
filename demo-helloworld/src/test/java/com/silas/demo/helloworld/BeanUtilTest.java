package com.silas.demo.helloworld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaus.fd.utils.BeanUtil;
import com.silas.demo.helloworld.controller.GzipController;
import com.silas.demo.helloworld.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Klaus
 */

@SpringBootTest
public class BeanUtilTest {

    @Test
    void testContext() {
        System.out.println(BeanUtil.getBean(GzipController.class));
    }

    @Test
    void testStatic() {
        System.out.println(TestUtil.testBeanUtil());
    }

    @Test
    void testGetName() {
        ObjectMapper objectMapper = BeanUtil.getBean(ObjectMapper.class);
        System.out.println(objectMapper);
    }

}
