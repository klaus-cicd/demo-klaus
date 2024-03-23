package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 主发电机组
 *
 * @author Klaus
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("sg_main_generator_set")
public class MainGeneratorSet extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 值班班次
     */
    private String dutyShift;

    /**
     * 主发电机组
     */
    private int groupId;

    /**
     * 前增压排气温度
     */
    private String frontTurboExhaustTemp;

    /**
     * 后增压排气温度
     */
    private String rearTurboExhaustTemp;

    /**
     * 增压器转速
     */
    private String turbochargerSpeed;

    /**
     * 增压器滑油压力
     */
    private String turbochargerOilPressure;

    /**
     * 扫气压力
     */
    private String scavengingAirPressure;

    /**
     * 扫气温度
     */
    private String scavengingAirTemp;

    /**
     * 曲拐箱压
     */
    private String bendBoxPressure;

    /**
     * 高温水压
     */
    private String highTempWaterPressure;

    /**
     * 进口高温水温度
     */
    private String highTempWaterInletTemp;

    /**
     * 出口高温水温度
     */
    private String highTempWaterOutletTemp;

    /**
     * 低温水压力
     */
    private String lowTempWaterPressure;

    /**
     * 进口低温水温度
     */
    private String lowTempWaterInletTemp;

    /**
     * 出口低温水温度
     */
    private String lowTempWaterOutletTemp;

    /**
     * 滑油压力
     */
    private String oilPressure;
}
