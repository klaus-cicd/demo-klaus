package com.silas.demo.tdengine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dream.system.annotation.Table;
import com.silas.demo.tdengine.anno.TDTableEntity;
import com.silas.demo.tdengine.anno.TDTagField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Klaus
 */
@Data
@Table
@TDTableEntity
@TableName("meters")
@EqualsAndHashCode(callSuper = true)
public class TdMeters extends TDengineEntityBase {

    private Float current;

    private Integer voltage;

    private Double phase;

    @TDTagField
    private Integer groupId;

    @TDTagField
    private String location;

}
