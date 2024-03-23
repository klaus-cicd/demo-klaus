package com.silas.demo.helloworld;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.silas.demo.helloworld.util.GzipUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class GzipTest {

    @Test
    void testGzip() throws IOException {
        HttpRequest httpRequest = HttpRequest.post("http://localhost:8081/api/gzip");

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        httpRequest.body(GzipUtil.compress(JSON.toJSONString(list)));
        httpRequest.header("Content-Encoding", "gzip");
        try (HttpResponse httpResponse = httpRequest.executeAsync()) {
            log.info("httpResponse: {}", JSON.toJSONString(httpResponse));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
