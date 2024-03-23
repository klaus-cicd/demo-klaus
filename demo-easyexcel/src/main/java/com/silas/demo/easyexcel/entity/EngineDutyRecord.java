package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 值班记录
 *
 * @author Klaus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sg_engine_duty_record")
public class EngineDutyRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 班次
     */
    private String shift;

    /**
     * 主发电机组
     */
    private int groupId;

    /**
     * 值班记事
     */
    private String note;

    /**
     * 值班轮机员
     */
    private String watchEngineer;
}
