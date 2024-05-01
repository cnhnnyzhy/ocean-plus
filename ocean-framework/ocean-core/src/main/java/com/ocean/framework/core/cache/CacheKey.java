package com.ocean.framework.core.cache;

import com.ocean.framework.core.text.StringPool;
import com.ocean.framework.core.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 缓存key
 *
 * @author ocean
 * @date 2022/10/16
 */
@Getter
public class CacheKey {
    /**
     * 缓存key前缀
     */
    private String prefix;
    /**
     * 缓存key
     */
    private String key;
    /**
     * 缓存key版本号
     */
    private String version;
    /**
     * 缓存key格式
     */
    private String format;
    /**
     * 缓存key失效时间
     */
    private Long expireTime;
    /**
     * 缓存key失效时间单位
     */
    private TimeUnit timeUnit;


    public CacheKey(String prefix, String key, String version, String format, Long expireTime) {
        this(prefix, key, version, format, expireTime, TimeUnit.SECONDS);
    }

    public CacheKey(String prefix, String key, String version, String format, Long expireTime, TimeUnit timeUnit) {
        this.prefix = prefix;
        this.key = key;
        this.version = version;
        this.format = format;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
    }

    /**
     * 获取真实key
     *
     * @param params 参数
     * @return java.lang.String
     */
    public String getRealKey(Object... params) {
        StringBuilder keyBuilder = new StringBuilder(prefix + key);
        if (StringUtils.isNotBlank(version)) {
            keyBuilder.append(StringPool.UNDERLINE).append(version);
        }
        Arrays.stream(params).filter(Objects::nonNull).forEach(param ->
                keyBuilder.append(StringPool.COLON).append(param)
        );
        return keyBuilder.toString();
    }
}
