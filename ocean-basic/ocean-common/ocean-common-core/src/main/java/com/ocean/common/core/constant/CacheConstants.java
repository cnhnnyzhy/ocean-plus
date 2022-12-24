package com.ocean.common.core.constant;

import com.ocean.common.core.cache.CacheKey;

/**
 * 缓存常量
 *
 * @author ocean
 * @date 2022/10/16
 */
public interface CacheConstants {
    /**
     * 全局缓存，在缓存名称上加上该前缀表示该缓存不区分租户，比如:
     * <p/>
     * {@code @Cacheable(value = CacheConstants.GLOBAL_CACHE_PREFIX+CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")}
     */
    String GLOBAL_CACHE_PREFIX = "gl:";

    String PREFIX = Constants.PROJECT + "_";
    /**
     * 用户信息缓存
     */
    CacheKey USER_DETAILS = new CacheKey("user_details", 3600L);
    /**
     * AccessToken缓存
     */
    CacheKey OAUTH_ACCESS_TOKEN = new CacheKey("{}:" + PREFIX + "oauth:access:{}", 3600L);
}
