package com.klaus.demo.tdorm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalus.tdengineorm.annotation.TdField;
import com.kalus.tdengineorm.annotation.TdTag;
import com.kalus.tdengineorm.entity.BaseTdEntity;
import com.kalus.tdengineorm.enums.TdFieldTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author Klaus
 */
@Data
@TableName("td_orm_test_b")
@EqualsAndHashCode(callSuper = true)
public class TestTdEntityB extends BaseTdEntity {
    @TdField(type = TdFieldTypeEnum.NCHAR, length = 20)
    private String name;
    @TdField(type = TdFieldTypeEnum.DOUBLE)
    private Double age;
    @TdField(type = TdFieldTypeEnum.DOUBLE)
    private Double db1;
    @TdField(type = TdFieldTypeEnum.DOUBLE)
    private Double db2;
    @TdField(type = TdFieldTypeEnum.FLOAT)
    private Float fl1;
    @TdField(type = TdFieldTypeEnum.BIGINT)
    private Long id;
    @TdField(type = TdFieldTypeEnum.TIMESTAMP)
    private Timestamp createTime;

    @TableField("a_b_cd_efgg_a")
    @TdField(type = TdFieldTypeEnum.NCHAR, length = 20)
    private String aBCdEfggA;

    @TdTag
    @TdField(type = TdFieldTypeEnum.NCHAR, length = 20)
    private String tg1;

    @TdTag
    @TdField(type = TdFieldTypeEnum.NCHAR, length = 20)
    private Integer tg2;
}
