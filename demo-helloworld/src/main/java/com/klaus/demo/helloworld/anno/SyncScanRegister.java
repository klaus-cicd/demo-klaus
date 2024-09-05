package com.klaus.demo.helloworld.anno;

import cn.hutool.core.util.ArrayUtil;
import com.klaus.demo.comm.entity.BaseEntity;
import com.klaus.demo.helloworld.test.TestHandler;
import com.klaus.demo.helloworld.util.PackageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Silas
 */
@Configuration
@RequiredArgsConstructor
public class SyncScanRegister implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(SyncScanRegister.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableDataSync.class.getName()));
        if (mapperScanAttrs == null) {
            return;
        }
        // 获取包路径下的Class对象
        String[] packages = mapperScanAttrs.getStringArray("value");
        if (ArrayUtil.isEmpty(packages)) {
            return;
        }
        for (String pck : packages) {
            Set<Class<?>> classes = PackageUtil.getClasses(pck);
            if (CollectionUtils.isEmpty(classes)) {
                continue;
            }

            // 组装数据同步配置对象
            List<TestHandler> dataSyncConfigs = classes.stream()
                    .filter(clazz -> clazz.isAnnotationPresent(DataSync.class)
                            && clazz.getAnnotation(DataSync.class).enabled())
                    .map(clazz -> {
                        if (clazz.isInstance(BaseEntity.class)) {
                            return null;
                        }
                        Class<? extends BaseEntity> clazz1 = (Class<? extends BaseEntity>) clazz;
                        DataSync dataSync = clazz.getAnnotation(DataSync.class);
                        TestHandler<? extends BaseEntity> baseEntityTestHandler = new TestHandler<>(clazz1);
                        return baseEntityTestHandler;
                    }).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(dataSyncConfigs)) {
                return;
            }

            for (TestHandler dataSyncConfig : dataSyncConfigs) {
                BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(TestHandler.class, () -> dataSyncConfig).getBeanDefinition();
                registry.registerBeanDefinition("testHandler_" + dataSyncConfig.getEntityClass().getSimpleName(), beanDefinition);
                log.info("registerBeanDefinition:{}", "testHandler_" + dataSyncConfig.getEntityClass().getSimpleName());
            }
        }
    }
}
