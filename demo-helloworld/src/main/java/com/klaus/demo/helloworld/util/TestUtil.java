package com.klaus.demo.helloworld.util;

import com.klaus.demo.helloworld.controller.HelloWorldController;

/**
 * @author Klaus
 */
public class TestUtil {

    public static String testBeanUtil() {
        return HelloWorldBeanUtil.getBean(HelloWorldController.class).toString();
    }
}
