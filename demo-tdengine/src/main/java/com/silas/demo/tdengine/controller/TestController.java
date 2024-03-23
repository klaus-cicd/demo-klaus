package com.silas.demo.tdengine.controller;

import com.alibaba.fastjson2.JSON;
import com.silas.demo.tdengine.entity.Test;
import com.silas.demo.tdengine.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
@RestController
@RequestMapping("/api/demo/tdengine")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }


    @PostMapping
    public Boolean add(Integer a) {
        List<Test> tests = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            Test test = new Test();
            test.setCode("test");
            test.setA(a);
            test.setBigint((long) a);
            test.setTs(new Timestamp(LocalDateTime.of(2023, 1, 1, 12, a).toEpochSecond(ZoneOffset.UTC)));
        }
        return testService.saveBatch(tests);
    }


    @GetMapping
    public List<Test> list() {
        List<Test> list = testService.list();
        log.info(JSON.toJSONString(list));
        return list;
    }

}
