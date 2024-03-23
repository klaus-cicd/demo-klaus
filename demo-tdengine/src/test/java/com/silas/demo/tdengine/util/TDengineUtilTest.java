package com.silas.demo.tdengine.util;

import com.klaus.fd.utils.DateUtil;
import com.silas.demo.tdengine.entity.TdMeters;
import com.silas.demo.tdengine.strategy.DateDynamicTbNameStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootTest
public class TDengineUtilTest {

    @Resource
    private TDengineUtil tDengineUtil;

    private StopWatch stopWatch;

    @BeforeEach
    void before() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    @AfterEach
    void after() {
        stopWatch.stop();

        log.info("elapsed time: {}ms", stopWatch.getLastTaskTimeMillis());
    }

    @Test
    void testInsertSingle() {
        for (int i = 0; i < 1000000; i++) {
            TdMeters tdMeters = new TdMeters();
            tdMeters.setTs(new Timestamp(DateUtil.nowTs() + i));

            tdMeters.setGroupId(4);
            tdMeters.setLocation("California.SanFrancisco");

            tdMeters.setVoltage(i);
            tdMeters.setPhase(222d);
            tdMeters.setCurrent(333f);

            tDengineUtil.insertUsing(tdMeters, new DateDynamicTbNameStrategy());
        }
    }


    @Test
    void testInsertBatch() {
        List<TdMeters> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            TdMeters tdMeters = new TdMeters();
            tdMeters.setTs(new Timestamp(DateUtil.nowTs() + i * 1000L));

            tdMeters.setGroupId(4);
            tdMeters.setLocation("California.SanFrancisco");

            tdMeters.setVoltage(i);
            tdMeters.setPhase(222d);
            tdMeters.setCurrent(333f);

            list.add(tdMeters);
        }

        tDengineUtil.batchInsertUsing(TdMeters.class, list, new DateDynamicTbNameStrategy());
    }


}
