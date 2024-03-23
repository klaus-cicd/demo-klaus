package com.silas.demo.helloworld.enums;

import lombok.Getter;

/**
 * @author Klaus
 */
@Getter
public enum TestEnum {

    /**
     * 分钟
     */
    MINUTE(0),

    /**
     * 小时
     */
    HOUR(1),

    /**
     * 日
     */
    DAY(2);


    private final Integer value;

    TestEnum(Integer value) {
        this.value = value;
    }
}