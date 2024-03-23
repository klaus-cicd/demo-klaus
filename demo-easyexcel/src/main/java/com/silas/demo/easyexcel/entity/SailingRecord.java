package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sg_sailing_record")
@EqualsAndHashCode(callSuper = true)
public class SailingRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 数据的时效时间, 非数据创建时间, 用于在Excel内展示
     */
    private String strTime;

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
