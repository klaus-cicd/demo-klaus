package com.silas.demo.helloworld.request;

import com.silas.demo.helloworld.enums.TestEnum;
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
}
