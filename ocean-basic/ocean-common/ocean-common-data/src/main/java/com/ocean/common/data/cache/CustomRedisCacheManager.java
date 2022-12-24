package com.ocean.common.data.cache;

import cn.hutool.core.util.StrUtil;
import com.ocean.common.core.constant.CacheConstants;
import com.ocean.common.core.util.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 自定义的redis缓存管理器
 *
 * @author ocean
 * @date 2022/11/27
 */
@Slf4j
public class CustomRedisCacheManager extends RedisCacheManager {

    private static final String SPLIT_FLAG = "#";

    private static final int CACHE_NAME_LENGTH = 2;

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        if (StringUtils.isBlank(name) || !name.contains(SPLIT_FLAG)) {
            return super.createRedisCache(name, cacheConfig);
        }

        String[] cacheNameArray = name.split(SPLIT_FLAG);
        if (cacheNameArray.length < CACHE_NAME_LENGTH) {
            return super.createRedisCache(name, cacheConfig);
        }

        if (cacheConfig != null) {
            Duration duration = DurationStyle.detectAndParse(cacheNameArray[1], ChronoUnit.SECONDS);
            cacheConfig = cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(cacheNameArray[0], cacheConfig);
    }

    /**
     * 从上下文中获取租户ID，重写@Cacheable value 值
     *
     * @param name
     * @return
     */
    @Override
    public Cache getCache(String name) {
        if (name.startsWith(CacheConstants.GLOBAL_CACHE_PREFIX)) {
            return super.getCache(name);
        }
        return super.getCache(TenantContextHolder.getTenantId() + StrUtil.COLON + name);
    }
}
