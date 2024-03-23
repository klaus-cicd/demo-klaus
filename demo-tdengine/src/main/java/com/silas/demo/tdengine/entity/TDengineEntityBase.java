package com.silas.demo.tdengine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * TDengine数据库通用字段
 *
 * @author Klaus
 */
@Data
@NoArgsConstructor
public class TDengineEntityBase {

    private Timestamp ts;
}
