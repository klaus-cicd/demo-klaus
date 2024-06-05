package com.silas.demo.tdengine.tdorm;

import com.kalus.tdengineorm.util.TDengineMapper;
import com.kalus.tdengineorm.wrapper.AbstractTdQueryWrapper;
import com.kalus.tdengineorm.wrapper.TdWrappers;
import com.klaus.fd.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootTest
public class TestA {

    @Resource
    private TDengineMapper tdengineMapper;

    @Test
    void testList() {
        AbstractTdQueryWrapper<TestEntityA> wrapper = TdWrappers.lambdaQueryWrapper(TestEntityA.class)
                .selectAll()
                .intervalWindow("1m")
                .orderByDesc(TestEntityA::getTs)
                .limit(1, 2);

        log.info("sql: {}", wrapper.getSql());
        log.info("param: {}", wrapper.getParamsMap());

        List<TestEntityA> list = tdengineMapper.list(wrapper);
        log.info("{}", JsonUtil.toJsonStr(list));
    }
}
