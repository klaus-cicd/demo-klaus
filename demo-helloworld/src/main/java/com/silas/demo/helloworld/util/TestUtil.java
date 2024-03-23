package com.silas.demo.helloworld.util;

import com.silas.demo.helloworld.controller.HelloWorldController;

/**
 * @author Klaus
 */
public class TestUtil {

    public static String testBeanUtil() {
        return HelloWorldBeanUtil.getBean(HelloWorldController.class).toString();
    }
}
