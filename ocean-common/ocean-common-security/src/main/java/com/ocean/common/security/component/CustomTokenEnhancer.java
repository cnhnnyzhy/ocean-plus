package com.ocean.common.security.component;

import com.ocean.common.core.constant.SecurityConstants;
import com.ocean.common.security.user.LoginUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * token增强
 *
 * @author ocean
 * @date 2022/11/26
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>(8);
        String clientId = authentication.getOAuth2Request().getClientId();
        additionalInfo.put(SecurityConstants.CLIENT_ID, clientId);
        additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.LICENSE);
        additionalInfo.put(SecurityConstants.ACTIVE, Boolean.TRUE);

        // 客户端模式不返回具体用户信息
        if (SecurityConstants.CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        }

        LoginUser loginUser = (LoginUser) authentication.getUserAuthentication().getPrincipal();
        additionalInfo.put(SecurityConstants.DETAILS_USER_ID, loginUser.getId());
        additionalInfo.put(SecurityConstants.DETAILS_USERNAME, loginUser.getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
