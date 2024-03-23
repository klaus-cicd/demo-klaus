package com.silas.demo.tdengine.schedule;

import com.silas.demo.tdengine.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 自动创建第二天需要的表
 *
 * @author Klaus
 */
@Slf4j
@Component
public class CreateTDChildTbSchedule {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Scheduled(cron = "0 0 20 * * ?")
    public void createNextDateChildTb() {
        // 获取所有需要创建子表的超级表
        List<String> tableNames = EntityUtils.getTableNames("com.silas.demo.tdengine");
        if (CollectionUtils.isEmpty(tableNames)) {
            return;
        }

        for (String tableName : tableNames) {
            String childTbName = tableName + "_" + LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            // 建表逻辑
            String sql = "CREATE TABLE :childTbName USEING :stb (?,?) TAGS (?,?)";

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("childTbName", childTbName);
            mapSqlParameterSource.addValue("stb", tableName);

            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        }

    }

}
