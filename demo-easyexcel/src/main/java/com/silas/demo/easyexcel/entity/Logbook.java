package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 当日航海日志
 *
 * @author Klaus
 */

@Data
@NoArgsConstructor
@TableName("sg_logbook")
@EqualsAndHashCode(callSuper = true)
public class Logbook extends VesselEntity {


    /**
     * 0: 航海日志
     * 1: 轮机日志
     */
    private int logType;

    /**
     * 船名
     */
    private String vesselName;


    /**
     * 日志编号
     */
    private String code;

    /**
     * 启用日期
     */
    private LocalDateTime startDate;

    /**
     * 启用人用户ID
     */
    private String startUserId;

    /**
     * 启用人姓名
     */
    private String startUsername;

    /**
     * 停用日期
     */
    private LocalDateTime stopDate;

    /**
     * 停用人ID
     */
    private String stopUserId;

    /**
     * 停用人姓名
     */
    private String stopUsername;

    /**
     * 记录天数
     */
    private Integer recordingDays;

    /**
     * 状态
     * 0: 停用
     * 1: 启用
     */
    private Integer status;
}
