package com.klaus.demo.helloworld.scheduler;

import com.klaus.demo.helloworld.listener.event.TimeZoneChangeEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

/**
 * 时区更改监听器
 *
 * @author klaus
 * @date 2024/01/04
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public abstract class AbstractDynamicJob {
    @Setter
    protected TimeZone timeZone;
    protected String cron;
    protected final TaskScheduler taskScheduler;
    protected ScheduledFuture<?> scheduledFuture;

    @EventListener(classes = TimeZoneChangeEvent.class)
    protected void timeZoneChangeListener(TimeZoneChangeEvent timeZoneChangeEvent) {
        log.info("TimeZoneListener#onApplicationEvent, oldTimeZone:{}, newTimeZone:{}",
                timeZone, timeZoneChangeEvent.getTimeZone().toZoneId());

        timeZone = timeZoneChangeEvent.getTimeZone();
        cron = "0 " + timeZoneChangeEvent.getLocalDateTime().getMinute() + " * * * ?";
        removeOldScheduler();
        createJob();
    }

    /**
     * 创建任务
     */
    public void createJob() {
        CronTrigger cronTrigger = getCronTrigger();
        scheduledFuture = taskScheduler.schedule(execute(), cronTrigger);
        if (null != scheduledFuture) {
            // DynamicSchedulerContext.put(name(), scheduledFuture);
            log.info("Create job success, name:{}, cron:{}", name(), cronTrigger.getExpression());
            return;
        }
        log.info("Create job fail, name:{}, cron:{}", name(), cronTrigger.getExpression());
    }

    /**
     * 任务名称, 需要唯一
     *
     * @return {@link String}
     */
    protected abstract String name();

    /**
     * cron触发器
     *
     * @return {@link CronTrigger}
     */
    protected CronTrigger getCronTrigger() {
        return new CronTrigger(getCron(), timeZone);
    }

    /**
     * 执行
     *
     * @return {@link Runnable}
     */
    protected abstract Runnable execute();

    public void removeOldScheduler() {
        if (null != scheduledFuture) {
            scheduledFuture.cancel(false);
        }
        // Map<String, ScheduledFuture<?>> scheduledFutureMap = DynamicSchedulerContext.JOB_MAP;
        // if (!CollectionUtils.isEmpty(scheduledFutureMap)) {
        //     scheduledFutureMap.forEach((k, v) -> {
        //         v.cancel(false);
        //         log.info("Stop name:{}, result:{}", k, v.isCancelled());
        //         if (v.isCancelled()) {
        //             DynamicSchedulerContext.remove(k);
        //         }
        //     });
        // }
        // if (!CollectionUtils.isEmpty(scheduledFutureMap)) {
        //     log.info("Remaining job: {}", JSON.toJSONString(scheduledFutureMap));
        // }
    }

    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone("GMT+8");
    }

    /**
     * 得到cron
     *
     * @return {@link String}
     */
    public abstract String getCron();

}
