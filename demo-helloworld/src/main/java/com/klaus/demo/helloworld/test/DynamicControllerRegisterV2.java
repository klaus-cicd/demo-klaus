package com.klaus.demo.helloworld.test;

import com.klaus.demo.helloworld.anno.EnableDataSync;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
public class DynamicControllerRegisterV2 implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 获取启动类
        Class<?> mainApplicationClass = applicationContext.getEnvironment().getProperty("spring.main.application-class", Class.class);
        if (mainApplicationClass != null) {
            // 查找启动类上的注解
            EnableDataSync enableDataSync = AnnotationUtils.findAnnotation(mainApplicationClass, EnableDataSync.class);
            if (enableDataSync != null) {
                String[] packages = enableDataSync.value();
                System.out.println("Scan packages: " + String.join(", ", packages));
                // 你可以在这里执行进一步的初始化逻辑
            }
        }
    }
}
