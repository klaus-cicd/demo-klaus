package com.silas.demo.mpflex.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Klaus
 */
@Data
@Table("test")
public class TbTest {

    /**
     * 标识主键为自增
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    private String content;
    private LocalDateTime time;
}
