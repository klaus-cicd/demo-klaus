package com.silas.demo.tdengine.tdorm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalus.tdengineorm.annotation.TdTag;
import com.silas.demo.tdengine.entity.BaseTdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Klaus
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("vessel_shm_sg_10086_smart")
public class TestEntityA extends BaseTdEntity {
    private Double current;

    private Integer voltage;

    @TableField("phase")
    private Float phase333;

    @TdTag
    private String tenantId;
}
