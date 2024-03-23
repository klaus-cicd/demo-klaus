package com.silas.demo.tdengine.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Klaus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("td_test")
public class Test extends TDengineEntityBase {

    @TableField("`int`")
    private Integer a;

    @TableField("`bigint`")
    private Long bigint;

    private String code;

}
