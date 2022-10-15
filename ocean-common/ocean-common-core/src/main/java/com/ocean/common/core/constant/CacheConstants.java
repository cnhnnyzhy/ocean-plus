package com.ocean.common.core.constant;

import com.ocean.common.core.cache.CacheKey;

/**
 * 缓存常量
 *
 * @author ocean
 * @date 2022/10/16
 */
public interface CacheConstants {

    String PREFIX = "ocean_";
    /**
     * 用户信息缓存
     */
    CacheKey USER_DETAILS = new CacheKey("user_details", 3600L);
    /**
     * AccessToken缓存
     */
    CacheKey OAUTH_ACCESS_TOKEN = new CacheKey("{}:" + PREFIX + "oauth:access:{}", 3600L);
}
