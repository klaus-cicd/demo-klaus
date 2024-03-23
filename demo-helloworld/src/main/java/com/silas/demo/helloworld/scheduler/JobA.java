package com.silas.demo.helloworld.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

/**
 * 动态工作
 *
 * @author klaus
 * @date 2024/01/04
 */
@Slf4j
@Component
public class JobA extends AbstractDynamicJob {

    protected JobA(TaskScheduler taskScheduler) {
        super(taskScheduler);
    }


    @Override
    protected String name() {
        return "JobA";
    }

    @Override
    protected Runnable execute() {
        return () -> log.info("Job {} execute, timeZone:{}, cron:{}", name(), timeZone.toZoneId(), cron);
    }

    @Override
    public String getCron() {
        // cron = "0 " + (LocalDateTime.now().getMinute() + 1) + " * * * ?";
        return cron;
    }
}
