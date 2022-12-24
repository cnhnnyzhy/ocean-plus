package com.ocean.auth.service.impl;

import com.ocean.common.core.constant.Constants;
import com.ocean.common.core.constant.SecurityConstants;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.stereotype.Service;

/**
 * 授权码模式实现
 *
 * @author ocean
 * @date 2022/11/26
 */
@Service
@RequiredArgsConstructor
public class CustomAuthorizationCodeServicesImpl extends RandomValueAuthorizationCodeServices {
    private final RedisConnectionFactory connectionFactory;

    /**
     * 授权码模式时: 验证码有效期设置, 默认有效期为5分钟, 单位秒
     */
    @Value("${" + Constants.PROJECT + ".properties.authorizationCode.expiredTime:300}")
    private Long expiredTime;

    @Setter
    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    /**
     * 保存code 和 认证信息
     *
     * @param code
     * @param authentication
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        @Cleanup
        RedisConnection connection = connectionFactory.getConnection();
        connection.set(serializationStrategy.serialize(SecurityConstants.OAUTH_CODE_PREFIX + code), serializationStrategy.serialize(authentication),
                Expiration.seconds(expiredTime), RedisStringCommands.SetOption.UPSERT
        );
    }

    /**
     * 删除code并返回认证信息
     *
     * @param code
     * @return
     */
    @Override
    protected OAuth2Authentication remove(String code) {
        @Cleanup
        RedisConnection connection = connectionFactory.getConnection();

        byte[] key = serializationStrategy.serialize(SecurityConstants.OAUTH_CODE_PREFIX + code);
        byte[] value = connection.get(key);

        if (value == null) {
            return null;
        }
        OAuth2Authentication oAuth2Authentication = serializationStrategy.deserialize(value, OAuth2Authentication.class);
        connection.del(key);
        return oAuth2Authentication;
    }
}
