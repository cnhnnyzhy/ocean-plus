package com.ocean.auth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocean.admin.api.dto.rsp.OauthClientDetailsRsp;
import com.ocean.auth.exception.BizErrorCode;
import com.ocean.auth.service.TokenService;
import com.ocean.client.factory.RemoteClientFactory;
import com.ocean.common.core.constant.CacheConstants;
import com.ocean.common.core.constant.SecurityConstants;
import com.ocean.common.core.exception.BizException;
import com.ocean.common.core.util.ResultOptional;
import com.ocean.common.core.util.SpringContextHolder;
import com.ocean.common.core.util.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Token服务实现
 *
 * @author ocean
 * @date 2022/10/16
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final TokenStore tokenStore;

    private final CacheManager cacheManager;

    private final RemoteClientFactory remoteClientFactory;

    private final RedisTemplate redisTemplate;


    @Override
    public Boolean removeToken(String token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (Objects.isNull(accessToken) || StringUtils.isBlank(accessToken.getValue())) {
            throw new BizException(BizErrorCode.ACCESS_TOKEN_IS_NULL);
        }
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
        // 清空缓存中的用户信息
        cacheManager.getCache(CacheConstants.USER_DETAILS.getKey()).evict(oAuth2Authentication.getName());
        // 清空accessToken
        tokenStore.removeAccessToken(accessToken);
        // 清空refreshToken
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        tokenStore.removeRefreshToken(refreshToken);
        // 发送退出成功事件
        SpringContextHolder.publishEvent(new LogoutSuccessEvent(oAuth2Authentication));
        return true;
    }

    @Override
    public Page queryTokenByUsername(Page page, String username) {
        List<OAuth2AccessToken> accessTokenList =
                ResultOptional.of(remoteClientFactory.getClientDetailsRemoteClient().listClientDetails(SecurityConstants.FROM_IN))
                        .getData()
                        .orElseGet(Collections::emptyList)
                        .stream().map(OauthClientDetailsRsp::getClientId)
                        .flatMap(clientId -> tokenStore.findTokensByClientIdAndUserName(clientId, username).stream()).distinct()
                        .collect(Collectors.toList());
        page.setRecords(accessTokenList);
        page.setTotal(accessTokenList.size());
        return page;
    }

    @Override
    public Page queryToken(Page page) {
        String patternKey = CacheConstants.OAUTH_ACCESS_TOKEN.format(TenantContextHolder.getTenantId(), "*");
        List<String> keys = findKeysForPage(patternKey, page.getCurrent(), page.getSize());
        page.setRecords(redisTemplate.opsForValue().multiGet(keys));
        page.setTotal(redisTemplate.keys(patternKey).size());
        return page;
    }

    /**
     * 通过游标的方式从redis中取key的数据
     *
     * @param patternKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    private List<String> findKeysForPage(String patternKey, long pageNo, long pageSize) {
        ScanOptions options = ScanOptions.scanOptions().count(1000L).match(patternKey).build();
        RedisSerializer<String> redisSerializer = redisTemplate.getKeySerializer();
        Cursor cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
        if (Objects.isNull(cursor)) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        int index = 0;
        long startIndex = (pageNo - 1) * pageSize;
        long endIndex = pageNo * pageSize;
        while (cursor.hasNext()) {
            if (index >= startIndex && index < endIndex) {
                result.add(cursor.next().toString());
                index++;
                continue;
            }
            if (index >= endIndex) {
                break;
            }
            index++;
            cursor.next();
        }
        try {
            cursor.close();
        } catch (Exception e) {
            log.error("关闭Cursor异常：", e);
        }
        return result;
    }

    @Override
    public OAuth2AccessToken queryTokenInfo(String token) {
        return tokenStore.readAccessToken(token);
    }
}
