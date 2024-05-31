package com.klaus.demo.tdorm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalus.tdengineorm.annotation.TdTag;
import com.kalus.tdengineorm.entity.TdBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Klaus
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("vessel_shm_sg_10086_smart")
public class VesselShm extends TdBaseEntity {

    private Double current;
    private Integer voltage;
    @TableField("phase")
    private Float phase333;
    @TdTag
    private String tenantId;
}
