package com.klaus.demo.mp.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.klaus.demo.comm.enums.TestEnum;
import com.klaus.demo.mp.service.TestService;
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
class TestServiceImplTest {

    @Resource
    private TestService testService;

    @Test
    void testEnumInsert() {
        com.klaus.demo.comm.entity.Test test = new com.klaus.demo.comm.entity.Test();
        test.setTestEnum(TestEnum.A);
        test.setId(IdUtil.getSnowflakeNextId());
        testService.save(test);
    }

    @Test
    void testEnumQuery() {
        List<com.klaus.demo.comm.entity.Test> list = testService.list();
        // fastjson和jackson可以收到@JsonValue的影响
        log.info(JSON.toJSONString(list));
        log.info(JsonUtil.toJsonStr(list));
        // hutool json工具类不受@JsonValue注解的影响
        log.info(JSONUtil.toJsonStr(list));
    }
}