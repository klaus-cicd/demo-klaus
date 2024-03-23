package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 推进系统
 *
 * @author Klaus
 */

@Data
@NoArgsConstructor
@TableName("sg_propulsion_system")
@EqualsAndHashCode(callSuper = true)
public class PropulsionSystem extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 班次
     */
    private String dutyShift;

    /**
     * 推进系统
     */

    private Integer groupId;

    /**
     * 推进电机负荷
     */
    private String propulsionMotorLoad;

    /**
     * 转速
     */
    private String speed;

    /**
     * 减速齿轮箱油压
     */
    private String gearboxOilPressure;

    /**
     * 减速齿轮箱油温
     */
    private String gearboxOilTemperature;

    /**
     * 减速齿轮箱油位
     */
    private String gearboxOilLevel;

    /**
     * 前推力轴承温度
     */
    private String forwardThrustBearingTemp;

    /**
     * 后推力轴承温度
     */
    private String aftThrustBearingTemp;

    /**
     * 推力轴承油压
     */
    private String thrustBearingOilPressure;

    /**
     * 中间轴承温度1
     */
    private String intermediateBearingTemp1;

    /**
     * 中间轴承温度2
     */
    private String intermediateBearingTemp2;

    /**
     * 舰轴承温度1
     */
    private String shipBearingTempe1;

    /**
     * 舰轴承温度2
     */
    private String shipBearingTemp2;

    /**
     * 舰轴承温度3
     */
    private String shipBearingTemp3;

    /**
     * 舰轴封空压力
     */
    private String shipShaftSealAirPressure;

    /**
     * 励磁绕组温度U
     */
    private String excitationWindingTempU;

    /**
     * 励磁绕组温度V
     */
    private String excitationWindingTempV;

    /**
     * 励磁绕组温度W
     */
    private String excitationWindingTempW;

}
