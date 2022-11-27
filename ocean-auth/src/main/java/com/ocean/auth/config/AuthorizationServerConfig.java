package com.ocean.auth.config;

import com.ocean.common.security.component.AuthExceptionEntryPoint;
import com.ocean.common.security.component.CustomTokenServices;
import com.ocean.common.security.component.CustomWebResponseExceptionTranslator;
import com.ocean.common.security.grant.ResourceOwnerCustomAppTokenGranter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 认证服务器配置
 *
 * @author ocean
 * @date 2022/10/16
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final ClientDetailsService clientDetailsService;

    private final AuthExceptionEntryPoint authExceptionEntryPoint;

    private final TokenStore redisTokenStore;

    private final TokenEnhancer tokenEnhancer;

    private final AuthorizationCodeServices authorizationCodeServices;

    private final AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .authenticationEntryPoint(authExceptionEntryPoint)
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).tokenServices(tokenServices())
                .tokenStore(redisTokenStore).tokenEnhancer(tokenEnhancer)
                .authorizationCodeServices(authorizationCodeServices).authenticationManager(authenticationManager)
                .reuseRefreshTokens(false).pathMapping("/oauth/confirm_access", "/token/confirm_access")
                .exceptionTranslator(new CustomWebResponseExceptionTranslator());
        setTokenGranter(endpoints);
    }

    private void setTokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenGranter tokenGranter = endpoints.getTokenGranter();
        List<TokenGranter> tokenGranters = new ArrayList<>(Arrays.asList(tokenGranter));
        tokenGranters.add(new ResourceOwnerCustomAppTokenGranter(authenticationManager,
                endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));
    }

    @Bean
    public CustomTokenServices tokenServices() {
        CustomTokenServices tokenServices = new CustomTokenServices();
        tokenServices.setTokenStore(redisTokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(false);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer);
        return tokenServices;
    }
}
