package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 航行日志修改记录
 *
 * @author Klaus
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sg_logbook_log")
public class LogbookLog extends VesselEntity {
    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 类型
     * 参考枚举 com.sango.common.enums.LogbookType
     * 0: 航海日志
     * 1: 轮机日志
     */
    private int type;

    /**
     * 修改项
     */
    private int updateType;

    /**
     * 修改前内容
     */
    private String oldValue;

    /**
     * 修改后内容
     */
    private String newValue;


}
