package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 气象海况记录
 *
 * @author Klaus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sg_weather_condition_record")
public class WeatherConditionRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 气温干度
     */
    private String temperatureHumidity;

    /**
     * 气温湿度
     */
    private String temperatureMoisture;

    /**
     * 海温
     */
    private String seaTemperature;

    /**
     * 风向
     */
    private String windDirection;

    /**
     * 风级
     */
    private String windForce;

    /**
     * 云状
     */
    private String cloudShape;

    /**
     * 云量
     */
    private String cloudCoverage;

    /**
     * 浪向
     */
    private String waveDirection;

    /**
     * 浪级
     */
    private String waveIntensity;
}
