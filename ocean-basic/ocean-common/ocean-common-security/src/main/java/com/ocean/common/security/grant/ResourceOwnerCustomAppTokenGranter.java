package com.ocean.common.security.grant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 资源所有者电话令牌授予者
 *
 * @author ocean
 * @date 2022/10/22
 */
public class ResourceOwnerCustomAppTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "mobile";

    private final AuthenticationManager authenticationManager;

    public ResourceOwnerCustomAppTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    public ResourceOwnerCustomAppTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    @Override
    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        return super.grant(grantType, tokenRequest);
    }

    @Override
    protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
        return super.getAccessToken(client, tokenRequest);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String mobile = parameters.get("mobile");
        String code = parameters.get("code");
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(code)) {
            throw new InvalidGrantException("Bad credentials [ params must be has phone with code ]");
        }
        parameters.remove("code");

        Authentication authentication = new CustomAppAuthenticationToken(mobile, code, tokenRequest.getGrantType());
        ((AbstractAuthenticationToken) authentication).setDetails(parameters);
        try {
            authentication = authenticationManager.authenticate(authentication);
        } catch (AccountStatusException | BadCredentialsException ase) {
            // covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        }
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + mobile);
        }
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, authentication);
    }
}
