package com.klaus.demo.mp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Klaus
 */
@Data
public class FlywaySchemaHistory {

    @TableId
    private Long id;
    private String version;
    private Integer success;
    private Integer installedRank;
}
