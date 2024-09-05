package com.klaus.demo.helloworld.controller;

import com.alibaba.fastjson2.JSONObject;
import com.fd.web.response.Result;
import com.klaus.demo.helloworld.request.TestReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/hello-world")
    public String helloWorld(@RequestParam(required = false) String a) {
        String result = "Hello world! " + (StringUtils.hasText(a) ? a : "");
        log.info("Request hello world, a={}", a);
        return result;
    }


    @PostMapping("/test-enum")
    public TestReq testEnumReq(@RequestBody @Validated TestReq testReq) {
        return testReq;
    }

    @PostMapping("/req-list")
    public Result<List<String>> reqList(@RequestBody @Validated List<String> list) {
        return Result.ok(list);
    }

    @GetMapping("/json")
    public Result<JSONObject> getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "klaus");
        jsonObject.put("age", null);
        System.out.println(jsonObject);
        return Result.ok(jsonObject);
    }
}
