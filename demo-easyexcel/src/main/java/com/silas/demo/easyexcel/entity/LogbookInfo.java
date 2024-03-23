package com.silas.demo.easyexcel.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 航海日志基本信息
 *
 * @author Klaus
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sg_logbook_info")
public class LogbookInfo extends VesselEntity {

    /**
     * 日志编号
     */
    private String logCode;

    /**
     * 船名
     */
    private String vesselName;

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 航次编号
     */
    private String voyageCode;

    /**
     * 值班员核实状态
     */
    private Integer dutyVerifyStatus;

    /**
     * 大副
     */
    private String firstMate;

    /**
     * 大副核实状态
     */
    private Integer firstMateVerifyStatus;

    /**
     * 船长
     */
    private String captain;

    /**
     * 船长核实状态
     */
    private Integer captainVerifyStatus;

    /**
     * 记录天数
     */
    private Integer recordingDays;

    /**
     * 时区
     */
    private String timeZone;

    /**
     * 出发时间
     */
    private LocalDateTime startTime;

    /**
     * 到达时间
     */
    private LocalDateTime arrivalTime;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 安全等级
     */
    private Integer securityLevel;

    /**
     * 停泊港口
     */
    private String portOfRoute;

}
