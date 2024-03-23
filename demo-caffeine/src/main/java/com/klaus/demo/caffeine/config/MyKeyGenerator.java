package com.klaus.demo.caffeine.config;

import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;

@Component
public class MyKeyGenerator extends SimpleKeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object generate = super.generate(target, method, params);
        String format = MessageFormat.format("{0}{1}{2}", method.toGenericString(), generate);
        return format;
    }
}