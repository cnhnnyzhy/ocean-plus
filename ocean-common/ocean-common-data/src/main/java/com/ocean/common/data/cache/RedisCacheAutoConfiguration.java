package com.ocean.common.data.cache;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis缓存自动装配
 *
 * @author ocean
 * @date 2022/11/27
 */
@Configuration
@AutoConfigureAfter(value = {RedisAutoConfiguration.class})
@ConditionalOnBean(value = {RedisConnectionFactory.class})
@EnableConfigurationProperties(value = CacheProperties.class)
public class RedisCacheAutoConfiguration {
    private final CacheProperties cacheProperties;

    private final CacheManagerCustomizers cacheManagerCustomizers;

    @Nullable
    private final RedisCacheConfiguration redisCacheConfiguration;

    public RedisCacheAutoConfiguration(CacheProperties cacheProperties, CacheManagerCustomizers cacheManagerCustomizers, ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration) {
        this.cacheProperties = cacheProperties;
        this.cacheManagerCustomizers = cacheManagerCustomizers;
        this.redisCacheConfiguration = redisCacheConfiguration.getIfAvailable();
    }

    @Bean
    @Primary
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, ResourceLoader resourceLoader) {
        DefaultRedisCacheWriter redisCacheWriter = new DefaultRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration cacheConfiguration = this.determineConfiguration(resourceLoader.getClassLoader());
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
        if (!cacheNames.isEmpty()) {
            Map<String, RedisCacheConfiguration> cacheConfigMap = new LinkedHashMap<>(cacheNames.size());
            cacheNames.forEach(it -> cacheConfigMap.put(it, cacheConfiguration));
            initialCaches.putAll(cacheConfigMap);
        }
        CustomRedisCacheManager cacheManager = new CustomRedisCacheManager(redisCacheWriter, cacheConfiguration,
                initialCaches, true);
        cacheManager.setTransactionAware(false);
        return this.cacheManagerCustomizers.customize(cacheManager);
    }

    private RedisCacheConfiguration determineConfiguration(ClassLoader classLoader) {
        if (this.redisCacheConfiguration != null) {
            return this.redisCacheConfiguration;
        } else {
            CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
            config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                    .fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
            if (redisProperties.getTimeToLive() != null) {
                config = config.entryTtl(redisProperties.getTimeToLive());
            }

            if (redisProperties.getKeyPrefix() != null) {
                config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
            }

            if (!redisProperties.isCacheNullValues()) {
                config = config.disableCachingNullValues();
            }

            if (!redisProperties.isUseKeyPrefix()) {
                config = config.disableKeyPrefix();
            }

            return config;
        }
    }
}
