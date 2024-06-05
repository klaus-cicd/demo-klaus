package com.klaus.demo.helloworld.controller;

import com.fd.web.response.Result;
import com.klaus.demo.helloworld.entity.Test;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Klaus
 */
public class DynamicController<DTO> {

    @ResponseBody
    public Result<Long> get(@RequestParam("id") Long id) {
        System.out.println("get......");
        return Result.ok(id);
    }


    @ResponseBody
    public Result<DTO> postSingle(@RequestBody DTO dto) {
        System.out.println("postSingle......");
        return Result.ok(dto);
    }

    @ResponseBody
    public Result<List<String>> postSimple(@RequestBody List<String> testList) {
        System.out.println("postSimple......");
        return Result.ok(testList);
    }

    @ResponseBody
    public Result<List<Test>> postTest(@RequestBody List<Test> testList) {
        System.out.println("postTest......");
        return Result.ok(testList);
    }

    @ResponseBody
    public Result<List<DTO>> post(@RequestBody List<DTO> testList) {
        System.out.println("post......");
        return Result.ok(testList);
    }
}
