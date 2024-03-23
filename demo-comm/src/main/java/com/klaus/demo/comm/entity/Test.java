package com.klaus.demo.comm.entity;

import com.klaus.demo.comm.enums.TestEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Klaus
 */
@Data
public class Test {

    private Long id;

    private String content;

    private LocalDateTime time;

    private TestEnum testEnum;
}
