package com.klaus.demo.helloworld.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * 时区更改事件
 *
 * @author klaus
 * @date 2024/01/04
 */
@Getter
public class TimeZoneChangeEvent extends ApplicationEvent {

    @Setter
    private TimeZone timeZone;
    @Setter
    private LocalDateTime localDateTime;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public TimeZoneChangeEvent(Object source) {
        super(source);
    }

    public TimeZoneChangeEvent(Object source, TimeZone timeZone, LocalDateTime localDateTime) {
        super(source);
        this.timeZone = timeZone;
        this.localDateTime = localDateTime;
    }
}