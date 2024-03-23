package com.silas.demo.helloworld.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HelloWorldBeanUtil extends BeanUtils implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldBeanUtil.class);
    private static ApplicationContext applicationContext;

    public HelloWorldBeanUtil() {
    }

    public static <T> T getBean(Class<T> targetClass) {
        return applicationContext.getBean(targetClass);
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBeansOfType(clazz);
    }


    @Override
    public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
        if (applicationContext == null) {
            applicationContext = context;
        }

    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("BeanUtil.applicationContext未注入");
        }
    }
}
