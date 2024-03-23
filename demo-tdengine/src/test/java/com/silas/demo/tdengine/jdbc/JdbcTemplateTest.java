package com.silas.demo.tdengine.jdbc;

import com.alibaba.fastjson2.JSON;
import com.klaus.fd.constant.DateConstant;
import com.klaus.fd.utils.DateUtil;
import com.silas.demo.tdengine.entity.TdMeters;
import com.silas.demo.tdengine.strategy.DateDynamicTbNameStrategy;
import com.silas.demo.tdengine.util.TDengineUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@SpringBootTest
public class JdbcTemplateTest {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private TDengineUtil tDengineUtil;

    public boolean checkDbExist(String dbName) {
        String sql = "SHOW DATABASES";
        List<String> databaseNameList = jdbcTemplate.query(sql, (rs, row) -> rs.getString(1));
        log.info("{}", JSON.toJSONString(databaseNameList));
        if (CollectionUtils.isEmpty(databaseNameList)) {
            return false;
        }

        return databaseNameList.contains(dbName);
    }

    @Test
    void testShowDataBase() {
        String dbName = "default";
        log.info("testShowDataBase, dbName:{}, result:{}", dbName, checkDbExist(dbName));
    }

    @Test
    void testInstant() {
        LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);
        log.info("nowLocalDateTime: {}", nowUtc);
        long ts = nowUtc.toInstant(ZoneOffset.UTC).toEpochMilli();
        log.info("ts: {}", ts);
        Instant instant = nowUtc.toInstant(ZoneOffset.UTC);
        log.info("instant: {}", instant.toString());

        TimeZone.setDefault(TimeZone.getTimeZone(DateConstant.ZONE_ID_UTC));
        log.info("Timestamp: {}", new Timestamp(ts));
    }

    @Test
    void testInstant8() {
        LocalDateTime shanghaiLocalDateTime = LocalDateTime.now();
        log.info("nowLocalDateTime: {}", shanghaiLocalDateTime);
        long ts = shanghaiLocalDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        log.info("ts: {}", ts);
        Instant instant = shanghaiLocalDateTime.toInstant(ZoneOffset.UTC);
        log.info("instant: {}", instant.toString());

        TimeZone.setDefault(TimeZone.getTimeZone(DateConstant.ZONE_ID_UTC));
        log.info("Timestamp: {}", new Timestamp(ts));
    }

    @Test
    void testTimestamp() {
        String iso8601TimeStr = "2024-01-02T22:15:44.025+08:00";
        long timestamp = 1672785344025L;
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String formattedString = formatter.format(zonedDateTime);

        System.out.println(formattedString);
    }

    @Test
    void testInsert() {
        TdMeters tdMeters = new TdMeters();
        LocalDateTime utcNow = DateUtil.now();
        log.info("utcNow: {}", utcNow);

        Instant instant = utcNow.toInstant(ZoneOffset.UTC);
        log.info("instant: {}", instant.toString());
        log.info("ts: {}", instant.toEpochMilli());

        tdMeters.setTs(Timestamp.from(instant));
        tdMeters.setGroupId(2);
        tdMeters.setLocation("California.SanFrancisco");
        tdMeters.setVoltage(1);
        tDengineUtil.insertUsing(tdMeters, new DateDynamicTbNameStrategy());

    }


    @Test
    void testQuery() {
        String sql = "SELECT * FROM `meters_20240102`";
        List<TdMeters> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TdMeters.class));
        for (TdMeters item : list) {
            Timestamp ts = item.getTs();
            log.info("item ts.toInstant: {}", ts.toInstant());
            log.info("item ts: {}", ts.toInstant().toEpochMilli());
        }
    }

    @Test
    void test6030() throws ClassNotFoundException, SQLException {
        Class.forName("com.taosdata.jdbc.TSDBDriver");
        String jdbcUrl = "jdbc:TAOS://taosdemo.com:6030/default?user=root&password=taosdata";
        Connection conn = DriverManager.getConnection(jdbcUrl);
        PreparedStatement preparedStatement = conn.prepareStatement("show databases");
        ResultSet resultSet = preparedStatement.executeQuery();

        if (null == resultSet) {
            return;
        }

        String string = resultSet.getString(1);
        System.out.println(string);
    }
}
