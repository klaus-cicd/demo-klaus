package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 正午记录
 *
 * @author Klaus
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("sg_noon_record")
public class NoonRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 推算经度
     */
    private String estimatedLongitude;

    /**
     * 推算纬度
     */
    private String estimatedLatitude;

    /**
     * 实测经度
     */
    private String measuredLongitude;

    /**
     * 实测纬度
     */
    private String measuredLatitude;

    /**
     * 重油消耗
     */
    private String heavyFuelConsumption;

    /**
     * 重油添加量
     */
    private String heavyFuelAddition;

    /**
     * 重油库存
     */
    private String heavyFuelInventory;

    /**
     * 轻油消耗
     */
    private String lightFuelConsumption;

    /**
     * 轻油添加量
     */
    private String lightFuelAddition;

    /**
     * 轻油库存
     */
    private String lightFuelInventory;

    /**
     * 柴油消耗
     */
    private String dieselConsumption;

    /**
     * 柴油添加量
     */
    private String dieselAddition;

    /**
     * 柴油库存
     */
    private String dieselInventory;

    /**
     * 淡水消耗
     */
    private String freshwaterConsumption;

    /**
     * 淡水添加量
     */
    private String freshwaterAddition;

    /**
     * 淡水库存
     */
    private String freshwaterInventory;

    /**
     * 晨光结束时间
     */
    private long morningLightEndTime;

    /**
     * 夜光开始时间
     */
    private long eveningLightStartTime;

    /**
     * 日夜距离
     */
    private String dayNightDistance;

    /**
     * 日夜平均速度
     */
    private String dayNightAverageSpeed;

    /**
     * 航行时间
     */
    private String voyageTime;

    /**
     * 累计实际距离
     */
    private String accumulatedActualDistance;

    /**
     * 总航行时间
     */
    private String totalVoyageTime;

    /**
     * 平均速度
     */
    private String averageSpeed;

    /**
     * 距离下一港口
     */
    private String distanceToNextPort;

    /**
     * 计划到达时间
     */
    private long plannedArrivalTime;
}
