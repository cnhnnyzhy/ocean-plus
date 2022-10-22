package com.ocean.common.security.component;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * 自定义token发放服务
 *
 * @author ocean
 * @date 2022/10/16
 */
@Slf4j
public class CustomTokenServices implements AuthorizationServerTokenServices, ResourceServerTokenServices, ConsumerTokenServices, InitializingBean {
    private TokenStore tokenStore;

    private ClientDetailsService clientDetailsService;
    /**
     * 客户端默认同时在线数量
     */
    private int defaultOnlineQuantity = 0;

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication oAuth2Authentication) throws AuthenticationException {
        OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken(oAuth2Authentication);
        OAuth2RefreshToken refreshToken = null;
        if (Objects.nonNull(existingAccessToken)) {
            int onlineQuantity = getOnlineQuantity(oAuth2Authentication.getOAuth2Request());
            if (onlineQuantity == defaultOnlineQuantity) {
                if (existingAccessToken.isExpired()) {
                    if (Objects.nonNull(existingAccessToken.getRefreshToken())) {
                        refreshToken = existingAccessToken.getRefreshToken();
                        tokenStore.removeRefreshToken(refreshToken);
                    }
                    tokenStore.removeAccessToken(existingAccessToken);
                } else {
                    storeAccessToken(existingAccessToken, oAuth2Authentication);
                    return existingAccessToken;
                }
            }
        }

        Collection<OAuth2AccessToken> currentTokenList = tokenStore.findTokensByClientIdAndUserName(oAuth2Authentication.getOAuth2Request().getClientId(), oAuth2Authentication.getName());
        if (CollectionUtils.isNotEmpty(currentTokenList) && currentTokenList.size() >= getOnlineQuantity(oAuth2Authentication.getOAuth2Request())) {
            Optional<OAuth2AccessToken> oldestTokenOptional = currentTokenList.stream().min(Comparator.comparing(OAuth2AccessToken::getExpiration));
            if (oldestTokenOptional.isPresent()) {
                OAuth2AccessToken oldestAccessToken = oldestTokenOptional.get();
                tokenStore.removeAccessToken(oldestAccessToken);
                if (Objects.nonNull(oldestAccessToken.getRefreshToken())) {
                    refreshToken = oldestAccessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(refreshToken);
                }
            }
        }

        refreshToken = createRefreshToken(oAuth2Authentication);
        OAuth2AccessToken accessToken = createAccessToken(oAuth2Authentication, refreshToken);
        storeAccessToken(accessToken, oAuth2Authentication);
        storeRefreshToken(accessToken.getRefreshToken(), oAuth2Authentication);
        return accessToken;
    }

    private void storeAccessToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (Objects.isNull(accessToken)) {
            return;
        }
        tokenStore.storeAccessToken(accessToken, authentication);
    }

    private void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        if (Objects.isNull(refreshToken)) {
            return;
        }
        tokenStore.storeRefreshToken(refreshToken, authentication);
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String s, TokenRequest tokenRequest) throws AuthenticationException {
        return null;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(tokenStore, "tokenStore must be set");
    }

    @Override
    public boolean revokeToken(String s) {
        return false;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String s) throws AuthenticationException, InvalidTokenException {
        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        return null;
    }

    /**
     * 获取客户端在线数量
     *
     * @param oAuth2Request
     * @return
     */
    private int getOnlineQuantity(OAuth2Request oAuth2Request) {
        if (Objects.nonNull(clientDetailsService)) {
            ClientDetails client = clientDetailsService.loadClientByClientId(oAuth2Request.getClientId());
            return MapUtil.getInt(client.getAdditionalInformation(), "online_quantity", -1);
        }
        return defaultOnlineQuantity;
    }

    private OAuth2RefreshToken createRefreshToken(OAuth2Authentication authentication) {
        return null;
    }

    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        return null;
    }
}
