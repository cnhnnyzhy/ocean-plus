package com.ocean.common.core.cache;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 缓存key
 *
 * @author ocean
 * @date 2022/10/16
 */
@Getter
public class CacheKey {
    private String key;
    private Long expireTime;
    private TimeUnit timeUnit;

    public CacheKey(String key, Long expireTime) {
        this.key = key;
        this.expireTime = expireTime;
        this.timeUnit = TimeUnit.SECONDS;
    }

    public CacheKey(String key, Long expireTime, TimeUnit timeUnit) {
        this.key = key;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
    }

    public String format(Object... params) {
        return String.format(this.key, params);
    }
}
