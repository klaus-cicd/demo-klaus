package com.silas.demo.tdengine.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 按天分表解析
 */
public class DaysTableNameParser implements TableNameHandler {

    @Override
    public String dynamicTableName(String sql, String tableName) {
        if (!"test".equals(tableName)) {
            return tableName;
        }

        // 自定义逻辑, 也可以根据当天日期来, 这里只是随意写了一个随机规则

        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String[] tableSuffix = {"001", "002", "003"};
        // String[] tableSuffix = {"004"};
        return tableName + "_" + tableSuffix[(int)(Math.random() * tableSuffix.length)];
        // return tableName + "_" + format;
    }
}