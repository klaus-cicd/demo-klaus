package com.silas.demo.tdengine.mapper;

import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.klaus.fd.utils.DateUtil;
import com.silas.demo.tdengine.entity.TdMeters;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Slf4j
@SpringBootTest
class TestMapperTest {

    // @Resource
    // private TdMetersMapper tdMetersMapper;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    void testInsertA() {
        TdMeters tdMeters = new TdMeters();
        tdMeters.setTs(new Timestamp(DateUtil.nowTs()));
        tdMeters.setGroupId(3);
        tdMeters.setLocation("California.SanFrancisco");

        tdMeters.setVoltage(111);
        tdMeters.setPhase(222d);
        tdMeters.setCurrent(333f);

        // int result = tdMetersMapper.tdSave(tdMeters);
        // log.info("Result: {}", result == 1);
    }


    @Test
    void testInsertB() {
        String sql = "INSERT INTO d1001 (ts, current, voltage, phase) "
                + "values (:ts,:current,:voltage,:phase)";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ts", new Timestamp(DateUtil.nowTs()))
                .addValue("current", 333f)
                .addValue("voltage", 111)
                .addValue("phase", 222d);

        namedParameterJdbcTemplate.update(sql, parameters);
    }


    @Test
    void testInsertAutoCreate() {
        String sql = "INSERT INTO meters_001 "
                + "USING meters "
                + "TAGS (:groupId, :location) "
                + "(ts, current, voltage, phase)  values (:ts,:current,:voltage,:phase) ";


        for (int i = 0; i < 100000; i++) {
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("ts", new Timestamp(DateUtil.nowTs() + i))
                    .addValue("current", i)
                    .addValue("voltage", i)
                    .addValue("groupId", 3)
                    .addValue("location", "California.SanFrancisco")
                    .addValue("phase", 222d);

            namedParameterJdbcTemplate.update(sql, parameters);
        }
    }


    @Test
    void dynamicTbNameInsert() {
        TdMeters tdMeters = new TdMeters();
        tdMeters.setTs(new Timestamp(DateUtil.nowTs()));

        // tdMeters.setGroupId(4);
        // tdMeters.setLocation("California.SanFrancisco");

        tdMeters.setVoltage(111);
        tdMeters.setPhase(222d);
        tdMeters.setCurrent(333f);

        // log.info("dynamicTbNameInsert result: {}", tdMetersMapper.insert(tdMeters));
    }


    @Test
    void dynamicTbNameQuery() {
        MPJLambdaWrapper<TdMeters> tdMetersMPJLambdaWrapper = MPJWrappers.<TdMeters>lambdaJoin()
                // 设置动态表名
                .setTableName(name -> name + "_001");

        // List<TdMeters> tdMeters = tdMetersMapper.selectList(tdMetersMPJLambdaWrapper);
        // log.info("result: {}", tdMeters);
    }


}