package com.klaus.demo.test;

/**
 * @author Klaus
 */

import com.kalus.tdengineorm.enums.SelectJoinSymbolEnum;
import com.kalus.tdengineorm.enums.TdSelectFuncEnum;
import com.kalus.tdengineorm.mapper.TDengineMapper;
import com.kalus.tdengineorm.strategy.AbstractDynamicNameStrategy;
import com.kalus.tdengineorm.wrapper.AbstractTdQueryWrapper;
import com.kalus.tdengineorm.wrapper.TdQueryWrapper;
import com.kalus.tdengineorm.wrapper.TdWrappers;
import com.klaus.demo.tdorm.TdOrmApplication;
import com.klaus.demo.tdorm.entity.TestTdEntityB;
import com.klaus.fd.utils.DateUtil;
import com.klaus.fd.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest(classes = TdOrmApplication.class)
public class TestA {

    @Resource
    private TDengineMapper tdengineMapper;


    @Test
    void testCreateStableTable() {
        int stableTable = tdengineMapper.createStableTable(TestTdEntityB.class);
        System.out.println("testCreateStableTable result =====> " + stableTable);
    }

    @Test
    void insertUsing() {
        long time = DateUtil.nowTs();

        TestTdEntityB testTdEntityB = new TestTdEntityB();
        testTdEntityB.setTs(new Timestamp(time));
        testTdEntityB.setAge(12D);
        testTdEntityB.setFl1(12F);
        testTdEntityB.setName("12");
        testTdEntityB.setId(12L);
        testTdEntityB.setDb2(12D);
        testTdEntityB.setDb2(22D);
        testTdEntityB.setABCdEfggA("12");
        testTdEntityB.setTg1("12");
        testTdEntityB.setTg2(12);
        testTdEntityB.setCreateTime(new Timestamp(time));

        tdengineMapper.insertUsing(testTdEntityB, new AbstractDynamicNameStrategy() {
            @Override
            public String dynamicTableName(String s) {
                return s + "_0";
            }
        });
    }

    @Test
    void insert() {
        long time = DateUtil.nowTs();

        TestTdEntityB testTdEntityB = new TestTdEntityB();
        testTdEntityB.setTs(new Timestamp(time));
        testTdEntityB.setAge(12D);
        testTdEntityB.setFl1(12F);
        testTdEntityB.setName("12");
        testTdEntityB.setId(12L);
        testTdEntityB.setDb2(12D);
        testTdEntityB.setDb2(22D);
        testTdEntityB.setABCdEfggA("12");
        testTdEntityB.setCreateTime(new Timestamp(time));

        tdengineMapper.insert(testTdEntityB, new AbstractDynamicNameStrategy() {
            @Override
            public String dynamicTableName(String s) {
                return s + "_0";
            }
        });
    }


    /**
     * 查询最新一条数据
     */
    @Test
    void getLastOne() {
        System.out.println(tdengineMapper.getLastOneByTs(TestTdEntityB.class));
    }


    /**
     * 按照指定条件查询单个数据(如果有多个则自动获取列表第一个)
     */
    @Test
    void getOne() {
        System.out.println(tdengineMapper.getOne(
                TdWrappers.queryWrapper(TestTdEntityB.class)
                        .selectAll()
                        .eq(TestTdEntityB::getTs, "2024-05-16T02:51:00.971")));
    }


    /**
     * 批量插入使用
     */
    @Test
    void batchInsertUsing() {
        List<TestTdEntityB> list = new ArrayList<>();
        LocalDateTime localDateTime = DateUtil.now();
        for (int i = 0; i < 10000; i++) {
            Timestamp timestamp = DateUtil.parseToTimestamp(localDateTime.plusHours(i));
            TestTdEntityB testTdEntityB = new TestTdEntityB();
            testTdEntityB.setTs(timestamp);
            testTdEntityB.setAge((double) i);
            testTdEntityB.setFl1(13F);
            testTdEntityB.setName("" + i);
            testTdEntityB.setId((long)i);
            testTdEntityB.setDb2(13D);
            testTdEntityB.setDb2(23D);
            testTdEntityB.setABCdEfggA("" + i);
            // 对于同一张子表的Tag字段一定是相同的
            testTdEntityB.setTg1("13");
            testTdEntityB.setTg2(13);
            testTdEntityB.setCreateTime(timestamp);
            list.add(testTdEntityB);
        }
        tdengineMapper.batchInsertUsing(TestTdEntityB.class, list, new AbstractDynamicNameStrategy() {
            @Override
            public String dynamicTableName(String s) {
                return s + "_1";
            }
        });
    }


    @Test
    void testLambdaList() {
        AbstractTdQueryWrapper<TestTdEntityB> wrapper =
                TdWrappers.queryWrapper(TestTdEntityB.class)
                        .selectAll()
                        .eq(TestTdEntityB::getTg1, "13")
                        .limit(5000);

        List<TestTdEntityB> list = tdengineMapper.list(wrapper);
        log.info("{}", JsonUtil.toJsonStr(list));
    }

    @Test
    void testStrList() {
        TdQueryWrapper<TestTdEntityB> wrapper = TdWrappers.queryWrapper(TestTdEntityB.class)
                .selectAll()
                .eq("tg1", "13").limit(2, 3000);
        System.out.println(tdengineMapper.list(wrapper));
    }

    @Test
    void simpleWindowFuncLambdaQuery() {
        TdQueryWrapper<TestTdEntityB> wrapper = TdWrappers.queryWrapper(TestTdEntityB.class)
                .selectFunc(TdSelectFuncEnum.FIRST, TestTdEntityB::getTs, TestTdEntityB::getName, TestTdEntityB::getAge)
                .selectFunc(TdSelectFuncEnum.LAST, TestTdEntityB::getId)
                .eq(TestTdEntityB::getTg1, "13")
                .intervalWindow("30m");
        System.out.println(tdengineMapper.list(wrapper));
    }


    /**
     * 简单窗口函数使用
     */
    @Test
    void simpleWindowFuncQuery() {
        TdQueryWrapper<TestTdEntityB> wrapper = TdWrappers.queryWrapper(TestTdEntityB.class)
                .selectFunc(TdSelectFuncEnum.FIRST, "ts", "name", "age")
                .selectFunc(TdSelectFuncEnum.LAST, "id")
                .eq("tg1", "13")
                .intervalWindow("30m");
        System.out.println(tdengineMapper.list(wrapper));
    }


    @Test
    void complexWindowFunc() {
        TdQueryWrapper<TestTdEntityB> wrapper = TdWrappers.queryWrapper(TestTdEntityB.class);
    }


    @Test
    void nestingLambdaQuery() {
        TdQueryWrapper<TestTdEntityB> wrapper = TdWrappers.queryWrapper(TestTdEntityB.class)
                .innerQueryWrapper(innerWrapper ->
                        innerWrapper
                                .selectFunc(TdSelectFuncEnum.FIRST, TestTdEntityB::getTs)
                                .eq(TestTdEntityB::getTg1, "13")
                                .intervalWindow("30m")
                )
                .selectAll()
                .orderByDesc(TestTdEntityB::getTs);

        System.out.println(tdengineMapper.list(wrapper));
    }

    @Test
    void selectJoinQuery() {
        TdQueryWrapper<TestTdEntityB> wrapper = TdWrappers.queryWrapper(TestTdEntityB.class)
                .selectAll()
                .innerQueryWrapper(innerWrapper -> innerWrapper
                        .selectCalc(TestTdEntityB::getAge, consumer -> {
                            consumer.select(TdSelectFuncEnum.FIRST, TestTdEntityB::getAge)
                                    .operate(SelectJoinSymbolEnum.PLUS)
                                    .select(TdSelectFuncEnum.LAST, TestTdEntityB::getAge);
                        })
                        .eq(TestTdEntityB::getTg1, "13")
                        .intervalWindow("30m")
                )
                .orderByDesc(TestTdEntityB::getAge)
                .limit(12);

        System.out.println(tdengineMapper.list(wrapper));
    }
}
