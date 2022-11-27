package com.ocean.common.security.component;

import cn.hutool.core.util.StrUtil;
import com.ocean.common.core.constant.SecurityConstants;
import com.ocean.common.core.util.KeyStrResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权自动装配
 *
 * @author ocean
 * @date 2022/11/26
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class CommonSecurityAutoConfiguration {
    private final KeyStrResolver resolver;

    private final RedisConnectionFactory connectionFactory;

    @Bean
    public TokenStore tokenStore() {
        CustomRedisTokenStore tokenStore = new CustomRedisTokenStore(connectionFactory, resolver);
        tokenStore.setPrefix(SecurityConstants.OAUTH_PREFIX);
        tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                return resolver.extract(super.extractKey(authentication), StrUtil.COLON);
            }
        });
        return tokenStore;
    }

    /**
     * 认证状态检查
     *
     * @return
     */
    @Bean
    public UserDetailsChecker preAuthenticationChecker() {
        return new CustomPreAuthenticationChecker();
    }
}
