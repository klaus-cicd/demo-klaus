package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 其他记录
 *
 * @author Klaus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sg_other_record")
public class OtherRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 燃料油结存
     */
    private String fuelOilInventory;

    /**
     * 柴油结存
     */
    private String dieselInventory;

    /**
     * 气缸油结存
     */
    private String cylinderOilInventory;

    /**
     * 主发电机柴油机滑油结存
     */
    private String mainEngineLubricatingOilInventory;

    /**
     * 副发电机柴油机滑油结存
     */
    private String auxiliaryEngineLubricatingOilInventory;

    /**
     * 燃料油新领量
     */
    private String fuelOilNewSupply;

    /**
     * 柴油新领量
     */
    private String dieselNewSupply;

    /**
     * 气缸油新领量
     */
    private String cylinderOilNewSupply;

    /**
     * 主发电机柴油机滑油新领量
     */
    private String mainEngineLubricatingOilNewSupply;

    /**
     * 副发电机柴油机滑油新领量
     */
    private String auxiliaryEngineLubricatingOilNewSupply;

    /**
     * 主发电柴油机今日柴油消耗
     */
    private String mainEngineDieselConsumptionToday;

    /**
     * 主发电柴油机今日气缸油消耗
     */
    private String mainEngineCylinderOilConsumptionToday;

    /**
     * 主发电柴油机今日滑油消耗
     */
    private String mainEngineLubricatingOilConsumptionToday;

    /**
     * 主发电柴油机今日燃料油消耗
     */
    private String mainEngineFuelOilConsumptionToday;

    /**
     * 副发电柴油机今日燃料油消耗
     */
    private String auxiliaryEngineFuelOilConsumptionToday;

    /**
     * 副发电柴油机今日柴油消耗
     */
    private String auxiliaryEngineDieselConsumptionToday;

    /**
     * 副发电柴油机今日气缸油消耗
     */
    private String auxiliaryEngineCylinderOilConsumptionToday;

    /**
     * 副发电柴油机今日滑油消耗
     */
    private String auxiliaryEngineLubricatingOilConsumptionToday;

    /**
     * 船位经度
     */
    private String longitude;

    /**
     * 船位纬度
     */
    private String latitude;

    /**
     * 理论航速
     */
    private String theoreticalSpeed;

    /**
     * 实际速度
     */
    private String actualSpeed;

    /**
     * 实际航程
     */
    private String actualDistance;

    /**
     * 航程累计
     */
    private String accumulatedDistance;

    /**
     * 海况
     */
    private String seaCondition;

    /**
     * 滑失率
     */
    private String slipRate;

    /**
     * 风向
     */
    private String windDirection;

    /**
     * 风力
     */
    private String windForce;

    /**
     * 废气锅炉蒸汽压力
     */
    private String wasteHeatBoilerSteamPressure;

    /**
     * 燃油锅炉蒸汽压力
     */
    private String fuelOilBoilerSteamPressure;

    /**
     * 燃油锅炉燃油压力
     */
    private String fuelOilBoilerFuelPressure;

    /**
     * 热油锅炉热油压力
     */
    private String thermalOilBoilerOilPressure;

    /**
     * 主发电柴油机1工作时间
     */
    @TableField("main_engine_one_working_time")
    private String mainEngine1WorkingTime;

    /**
     * 主发电柴油机2工作时间
     */
    @TableField("main_engine_two_working_time")
    private String mainEngine2WorkingTime;

    /**
     * 主发电柴油机3工作时间
     */
    @TableField("main_engine_three_working_time")
    private String mainEngine3WorkingTime;

    /**
     * 主发电柴油机4工作时间
     */
    @TableField("main_engine_four_working_time")
    private String mainEngine4WorkingTime;

    /**
     * 主发电柴油机5工作时间
     */
    @TableField("main_engine_five_working_time")
    private String mainEngine5WorkingTime;

    /**
     * 主发电柴油机6工作时间
     */
    @TableField("main_engine_six_working_time")
    private String mainEngine6WorkingTime;

    /**
     * 副发电柴油机工作时间
     */
    private String auxiliaryEngineWorkingTime;

    /**
     * 主发电柴油机1累计时间
     */
    @TableField("main_engine_one_accumulated_time")
    private String mainEngine1AccumulatedTime;

    /**
     * 主发电柴油机2累计时间
     */
    @TableField("main_engine_two_accumulated_time")
    private String mainEngine2AccumulatedTime;

    /**
     * 主发电柴油机3累计时间
     */
    @TableField("main_engine_three_accumulated_time")
    private String mainEngine3AccumulatedTime;

    /**
     * 主发电柴油机4累计时间
     */
    @TableField("main_engine_four_accumulated_time")
    private String mainEngine4AccumulatedTime;

    /**
     * 主发电柴油机5累计时间
     */
    @TableField("main_engine_five_accumulated_time")
    private String mainEngine5AccumulatedTime;

    /**
     * 主发电柴油机6累计时间
     */
    @TableField("main_engine_six_accumulated_time")
    private String mainEngine6AccumulatedTime;

    /**
     * 副发电柴油机累计时间
     */
    private String auxiliaryEngineAccumulatedTime;

    /**
     * 机舱温度
     */
    private String engineRoomTemperature;

    /**
     * 0-12时海水温度
     */
    @TableField("sea_temperature_one")
    private String seaTemperature1;

    /**
     * 12-24时海水温度
     */
    @TableField("sea_temperature_two")
    private String seaTemperature2;
}
