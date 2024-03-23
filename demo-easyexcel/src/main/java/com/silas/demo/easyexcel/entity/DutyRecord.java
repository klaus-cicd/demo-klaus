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
@TableName("sg_duty_record")
public class DutyRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 驾驶员
     */
    private String helmsman;

    /**
     * 水手
     */
    private String sailor;
}
