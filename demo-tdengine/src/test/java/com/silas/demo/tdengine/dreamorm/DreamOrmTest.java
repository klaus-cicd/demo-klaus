package com.silas.demo.tdengine.dreamorm;

import com.dream.tdengine.mapper.FlexTdMapper;
import com.silas.demo.tdengine.entity.TdMeters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Klaus
 */
@SpringBootTest
public class DreamOrmTest {

    @Autowired
    private FlexTdMapper flexTdMapper;

    /**
     * 时间窗口查询
     *
     * @throws Exception
     */
    @Test
    public void testSliding() {
        List<TdMeters> list = flexTdMapper.select("*")
                .from("meters")
                .partitionBy("location")
                .interval("10m")
                .sliding("5m")
                .limit(1, 2)
                .list(TdMeters.class);
        System.out.println("查询结果：" + list);
    }
}
