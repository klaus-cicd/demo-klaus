package com.klaus.demo.helloworld.request;

import com.klaus.demo.helloworld.enums.TestEnum;
import com.klaus.demo.helloworld.enums.TestJsonValueEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Klaus
 */
@Data
public class TestReq {

    /**
     * 测试枚举类
     */
    @NotNull(message = "Time span is missing")
    private TestEnum testEnum;

    private TestJsonValueEnum testJsonValueEnum;
}
