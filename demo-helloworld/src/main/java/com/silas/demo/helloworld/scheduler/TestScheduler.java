package com.silas.demo.helloworld.scheduler;

import com.alibaba.fastjson2.JSON;
import com.silas.demo.helloworld.listener.event.TimeZoneChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * 测试调度程序
 *
 * @author klaus
 * @date 2024/01/04
 */
@Slf4j
@Component
public class TestScheduler {

    @Value("${zone}")
    private String zone;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void changeZone() {
        zone = "Asia/Chita";
        log.info(zone);
    }

    @Scheduled(cron = "00 53 10 * * ?", zone = "${zone}")
    public void job1() {
        System.out.println(zone);
        String[] availableIDs = TimeZone.getAvailableIDs();
        log.info("{}", JSON.toJSONString(availableIDs));
        // zone = "Asia/Singapore";
    }


    @Scheduled(cron = "0 0/2 * * * ?")
    public void timeZoneChange() {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        applicationEventPublisher.publishEvent(
                new TimeZoneChangeEvent(this, TimeZone.getTimeZone("GM+8"), localDateTime)
        );
    }
}
