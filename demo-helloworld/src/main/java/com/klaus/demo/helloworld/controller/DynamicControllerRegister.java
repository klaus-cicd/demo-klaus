package com.klaus.demo.helloworld.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import com.klaus.demo.helloworld.entity.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Klaus
 */
@Slf4j
@Configuration
public class DynamicControllerRegister implements ApplicationRunner {
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    private List<String> getScanPackages() {
        return Arrays.asList("com/klaus/demo/helloworld");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("动态创建接口");
        List<String> scanPackages = getScanPackages();
        if (CollectionUtils.isEmpty(scanPackages)) {
            return;
        }
        for (String scanPackage : scanPackages) {

            PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourceResolver.getResources(scanPackage + "/**/*.class");
            if (ArrayUtil.isEmpty(resources)) {
                continue;
            }

            for (Resource resource : resources) {
                Class<?> clazz = ClassUtils.forName(resource.getURL().toString().replace("file:", ""), this.getClass().getClassLoader());
                System.out.println(clazz.getName());
            }
        }


        RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();
        config.setPatternParser(requestMappingHandlerMapping.getPatternParser());

        RequestMappingInfo getApiMappingInfo = RequestMappingInfo
                .paths("/api/dynamic/get")
                .methods(RequestMethod.GET)
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .options(config)
                .build();

        ParameterizedTypeReference<List<Test>> testListReference = new ParameterizedTypeReference<List<Test>>() {
        };

        DynamicController<ParameterizedTypeReference<Test>> testDynamicController = new DynamicController<>();
        Class<? extends DynamicController> aClass = testDynamicController.getClass();


        requestMappingHandlerMapping.registerMapping(getApiMappingInfo, testDynamicController, ReflectUtil.getMethodByName(aClass, "get"));

        requestMappingHandlerMapping.registerMapping(
                RequestMappingInfo
                        .paths("/api/dynamic/post/single")
                        .methods(RequestMethod.POST)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .options(config)
                        .build(),
                testDynamicController, ReflectUtil.getMethodByName(aClass, "postSingle")
        );


        requestMappingHandlerMapping.registerMapping(RequestMappingInfo
                        .paths("/api/dynamic/post")
                        .methods(RequestMethod.POST)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .options(config)
                        .build(),
                testDynamicController, ReflectUtil.getMethodByName(aClass, "post"));

        requestMappingHandlerMapping.registerMapping(RequestMappingInfo
                        .paths("/api/dynamic/post-simple")
                        .methods(RequestMethod.POST)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .options(config)
                        .build(),
                testDynamicController, ReflectUtil.getMethodByName(aClass, "postSimple"));

        requestMappingHandlerMapping.registerMapping(RequestMappingInfo
                        .paths("/api/dynamic/post-test")
                        .methods(RequestMethod.POST)
                        .consumes(MediaType.APPLICATION_JSON_VALUE)
                        .options(config)
                        .build(),
                testDynamicController,
                ReflectUtil.getMethodByName(aClass, "postTest"));


        // 获取所有的handler
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        log.info("接口列表: {}", handlerMethods);
    }

    public static void main(String[] args) {
        DynamicController<ParameterizedTypeReference<Test>> dynamicController = new DynamicController<>();
        Type[] genericInterfaces = dynamicController.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            System.out.println(genericInterface.getTypeName());
        }

    }
}
