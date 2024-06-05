package com.klaus.demo.helloworld.scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 动态调度器上下文
 *
 * @author klaus
 * @date 2024/01/04
 */
public class DynamicSchedulerContext {

    public static final Map<String, ScheduledFuture<?>> JOB_MAP = new ConcurrentHashMap<>();


    public synchronized static void put(String name, ScheduledFuture<?> scheduler) {
        JOB_MAP.put(name, scheduler);
    }

    public synchronized static void remove(String name) {
        JOB_MAP.remove(name);
    }


    public synchronized static Map<String, ScheduledFuture<?>> reset() {
        ConcurrentHashMap<String, ScheduledFuture<?>> map = new ConcurrentHashMap<>(JOB_MAP);
        JOB_MAP.clear();
        return map;
    }
}
