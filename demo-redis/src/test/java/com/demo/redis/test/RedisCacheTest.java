package com.demo.redis.test;

import com.klaus.demo.redis.RedisDemoApplication;
import com.klaus.fd.util.RedisCache;
import com.klaus.fd.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootTest(classes = RedisDemoApplication.class)
public class RedisCacheTest {
    @Test
    void testStr() {
        String key = "name";
        RedisCache.set(key, "klaus");
        System.out.println(RedisCache.get(key));

        RedisCache.del(key);
        System.out.println(RedisCache.get(key));
    }

    @Test
    void testList() {
        String key = "nameList";
        RedisCache.setList(key, Arrays.asList("Wynn", "Alvaro", "Silas", "Damon", "Klaus"));
        List<String> list = RedisCache.getStrList(key);
        log.info("{}", JsonUtil.toJsonStr(list));
        RedisCache.del(key);
        log.info("{}", JsonUtil.toJsonStr(RedisCache.getStrList(key)));

        RedisCache.setList(key, Arrays.asList(123, 123, 12432, 234235, 234));
        List<Integer> listInt = RedisCache.getIntList(key);
        log.info("{}", JsonUtil.toJsonStr(listInt));
        RedisCache.del(key);
        log.info("{}", JsonUtil.toJsonStr(RedisCache.getStrList(key)));
    }
}
