package com.silas.demo.tdengine.enums;

import lombok.Getter;

/**
 * @author Klaus
 */

@Getter
public enum TdEnums {
    /**
     * TDengine唯一的时间类型
     */
    TIMESTAMP("TIMESTAMP", "时间戳", "Timestamp", 0),
    INT("INT", "整型", "Integer", 0),
    INT_UNSIGNED("INT UNSIGNED", "无符号整数", "Integer", 0),
    BIGINT("BIGINT", "长整型", "Long", 0),
    BIGINT_UNSIGNED("BIGINT UNSIGNED", "长整型", "Long", 0),
    FLOAT("FLOAT", "浮点型", "Float", 0),
    DOUBLE("DOUBLE", "双精度浮点型", "Double", 0),
    BINARY("BINARY", "单字节字符串", "Char", 1),
    SMALLINT("SMALLINT", "短整型", "Integer", 0),
    SMALLINT_UNSIGNED("SMALLINT UNSIGNED", "Integer", "Timestamp", 0),
    TINYINT("TINYINT", "单字节整型", "Integer", 0),
    TINYINT_UNSIGNED("TINYINT UNSIGNED", "Integer", "Timestamp", 0),
    BOOL("BOOL", "布尔型", "Boolean", 0),
    NCHAR("NCHAR", "多字节字符串", "String", 1),
    /**
     * 只有标签（TAG）可以用 JSON 类型
     */
    JSON("JSON", "JSON", "String", 0),
    VARCHAR("VARCHAR", "BINARY类型的别名", "Char", 1),
    GEOMETRY("GEOMETRY", "几何类型", "未知", 1);

    /**
     * TDEngine字段类型
     */
    private String filedType;

    /**
     * 字段类型描述
     */
    private String desc;

    /**
     * 对应的Java类型
     */
    private String javaType;

    /**
     * 是否可以限制长度
     * 0: falase
     * 1: true
     */
    private int needLengthLimit;

    private TdEnums(String filedType, String desc, String javaType, int needLengthLimit) {
        this.filedType = filedType;
        this.desc = desc;
        this.javaType = javaType;
        this.needLengthLimit = needLengthLimit;
    }
}
