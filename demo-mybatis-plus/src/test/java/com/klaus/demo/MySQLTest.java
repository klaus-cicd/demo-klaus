package com.klaus.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
class MySQLTest {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void testA() {
        String sql = "SELECT NOW() as ts";
        List<Timestamp> ts = namedParameterJdbcTemplate.query(sql, (rs, row) -> rs.getTimestamp("ts"));
        Timestamp timestamp = ts.get(0);
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        log.info("{}", localDateTime);
    }

}