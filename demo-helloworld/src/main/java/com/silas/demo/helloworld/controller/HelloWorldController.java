package com.silas.demo.helloworld.controller;

import cn.hutool.json.JSONUtil;
import com.silas.demo.helloworld.enums.TestEnum;
import com.silas.demo.helloworld.request.TestReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Klaus
 */
@Slf4j
@RestController
public class HelloWorldController {

    @GetMapping("/hello-world")
    public String helloWorld(@RequestParam(required = false) String a) {
        String result = "Hello world! " + (StringUtils.hasText(a) ? a : "");
        log.info("Request hello world, a={}", a);
        return result;
    }


    @PostMapping("/test-enum")
    public String testEnumReq(@RequestBody @Validated TestReq testReq) {
        TestEnum testEnum = testReq.getTestEnum();
        return JSONUtil.toJsonStr(testReq);
    }
}
