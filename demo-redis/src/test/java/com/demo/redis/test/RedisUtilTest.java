package com.demo.redis.test;

import cn.hutool.core.date.StopWatch;
import com.klaus.demo.redis.RedisDemoApplication;
import com.klaus.fd.util.RedisUtil;
import com.klaus.fd.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootTest(classes = RedisDemoApplication.class)
public class RedisUtilTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, ?> redisTemplate;

    @Test
    void testStr() {
        String key = "name";
        RedisUtil.set(key, "klaus", Duration.ofSeconds(30));
        System.out.println(RedisUtil.get(key));
    }

    @Test
    void testList() {
        String key = "nameList";
        RedisUtil.setList(key, Arrays.asList("Wynn", "Alvaro", "Silas", "Damon", "Klaus"));
        List<String> list = RedisUtil.getStrList(key);
        log.info("{}", JsonUtil.toJsonStr(list));
        RedisUtil.del(key);
        log.info("{}", JsonUtil.toJsonStr(RedisUtil.getStrList(key)));

        RedisUtil.setList(key, Arrays.asList(123, 123, 12432, 234235, 234));
        List<Integer> listInt = RedisUtil.getIntList(key);
        log.info("{}", JsonUtil.toJsonStr(listInt));
        RedisUtil.del(key);
        log.info("{}", JsonUtil.toJsonStr(RedisUtil.getStrList(key)));
    }

    @Test
    void pipelineTest() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        list.forEach(i -> stringRedisTemplate.opsForSet().add("pipelineTest1", i));
        stopWatch.stop();
        log.info("for add time: {}", stopWatch.getLastTaskTimeMillis());

        stopWatch.start();
        stringRedisTemplate.executePipelined((RedisCallback<String>) connection -> {
            connection.openPipeline();
            byte[] key = "pipelineTest2".getBytes(StandardCharsets.UTF_8);
            list.forEach(i -> {
                byte[] value = i.getBytes(StandardCharsets.UTF_8);
                connection.sAdd(key, value);
            });
            return null;
        });
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }

}
