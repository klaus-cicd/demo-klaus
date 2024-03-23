package com.silas.demo.helloworld;

import com.klaus.fd.utils.DateUtil;
import com.klaus.fd.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootTest
public class JsonUtilTest {

    @Test
    public void test() {
        List<JsonTestEntity> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            JsonTestEntity jsonTestEntity = new JsonTestEntity();
            jsonTestEntity.setUsername("Klaus: " + i);
            jsonTestEntity.setTimestamp(new Timestamp(DateUtil.nowUtcTs()));
            jsonTestEntity.setLocalDate(LocalDate.now());
            jsonTestEntity.setLocalDateTime(LocalDateTime.now());

            list.add(jsonTestEntity);
        }
        String jsonListStr = JsonUtil.toJsonStr(list);
        log.info("[jsonListStr]: {}", jsonListStr);

        List<JsonTestEntity> testEntities = JsonUtil.parseToList(jsonListStr, JsonTestEntity.class);
        log.info("[testEntities]: {}", JsonUtil.toJsonStr(testEntities));

        log.info("-----------------------------");

        JsonTestEntity jsonTestEntity = new JsonTestEntity();
        jsonTestEntity.setUsername("Silas-single");
        jsonTestEntity.setTimestamp(new Timestamp(DateUtil.nowUtcTs()));
        jsonTestEntity.setLocalDate(LocalDate.now());
        jsonTestEntity.setLocalDateTime(LocalDateTime.now());

        String jsonStr = JsonUtil.toJsonStr(jsonTestEntity);
        log.info("[jsonStr]: {}", jsonStr);
        JsonTestEntity jsonTestEntity1 = JsonUtil.parseToObject(jsonStr, JsonTestEntity.class);
        log.info("[testEntity1]: {}", JsonUtil.toJsonStr(jsonTestEntity1));
    }


    @Test
    void  testDeserializer(){
        Map<String, String> map = new HashMap<>();
        map.put("localDateTime", "2024-03-24T14:10:35.163");

        JsonTestEntity jsonTestEntity = JsonUtil.parseToObject(JsonUtil.toJsonStr(map), JsonTestEntity.class);
        log.info(JsonUtil.toJsonStr(jsonTestEntity));
    }

    @Test
    void testGetTimeStr(){
        System.out.println(LocalDateTime.now());
    }
}
