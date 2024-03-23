package com.klaus.demo.comm.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author Klaus
 */
@Getter
public enum TestEnum {

    /**
     * A
     */
    A(0, "a"),
    /**
     * B
     */
    B(1, "b");

    private final int value;
    @JsonValue
    private final String name;

    TestEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }


}
