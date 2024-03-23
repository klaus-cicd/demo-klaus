package com.silas.demo.easyexcel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 事件记录
 *
 * @author Klaus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sg_event_item_record")
public class EventItemRecord extends VesselEntity {

    /**
     * 日志ID
     */
    private Long logbookId;

    /**
     * 记事栏
     */
    private String note;

    /**
     * 重大记事栏
     */
    private String importantNote;
}
