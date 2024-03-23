package com.silas.demo.easyexcel.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 航行记录
 *
 * @author Klaus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SailingRecordDTO extends VesselDTO {

    /**
     * ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 日志ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logbookId;

    /**
     * 陀螺罗经航向
     */
    private String gyroscopeHeading;

    /**
     * 陀螺罗经改正量
     */
    private String gyroscopeCorrection;


    /**
     * 磁罗经航向
     */
    private String magneticCompassHeading;

    /**
     * 磁罗经磁差
     */
    private String magneticCompassDeviation;


    /**
     * 磁罗经自差
     */
    private String magneticCompassVariation;


    /**
     * 真航向
     */
    private String trueHeading;


    /**
     * 风流压差
     */
    private Integer windFlowPressureDifference;


    /**
     * 计划航迹向
     */
    private String plannedTrackDirection;


    /**
     * 计程仪读数
     */
    private int odometerReading;

    /**
     * 实测时速
     */
    private String actualSpeed;


    /**
     * 天气现象
     */
    private String weatherPhenomenon;

    /**
     * 推进器 r/min
     */
    private Integer propulsionSystem;
}
