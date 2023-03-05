package com.ocean.ddd.module.domain.event;

import java.util.List;
import java.util.UUID;

/**
 * @Description: 事件存储
 * @Author: yang.zhang
 * @Date: 2022/7/13 16:20
 */
public interface EventStore {
    /**
     * 存储事件
     *
     * @param aggregateId
     * @param newEvents
     * @param baseVersion
     */
    void store(UUID aggregateId, List<Event> newEvents, int baseVersion);

    /**
     * 加载事件
     *
     * @param aggregateId
     * @return
     */
    List<Event> load(UUID aggregateId);
}
