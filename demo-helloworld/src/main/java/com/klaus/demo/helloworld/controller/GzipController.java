package com.klaus.demo.helloworld.controller;

import com.fd.web.response.Result;
import com.klaus.demo.helloworld.util.GzipUtil;
import com.klaus.demo.helloworld.util.HelloWorldBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * gzip控制器
 *
 * @author klaus
 * @date 2024/01/03
 */
@Slf4j
@RestController
@RequestMapping("/api/gzip")
// @DependsOn("com.klaus.fd.utils.BeanUtil")
public class GzipController {

    @Resource
    public HelloWorldBeanUtil helloWorldBeanUtil;

    @PostConstruct
    public void init() {
        HelloWorldController bean = HelloWorldBeanUtil.getBean(HelloWorldController.class);
        System.out.println(bean);
    }

    @PostMapping
    public Result<String> gzipTest(@RequestBody byte[] data) throws IOException {
        byte[] decompress = GzipUtil.decompress(data);
        String dataStr = new String(decompress);
        log.info(dataStr);
        return Result.ok("success");
    }


}
