package com.ocean.ddd.domain.event;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @Description: 事件类
 * @Author: yang.zhang
 * @Date: 2022/7/13 16:14
 */
@Getter
public abstract class Event {
    private final UUID aggregateId;
    private final LocalDateTime timestamp;
    private final int version;

    public Event(UUID aggregateId, LocalDateTime timestamp, int version) {
        this.aggregateId = checkNotNull(aggregateId);
        this.timestamp = checkNotNull(timestamp);
        this.version = version;
    }
}
